package com.random.random_challenge_defence.api.dto.member;

import com.random.random_challenge_defence.domain.member.MemberRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDetailsDto {

    private Long id;
    private String email;
    private String nickname;
    private MemberRole memberRole;
    private String picture;
    private String joinDtm;
    private String modifyDtm;

}
