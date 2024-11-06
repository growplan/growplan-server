package com.growplan.report.service;

import com.growplan.child.domain.UserChild;
import com.growplan.child.domain.repository.ChildRepository;
import com.growplan.common.exception.BadRequestException;
import com.growplan.report.dto.response.ReportListResponse;
import com.growplan.survey.domain.SurveyResult;
import com.growplan.survey.domain.repository.SurveyResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.growplan.common.code.ExceptionCode.NOT_FOUND_USER_CHILD;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final ChildRepository childRepository;
    private final SurveyResultRepository surveyResultRepository;

    public ReportListResponse getReports(final Long userId, final Long childId) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_CHILD));

        final LocalDate currentDate = LocalDate.now();
        final LocalDate targetDate = getTargetDate(currentDate);

        final List<SurveyResult> surveyResults = surveyResultRepository.findByYearMonth(targetDate.getYear(), targetDate.getDayOfMonth(), userChild.getId());
        final Map<String, Double> surveyMap = getSurveyMap(surveyResults);

        return ReportListResponse.of(targetDate.getMonthValue(), surveyMap, null);
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
}
