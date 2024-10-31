package com.growplan.survey.service;

import com.growplan.child.domain.UserChild;
import com.growplan.child.domain.repository.ChildRepository;
import com.growplan.common.exception.BadRequestException;
import com.growplan.survey.domain.Survey;
import com.growplan.survey.domain.repository.SurveyRepository;
import com.growplan.survey.dto.response.DevelopmentResultListResponse;
import com.growplan.survey.dto.response.DevelopmentResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.growplan.common.code.ExceptionCode.NOT_FOUND_USER_CHILD;

@Service
@Transactional
@RequiredArgsConstructor
public class DevelopService {

    private final SurveyRepository surveyRepository;
    private final ChildRepository childRepository;

    public DevelopmentResultListResponse getAllDevelopmentResults(final Long userId, final Long childId) {

    }


    public DevelopmentResultResponse getDevelopmentResult(final Long userId, final Long childId, final String developmentType) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_CHILD));

        final Double birthdate = calculateAge(userChild.getBirthdate());

        final List<Survey> surveys = surveyRepository.findByValidAgeLessThanOrEqualTo(birthdate);

        final Integer developmentScore = calculateDevelopmentScore(surveys);

        return DevelopmentResultResponse.of(surveys, developmentScore);

    }

    // TODO 계산식 작성하기
    private Integer calculateDevelopmentScore(final List<Survey> surveys) {
        return 0;
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
