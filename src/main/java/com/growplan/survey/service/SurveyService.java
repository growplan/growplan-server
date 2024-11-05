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

import static com.growplan.common.code.ExceptionCode.NOT_FOUND_CHILD_SURVEY;
import static com.growplan.common.code.ExceptionCode.NOT_FOUND_USER_CHILD;

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
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_CHILD));

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
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_CHILD));

        final Double validAge = calculateAge(userChild.getBirthdate());

        final LocalDate currentDate = LocalDate.now();
        final List<ChildSurvey> childSurveys = childSurveyRepository.findByChildIdAndDevelopmentType(currentDate, developmentType, childId);

        if (childSurveys.isEmpty()) {
            final List<Survey> surveys = surveyRepository.findByValidAgeAndDevelopmentType(validAge, developmentType);
            return SurveyDetailListResponse.fromSurveys(surveys);
        }

        return SurveyDetailListResponse.fromChildSurveys(childSurveys);
    }

    public void saveChildSurvey(final Long userId, final Long childId, final ChildSurveyRequest childSurveyRequest) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_CHILD));

        final Double validAge = calculateAge(userChild.getBirthdate());
    }

    public void updateChildSurvey(final Long userId, final Long childId, final Long surveyId, final ChildSurveyUpdateRequest childSurveyUpdateRequest) {
        final ChildSurvey childSurvey = childSurveyRepository.findById(surveyId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_CHILD_SURVEY));

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

        final double ageInYears = years + months / 12.0;
        return Math.round(ageInYears * 10.0) / 100.0;
    }
}
