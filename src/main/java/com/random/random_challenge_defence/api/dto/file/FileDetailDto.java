package com.random.random_challenge_defence.api.dto.file;

import com.random.random_challenge_defence.api.dto.member.MemberDetailsDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileDetailDto {

    private Long id;
    private String key;
    private String url;
    private String createDtm;
    private Long memberId;
    private Long challengeCardId;
    private Long challengeLogId;

}
