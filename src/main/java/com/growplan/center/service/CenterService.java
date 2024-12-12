package com.growplan.center.service;

import com.growplan.center.domain.Center;
import com.growplan.center.domain.repository.CenterRepository;
import com.growplan.center.domain.type.CenterTagType;
import com.growplan.center.domain.type.ProvinceType;
import com.growplan.center.dto.response.CenterListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CenterService {

    private final CenterRepository centerRepository;

    public CenterListResponse getCentersByPage(final Pageable pageable, final String centerTag, final String province, final String city) {
        List<Center> centers;

        centers = centerRepository.findAllByPageable(pageable.previousOrFirst());

        centers = filterByCenterTag(centers, centerTag);
        centers = filterByLocation(centers, province, city);

        final Long lastPageIndex = getLastPageIndex(pageable.getPageSize());
        return CenterListResponse.of(centers, lastPageIndex);
    }

    private Long getLastPageIndex(final int pageSize) {
        final Long centerCount = centerRepository.countCenter();
        final long lastPageIndex = centerCount / pageSize;
        if (centerCount % pageSize == 0) {
            return lastPageIndex;
        }
        return lastPageIndex + 1;
    }

    private List<Center> filterByCenterTag(final List<Center> centers, final String centerTag) {
        if (centerTag == null) return centers;
        return centers.stream()
                .filter(center ->
                        center.getCenterTags().stream()
                                .anyMatch(tag -> tag.getName().equals(CenterTagType.of(centerTag).getName()))
                )
                .collect(Collectors.toList());
    }

    private List<Center> filterByLocation(final List<Center> centers, final String province, final String city) {
        if (province == null || city == null) return centers;
        return centers.stream()
                .filter(center -> center.getLocation().equals(ProvinceType.of(province).getName() + " " + city))
                .collect(Collectors.toList());
    }
}
