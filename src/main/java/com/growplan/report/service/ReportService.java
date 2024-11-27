package com.growplan.report.service;

import com.growplan.chatgpt.dto.request.ChatGPTRequest;
import com.growplan.chatgpt.dto.response.ChatGPTResponse;
import com.growplan.child.domain.UserChild;
import com.growplan.child.domain.repository.ChildRepository;
import com.growplan.common.exception.BadRequestException;
import com.growplan.report.dto.response.ReportListResponse;
import com.growplan.report.dto.response.SummaryResponse;
import com.growplan.survey.domain.SurveyResult;
import com.growplan.survey.domain.repository.SurveyResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.growplan.common.code.ExceptionCode.USER_CHILD_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final RestTemplate template;
    private final ChildRepository childRepository;
    private final SurveyResultRepository surveyResultRepository;
    @Value("${openai.model}")
    private String model;
    @Value("${openai.api.url}")
    private String apiURL;

    public ReportListResponse getMonthlyReport(final Long userId, final Long childId) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(USER_CHILD_NOT_FOUND));

        final LocalDate currentDate = LocalDate.now();
        final LocalDate targetDate = getTargetDate(currentDate);

        final List<SurveyResult> surveyResults = surveyResultRepository.findByYearMonth(targetDate.getYear(), targetDate.getDayOfMonth(), userChild.getId());
        final Map<String, Double> surveyMap = getSurveyMap(surveyResults);

        return ReportListResponse.of(targetDate.getMonthValue(), surveyMap);
    }

    public SummaryResponse getSummary(final Long userId, final Long childId, final String developmentType) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(USER_CHILD_NOT_FOUND));

        final LocalDate currentDate = LocalDate.now();
        final LocalDate targetDate = getTargetDate(currentDate);
        final List<SurveyResult> surveyResults = surveyResultRepository.findByYearMonthAndDevelopmentType(targetDate.getYear(), targetDate.getDayOfMonth(), userChild.getId(), developmentType);

        final List<Integer> scores = getDevelopmentScoreList(surveyResults);
        final String summary = getSummary(developmentType, scores);

        return SummaryResponse.of(summary);
    }

    private LocalDate getTargetDate(final LocalDate currentDate) {
        if (currentDate.getDayOfMonth() > 14) {
            return currentDate;
        } else {
            return currentDate.minusMonths(1);
        }
    }

    private Map<String, Double> getSurveyMap(final List<SurveyResult> surveyResults) {
        return surveyResults.stream()
                .collect(Collectors.groupingBy(
                        surveyResult -> surveyResult.getDevelopmentType().getType(),
                        Collectors.averagingInt(SurveyResult::getScore)
                ));
    }

    private List<Integer> getDevelopmentScoreList(final List<SurveyResult> surveyResults) {
        return surveyResults.stream()
                .sorted(Comparator.comparing(SurveyResult::getCreatedAt))
                .map(SurveyResult::getScore)
                .collect(Collectors.toList());
    }

    private String createPrompt(final String developmentType, final List<Integer> scores) {
        final StringBuilder promptBuilder = new StringBuilder();

        promptBuilder.append("다음은 발달 장애 아동의 발달 점수 기록입니다.\n")
                .append("분석 대상 발달 유형: ").append(developmentType).append("\n\n");

        promptBuilder.append("다음은 날짜 순으로 정렬된 발달 점수입니다:\n")
                .append(scores.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", ")))
                .append("\n\n");

        promptBuilder.append("이 점수를 바탕으로 아래 내용을 분석해 주세요:\n")
                .append("1. 아동의 발달 상태에 대한 전반적인 추세를 설명해 주세요.\n")
                .append("2. 발달 강점으로 볼 수 있는 부분은 무엇인가요?\n")
                .append("3. 추가적인 지원이 필요한 영역은 무엇인지 제안해 주세요.\n\n")
                .append("제공된 발달 점수와 발달 유형을 바탕으로 상세한 분석을 부탁드립니다.");

        return promptBuilder.toString();
    }

    private String getSummary(final String developmentType, final List<Integer> scores) {
        final String prompt = createPrompt(developmentType, scores);
        final ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        final ChatGPTResponse chatGPTResponse = template.postForObject(apiURL, request, ChatGPTResponse.class);
        return chatGPTResponse.getChoices().get(0).getMessage().getContent();
    }
}
