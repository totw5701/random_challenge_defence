package com.random.random_challenge_defence.domain.challengelog.dto;

import lombok.*;

@Data
@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeLogDetailPagingReqDto {

    private Integer currentPage;
    private Integer pageSize;
}
