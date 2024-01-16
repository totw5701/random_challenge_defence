package com.random.random_challenge_defence.domain.memberpersonality.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberPersonalityDetailDto {

    private Long id;
    private String title;
    private String description;
}
