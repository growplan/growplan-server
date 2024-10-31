package com.growplan.survey.service;

import com.growplan.child.domain.UserChild;
import com.growplan.child.domain.repository.ChildRepository;
import com.growplan.common.exception.BadRequestException;
import com.growplan.survey.domain.ChildSurvey;
import com.growplan.survey.domain.repository.ChildSurveyRepository;
import com.growplan.survey.dto.response.DevelopmentDetailResultResponse;
import com.growplan.survey.dto.response.DevelopmentResultListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.growplan.common.code.ExceptionCode.NOT_FOUND_USER_CHILD;

@Service
@Transactional
@RequiredArgsConstructor
public class DevelopService {

    private final ChildRepository childRepository;
    private final ChildSurveyRepository childSurveyRepository;

    public DevelopmentResultListResponse getAllDevelopmentResults(final Long userId, final Long childId) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_CHILD));

        final Double birthdate = calculateAge(userChild.getBirthdate());

        final List<ChildSurvey> surveys = childSurveyRepository.findByValidAge(birthdate);

        final Map<String, Integer> resultScoreMap = calculateAllDevelopmentScores(surveys);

        return DevelopmentResultListResponse.of(resultScoreMap);
    }

    public DevelopmentDetailResultResponse getDevelopmentResult(final Long userId, final Long childId, final String developmentType) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_CHILD));

        final Double validAge = calculateAge(userChild.getBirthdate());

        final List<ChildSurvey> surveys = childSurveyRepository.findByValidAgeAndDevelopmentType(validAge, developmentType);

        final Integer developmentScore = calculateDevelopmentScore(surveys);

        return DevelopmentDetailResultResponse.of(surveys, developmentScore);

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

        final double developmentScore = totalScore * 30.0;
        return (int) Math.round(developmentScore);
    }

    private Map<String, Integer> calculateAllDevelopmentScores(final List<ChildSurvey> surveys) {
        Map<String, List<ChildSurvey>> surveyMap = new HashMap<>();

        for (ChildSurvey survey : surveys) {
            final String developmentType = survey.getSurvey().getDevelopmentType().getType();
            surveyMap.computeIfAbsent(developmentType, k -> new ArrayList<>()).add(survey);
        }

        Map<String, Integer> resultScoreMap = new HashMap<>();

        for (Map.Entry<String, List<ChildSurvey>> entry : surveyMap.entrySet()) {
            final String developmentType = entry.getKey();
            final List<ChildSurvey> childSurveys = entry.getValue();

            final Integer score = calculateDevelopmentScore(childSurveys);
            resultScoreMap.put(developmentType, score);
        }

        return resultScoreMap;
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
