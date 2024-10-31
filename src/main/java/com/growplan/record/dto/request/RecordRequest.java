package com.growplan.record.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordRequest {

    @NotEmpty(message = "발달 영역을 하나 이상 선택해주세요.")
    private List<String> developmentTypes;

    private List<MultipartFile> images;

    @NotBlank(message = "발달 관련 설명을 입력해주세요.")
    private String script;
}
