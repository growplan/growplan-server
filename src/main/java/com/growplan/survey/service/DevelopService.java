package com.growplan.survey.service;

import com.growplan.child.domain.UserChild;
import com.growplan.child.domain.repository.ChildRepository;
import com.growplan.common.exception.BadRequestException;
import com.growplan.survey.domain.*;
import com.growplan.survey.domain.repository.ChildSurveyRepository;
import com.growplan.survey.domain.repository.DevelopmentTypeRepository;
import com.growplan.survey.domain.repository.SurveyGroupRepository;
import com.growplan.survey.domain.repository.SurveyResultRepository;
import com.growplan.survey.dto.response.DevelopmentResultResponse;
import com.growplan.survey.dto.response.DevelopmentScaleSurveyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.growplan.common.code.ExceptionCode.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DevelopService {

    private final ChildRepository childRepository;
    private final ChildSurveyRepository childSurveyRepository;
    private final SurveyResultRepository surveyResultRepository;
    private final SurveyGroupRepository surveyGroupRepository;
    private final DevelopmentTypeRepository developmentTypeRepository;

    public DevelopmentScaleSurveyResponse getDevelopmentScalesAndSurveys(final Long userId, final Long childId) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(USER_CHILD_NOT_FOUND));

        final Integer childMonths = calculateAgeInMonths(userChild.getBirthdate());

        final List<SurveyResult> surveyResults = surveyResultRepository.findRecentSurveyResults(userChild.getId());

        final List<SurveyGroup> surveyGroups = surveyGroupRepository.findSurveyGroupByMonths(childMonths);

        return DevelopmentScaleSurveyResponse.of(userChild, childMonths, surveyResults, surveyGroups);
    }

    public DevelopmentResultResponse getDevelopmentResult(final Long userId, final Long childId, final String developmentType) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(USER_CHILD_NOT_FOUND));

        final LocalDate currentDate = LocalDate.now();

        final List<ChildSurvey> surveys = getChildSurveys(userChild, currentDate, developmentType);

        final Integer developmentScore = calculateDevelopmentScore(surveys);
        final Boolean isRisk = calculateRisk(developmentScore);

        final SurveyResult surveyResult = getOrCreateSurveyResult(userChild, developmentType, currentDate, developmentScore, isRisk, surveys);
        final List<Feedback> feedbacks = getFeedbacks(surveys, developmentScore);

        updateSurveyScore(surveyResult, developmentScore, isRisk);

        return DevelopmentResultResponse.of(currentDate, surveys, developmentScore, feedbacks);
    }

    private List<ChildSurvey> getChildSurveys(final UserChild userChild, final LocalDate date, final String developmentType) {
        final List<ChildSurvey> surveys = childSurveyRepository.findByChildIdAndDevelopmentType(date, developmentType, userChild.getId());
        if (surveys.isEmpty()) {
            throw new BadRequestException(CHILD_SURVEY_NOT_FOUND);
        }
        return surveys;
    }

    private Integer calculateDevelopmentScore(final List<ChildSurvey> surveys) {
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

    private List<Feedback> getFeedbacks(final List<ChildSurvey> surveys, final Integer developmentScore) {
        final SurveyGroup surveyGroup = surveys.get(0).getSurvey().getSurveyGroup();

        return surveyGroup.getFeedbacks().stream()
                .filter(feedback -> feedback.getMinRange() <= developmentScore && developmentScore < feedback.getMaxRange())
                .distinct()
                .toList();
    }

    private SurveyResult getOrCreateSurveyResult(final UserChild userChild, final String developmentType, final LocalDate date, final Integer developmentScore, final Boolean isRisk, final List<ChildSurvey> surveys) {
        return surveyResultRepository.findByDateAndDevelopmentType(date, developmentType)
                .orElseGet(() -> {
                    final DevelopmentType type = developmentTypeRepository.findByType(developmentType)
                            .orElseThrow(() -> new BadRequestException(DEVELOPMENT_TYPE_NOT_FOUND));

                    final SurveyResult newSurveyResult = new SurveyResult(
                            userChild,
                            type,
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

    private int calculateAgeInMonths(final String birthdateStr) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate birthdate = LocalDate.parse(birthdateStr, formatter);
        final LocalDate today = LocalDate.now();

        final long totalDays = ChronoUnit.DAYS.between(birthdate, today);

        return (int) (totalDays / 30);
    }
}
