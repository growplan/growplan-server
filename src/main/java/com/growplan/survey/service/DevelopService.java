package com.growplan.survey.service;

import com.growplan.child.domain.UserChild;
import com.growplan.child.domain.repository.ChildRepository;
import com.growplan.common.exception.BadRequestException;
import com.growplan.survey.domain.ChildSurvey;
import com.growplan.survey.domain.SurveyGroup;
import com.growplan.survey.domain.SurveyResult;
import com.growplan.survey.domain.repository.ChildSurveyRepository;
import com.growplan.survey.domain.repository.SurveyGroupRepository;
import com.growplan.survey.domain.repository.SurveyResultRepository;
import com.growplan.survey.dto.response.DevelopmentResultResponse;
import com.growplan.survey.dto.response.DevelopmentScaleSurveyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.growplan.common.code.ExceptionCode.NOT_FOUND_CHILD_SURVEY;
import static com.growplan.common.code.ExceptionCode.NOT_FOUND_USER_CHILD;

@Service
@Transactional
@RequiredArgsConstructor
public class DevelopService {

    private final ChildRepository childRepository;
    private final ChildSurveyRepository childSurveyRepository;
    private final SurveyResultRepository surveyResultRepository;
    private final SurveyGroupRepository surveyGroupRepository;

    public DevelopmentScaleSurveyResponse getDevelopmentScalesAndSurveys(final Long userId, final Long childId) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_CHILD));

        final Double validAge = calculateAge(userChild.getBirthdate());
        final Integer months = calculateAgeInMonths(userChild.getBirthdate());

        final List<SurveyResult> surveyResults = surveyResultRepository.findRecentSurveyResults(userChild.getId());

        final List<SurveyGroup> surveyGroups = surveyGroupRepository.findSurveyGroupByValidAge(validAge);

        return DevelopmentScaleSurveyResponse.of(userChild, months, surveyResults, surveyGroups);
    }

    public DevelopmentResultResponse getDevelopmentResult(final Long userId, final Long childId, final String developmentType) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_CHILD));

        final LocalDate currentDate = LocalDate.now();

        final List<ChildSurvey> surveys = getSurveys(userChild, currentDate, developmentType);

        final Integer developmentScore = calculateDevelopmentScore(surveys);
        final Boolean isRisk = calculateRisk(developmentScore);

        final SurveyResult surveyResult = getOrCreateSurveyResult(userChild, developmentType, currentDate, developmentScore, isRisk, surveys);

        updateSurveyScore(surveyResult, developmentScore, isRisk);

        // TODO 점수별 Script 추가 -> DB 반영 필요
        return DevelopmentResultResponse.of(surveys, developmentScore);
    }

    private List<ChildSurvey> getSurveys(final UserChild userChild, final LocalDate date, final String developmentType) {
        List<ChildSurvey> surveys = childSurveyRepository.findByChildIdAndDevelopmentType(date, developmentType, userChild.getId());
        if (surveys.isEmpty()) {
            throw new BadRequestException(NOT_FOUND_CHILD_SURVEY);
        }
        return surveys;
    }

    private Integer calculateDevelopmentScore(final List<ChildSurvey> surveys) {
        if (surveys.isEmpty()) {
            return 0;
        }

        double totalWeight = 0.0;
        double totalScore = 0.0;

        for (final ChildSurvey survey : surveys) {
            totalWeight += survey.getSurvey().getWeight();
        }

        for (final ChildSurvey survey : surveys) {
            final Double weight = survey.getSurvey().getWeight();
            final Integer status = survey.getStatus();

            totalScore += (weight / totalWeight) * (status / 4.0);
        }

        final double developmentScore = totalScore * 24.0;
        return (int) Math.round(developmentScore);
    }

    private Boolean calculateRisk(final Integer developmentScore) {
        return developmentScore <= 6;
    }

    private SurveyResult getOrCreateSurveyResult(final UserChild userChild, final String developmentType, final LocalDate currentDate, final Integer developmentScore, final Boolean isRisk, final List<ChildSurvey> surveys) {
        return surveyResultRepository.findByDateAndDevelopmentType(currentDate, developmentType)
                .orElseGet(() -> {
                    SurveyResult newSurveyResult = new SurveyResult(
                            userChild,
                            surveys.get(0).getSurvey().getSurveyGroup().getDevelopmentType(),
                            developmentScore,
                            isRisk
                    );
                    return surveyResultRepository.save(newSurveyResult);
                });
    }

    private void updateSurveyScore(final SurveyResult surveyResult, final Integer developmentScore, final Boolean isRisk) {
        if (!surveyResult.getScore().equals(developmentScore)) {
            surveyResult.updateSurveyResult(developmentScore, isRisk);
            surveyResultRepository.save(surveyResult);
        }
    }

    private Period getAgePeriod(final String birthdateStr) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate birthdate = LocalDate.parse(birthdateStr, formatter);
        final LocalDate today = LocalDate.now();
        return Period.between(birthdate, today);
    }

    private Double calculateAge(final String birthdateStr) {
        final Period period = getAgePeriod(birthdateStr);
        final int years = period.getYears();
        final int months = period.getMonths();

        return years + (months / 100.0);
    }

    private int calculateAgeInMonths(final String birthdateStr) {
        final Period period = getAgePeriod(birthdateStr);
        return period.getYears() * 12 + period.getMonths();
    }
}
