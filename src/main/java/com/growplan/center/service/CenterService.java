package com.growplan.center.service;

import com.growplan.center.domain.Center;
import com.growplan.center.domain.repository.CenterRepository;
import com.growplan.center.domain.type.CenterTagType;
import com.growplan.center.domain.type.ProvinceType;
import com.growplan.center.dto.response.CenterListResponse;
import com.growplan.center.dto.response.CenterResponse;
import com.growplan.common.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.growplan.common.code.ExceptionCode.CENTER_FILTER_QUERY_CREATE_FAILED;

@Service
@Transactional
@RequiredArgsConstructor
public class CenterService {

    private final CenterRepository centerRepository;

    public CenterListResponse getCentersByPage(final Pageable pageable, final List<String> centerTags, final String province, final String city, final String neighborhood, final Long userId, final Boolean isScraped) {
        List<Center> centers;

        final String locationQuery = createQuery(province, city, neighborhood);
        final List<String> centerTagNames = getCenterTagNames(centerTags);

        centers = centerRepository.findFilteredCentersByPageable(
                centerTagNames,
                locationQuery,
                userId,
                isScraped,
                pageable.previousOrFirst()
        );

        final Long lastPageIndex = getLastPageIndex(
                pageable.getPageSize(),
                centerTagNames,
                locationQuery,
                userId,
                isScraped
        );

        final List<CenterResponse> centerResponses = createCenterResponse(centers, userId);
        return CenterListResponse.of(centerResponses, lastPageIndex);
    }

    private List<String> getCenterTagNames(List<String> centerTags) {
        if (centerTags == null) {
            return null;
        }

        return centerTags.stream()
                .map(centerTag -> CenterTagType.of(centerTag).toString())
                .collect(Collectors.toList());
    }

    private Long getLastPageIndex(final int pageSize, final List<String> centerTags, final String locationQuery, final Long userId, final Boolean isScraped) {
        final long centerCount = centerRepository.countFilteredCenters(
                centerTags,
                locationQuery,
                userId,
                isScraped
        );

        final long lastPageIndex = centerCount / pageSize;
        if (centerCount % pageSize == 0) {
            return lastPageIndex;
        }
        return lastPageIndex + 1;
    }

    private String createQuery(final String province, final String city, final String neighborhood) {
        if (province == null)
            return null;
        else if (province != null && city == null)
            return ProvinceType.of(province).getName();
        else if (province != null && city != null && neighborhood == null)
            return ProvinceType.of(province).getName() + " " + city;
        else if (province != null && city != null && neighborhood != null)
            return ProvinceType.of(province).getName() + " " + city + " " + neighborhood;
        throw new BadRequestException(CENTER_FILTER_QUERY_CREATE_FAILED);
    }

    private List<CenterResponse> createCenterResponse(final List<Center> centers, final Long userId) {
        return centers.stream()
                .map(center -> {
                    final boolean isScraped = center.getScraps().stream()
                            .anyMatch(scrap -> scrap.getUser() != null && scrap.getUser().getId().equals(userId));
                    return CenterResponse.of(center, isScraped);
                })
                .collect(Collectors.toList());
    }
}
