package com.growplan.survey.service;

import com.growplan.child.domain.UserChild;
import com.growplan.child.domain.repository.ChildRepository;
import com.growplan.common.exception.BadRequestException;
import com.growplan.survey.domain.Survey;
import com.growplan.survey.domain.repository.SurveyRepository;
import com.growplan.survey.dto.response.SurveyListResponse;
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
public class SurveyService {

    private final ChildRepository childRepository;
    private final SurveyRepository surveyRepository;

    @Transactional(readOnly = true)
    public SurveyListResponse getSurvey(final Long userId, final Long childId) {
        // TODO 쿼리 수정하기
        final UserChild userChild = childRepository.findById(childId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_CHILD));

        final Double birthdate = calculateAge(userChild.getBirthdate());

        final List<Survey> surveys = surveyRepository.findByValidAgeLessThanOrEqualTo(birthdate);

        return SurveyListResponse.of(surveys);
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
