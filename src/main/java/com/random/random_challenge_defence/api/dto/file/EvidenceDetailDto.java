package com.random.random_challenge_defence.api.dto.file;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EvidenceDetailDto {

    private Long id;
    private String key;
    private String url;
    private String createDtm;

}
