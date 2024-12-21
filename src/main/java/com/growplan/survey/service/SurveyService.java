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
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

        final Integer childMonth = calculateAgeInMonths(userChild.getBirthdate());
        final LocalDate currentDate = LocalDate.now();

        final List<ChildSurvey> childSurveys = childSurveyRepository.findByMonths(childMonth, currentDate);

        return SurveyListResponse.of(childSurveys);
    }

    @Transactional(readOnly = true)
    public SurveyDetailListResponse getSurveyDetail(final Long userId, final Long childId, final String developmentType) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(USER_CHILD_NOT_FOUND));

        final Integer childMonths = calculateAgeInMonths(userChild.getBirthdate());

        final LocalDate currentDate = LocalDate.now();
        final List<ChildSurvey> childSurveys = childSurveyRepository.findByChildIdAndDevelopmentType(currentDate, developmentType, childId);

        if (childSurveys.isEmpty()) {
            final List<Survey> surveys = surveyRepository.findByMonthsAndDevelopmentType(childMonths, developmentType);
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

        createOrUpdateChildSurveys(childSurveyRequests, userChild, surveyMap);
    }

    private List<Long> getSurveyIds(final List<ChildSurveyRequest> childSurveyRequests) {
        return childSurveyRequests.stream()
                .map(ChildSurveyRequest::getId)
                .distinct()
                .collect(Collectors.toList());
    }

    public void createOrUpdateChildSurveys(final List<ChildSurveyRequest> childSurveyRequests, final UserChild userChild, final Map<Long, Survey> surveyMap) {
        final List<ChildSurvey> existingChildSurveys = childSurveyRepository.findBySurveyIds(surveyMap.keySet().stream().toList(), LocalDate.now());
        List<ChildSurvey> childSurveys;


        if (existingChildSurveys.isEmpty()) {
            childSurveys = createChildSurveys(childSurveyRequests, userChild, surveyMap);
        } else {
            childSurveys = updateChildSurveys(childSurveyRequests, existingChildSurveys, surveyMap);
        }
        childSurveyRepository.saveAll(childSurveys);
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

    private List<ChildSurvey> updateChildSurveys(final List<ChildSurveyRequest> childSurveyRequests, final List<ChildSurvey> existingChildSurveys, final Map<Long, Survey> surveyMap) {
        final Map<Long, ChildSurveyRequest> requestMap = childSurveyRequests.stream()
                .collect(Collectors.toMap(ChildSurveyRequest::getId, request -> request));

        existingChildSurveys.forEach(childSurvey -> {
            final ChildSurveyRequest matchingRequest = requestMap.get(childSurvey.getSurvey().getId());
            if (matchingRequest != null) {
                childSurvey.updateChildSurvey(matchingRequest.getStatus());
            }
        });

        return existingChildSurveys;
    }

    public void updateChildSurvey(final Long userId, final Long childId, final Long surveyId, final ChildSurveyUpdateRequest childSurveyUpdateRequest) {
        final ChildSurvey childSurvey = childSurveyRepository.findById(surveyId)
                .orElseThrow(() -> new BadRequestException(CHILD_SURVEY_NOT_FOUND));

        childSurvey.updateChildSurvey(childSurveyUpdateRequest.getStatus());

        childSurveyRepository.save(childSurvey);
    }

    private int calculateAgeInMonths(final String birthdateStr) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate birthdate = LocalDate.parse(birthdateStr, formatter);
        final LocalDate today = LocalDate.now();

        final long totalDays = ChronoUnit.DAYS.between(birthdate, today);

        return (int) (totalDays / 30);
    }
}
