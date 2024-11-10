package com.growplan.survey.service;

import com.growplan.child.domain.UserChild;
import com.growplan.child.domain.repository.ChildRepository;
import com.growplan.common.exception.BadRequestException;
import com.growplan.survey.domain.ChildSurvey;
import com.growplan.survey.domain.Survey;
import com.growplan.survey.domain.repository.ChildSurveyRepository;
import com.growplan.survey.domain.repository.SurveyRepository;
import com.growplan.survey.dto.request.ChildSurveyRequest;
import com.growplan.survey.dto.request.ChildSurveyUpdateRequest;
import com.growplan.survey.dto.response.SurveyDetailListResponse;
import com.growplan.survey.dto.response.SurveyListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.growplan.common.code.ExceptionCode.CHILD_SURVEY_NOT_FOUND;
import static com.growplan.common.code.ExceptionCode.USER_CHILD_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class SurveyService {

    private final ChildRepository childRepository;
    private final SurveyRepository surveyRepository;
    private final ChildSurveyRepository childSurveyRepository;

    @Transactional(readOnly = true)
    public SurveyListResponse getChildSurveys(final Long userId, final Long childId) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(USER_CHILD_NOT_FOUND));

        final Double validAge = calculateAge(userChild.getBirthdate());
        final LocalDate currentDate = LocalDate.now();

        final List<ChildSurvey> childSurveys = childSurveyRepository.findByValidAge(validAge, currentDate);

        final Map<String, List<ChildSurvey>> groupedChildSurveys = childSurveys.stream()
                .collect(Collectors.groupingBy(childSurvey -> childSurvey.getSurvey().getSurveyGroup().getTitle()));

        return SurveyListResponse.of(groupedChildSurveys);
    }

    @Transactional(readOnly = true)
    public SurveyDetailListResponse getSurveyDetail(final Long userId, final Long childId, final String developmentType) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(USER_CHILD_NOT_FOUND));

        final Double validAge = calculateAge(userChild.getBirthdate());

        final LocalDate currentDate = LocalDate.now();
        final List<ChildSurvey> childSurveys = childSurveyRepository.findByChildIdAndDevelopmentType(currentDate, developmentType, childId);

        if (childSurveys.isEmpty()) {
            final List<Survey> surveys = surveyRepository.findByValidAgeAndDevelopmentType(validAge, developmentType);
            return SurveyDetailListResponse.fromSurveys(surveys);
        }

        return SurveyDetailListResponse.fromChildSurveys(childSurveys);
    }

    public void saveChildSurvey(final Long userId, final Long childId, final List<ChildSurveyRequest> childSurveyRequests) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(USER_CHILD_NOT_FOUND));

        final List<Long> surveyIds = getSurveyIds(childSurveyRequests);

        final List<Survey> surveys = surveyRepository.findByIdIn(surveyIds);

        final Map<Long, Survey> surveyMap = surveys.stream()
                .collect(Collectors.toMap(Survey::getId, survey -> survey));

        final List<ChildSurvey> childSurveys = createChildSurveys(childSurveyRequests, userChild, surveyMap);

        childSurveyRepository.saveAll(childSurveys);
    }

    private List<Long> getSurveyIds(final List<ChildSurveyRequest> childSurveyRequests) {
        return childSurveyRequests.stream()
                .map(ChildSurveyRequest::getId)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<ChildSurvey> createChildSurveys(final List<ChildSurveyRequest> childSurveyRequests, final UserChild userChild, final Map<Long, Survey> surveyMap) {
        return childSurveyRequests.stream()
                .map(childSurveyRequest -> new ChildSurvey(
                        childSurveyRequest.getStatus(),
                        userChild,
                        surveyMap.get(childSurveyRequest.getId())
                ))
                .collect(Collectors.toList());
    }

    public void updateChildSurvey(final Long userId, final Long childId, final Long surveyId, final ChildSurveyUpdateRequest childSurveyUpdateRequest) {
        final ChildSurvey childSurvey = childSurveyRepository.findById(surveyId)
                .orElseThrow(() -> new BadRequestException(CHILD_SURVEY_NOT_FOUND));

        childSurvey.updateChildSurvey(childSurveyUpdateRequest.getStatus());

        childSurveyRepository.save(childSurvey);
    }

    private Double calculateAge(final String birthdateStr) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate birthdate = LocalDate.parse(birthdateStr, formatter);
        final LocalDate today = LocalDate.now();

        final Period period = Period.between(birthdate, today);
        final int years = period.getYears();
        final int months = period.getMonths();

        return years + (months / 100.0);
    }
}
