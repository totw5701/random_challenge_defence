package com.random.random_challenge_defence.api.dto.file;

import com.random.random_challenge_defence.api.dto.member.MemberDetailsDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileDetailDto {

    @ApiModelProperty(example = "long 파일 id")
    private Long id;
    @ApiModelProperty(example = "S3에서 사용하는 key")
    private String key;
    @ApiModelProperty(example = "파일 접근 url")
    private String url;
    @ApiModelProperty(example = "파일 생성일")
    private String createDtm;
    @ApiModelProperty(example = "long 소유 회원 id")
    private Long memberId;
    @ApiModelProperty(example = "long 챌린지 카드 id")
    private Long challengeCardId;
    @ApiModelProperty(example = "log 챌린지 이력 id")
    private Long challengeLogId;

}
