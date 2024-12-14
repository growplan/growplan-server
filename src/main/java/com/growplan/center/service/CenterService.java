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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        centers = centerRepository.findAllByPageable(pageable.previousOrFirst());

        centers = filterByCenterTag(centers, centerTags);
        centers = filterByLocation(centers, province, city, neighborhood);
        centers = filterByIsScraped(centers, userId, isScraped);

        final Long lastPageIndex = getLastPageIndex(pageable.getPageSize(), centerTags, province, city, neighborhood);

        final List<CenterResponse> centerResponses = createCenterResponse(centers, userId);
        return CenterListResponse.of(centerResponses, lastPageIndex);
    }

    private Long getLastPageIndex(final int pageSize, final List<String> centerTags, final String province, final String city, final String neighborhood) {
        List<Center> centers = centerRepository.findAll();
        centers = filterByCenterTag(centers, centerTags);
        centers = filterByLocation(centers, province, city, neighborhood);

        final int centerCount = centers.size();
        final long lastPageIndex = centerCount / pageSize;
        if (centerCount % pageSize == 0) {
            return lastPageIndex;
        }
        return lastPageIndex + 1;
    }

    private List<Center> filterByCenterTag(final List<Center> centers, final List<String> centerTags) {
        if (centerTags == null || centerTags.isEmpty() || centers == null) {
            return centers;
        }
        return centers.stream()
                .filter(center ->
                        center.getCenterTags().stream()
                                .anyMatch(tag -> centerTags.contains(CenterTagType.valueOf(tag.getDevelopmentType().getType()).getName()))
                )
                .collect(Collectors.toList());
    }

    private List<Center> filterByLocation(final List<Center> centers, final String province, final String city, final String neighborhood) {
        if (province == null) return centers;

        final String query = createQuery(province, city, neighborhood);

        return centers.stream()
                .filter(center -> center.getLocation().contains(query))
                .collect(Collectors.toList());
    }

    private List<Center> filterByIsScraped(final List<Center> centers, final Long userId, final Boolean isScraped) {
        if (!isScraped) return centers;

        return centers.stream()
                .filter(center -> center.getScraps().stream()
                        .anyMatch(scrap -> scrap.getUser().getId().equals(userId)))
                .collect(Collectors.toList());
    }

    private String createQuery(final String province, final String city, final String neighborhood) {
        if (province != null && city == null)
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
