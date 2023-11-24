package com.random.random_challenge_defence.domain.member;

import com.random.random_challenge_defence.api.dto.member.MemberDetailsDto;
import com.random.random_challenge_defence.api.dto.member.MemberPutReqDto;
import com.random.random_challenge_defence.domain.membermemberpersonality.MemberMemberPersonality;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Column(unique = true)
    private String email;
    private String password;
    private String nickname;
    private String picture;
    private String joinDtm;
    private String modifyDtm;
    private Long experience;

    @OneToMany(mappedBy = "member")
    private List<MemberMemberPersonality> memberMemberPersonalities;

    public Member entityUpdate(String picture) {
        this.picture = picture;
        return this;
    }

    public MemberDetailsDto toDetailDto() {
        return MemberDetailsDto.builder()
                .id(this.id)
                .email(this.email)
                .nickname(this.nickname)
                .memberRole(this.memberRole)
                .joinDtm(this.joinDtm)
                .experience(this.experience)
                .modifyDtm(this.modifyDtm)
                .build();
    }

    public Member update(MemberPutReqDto form) {
        if(form.getNickname() != null) {
            this.nickname = form.getNickname();
        }
        if(form.getPicture() != null) {
            this.picture = form.getPicture();
        }
        if(form.getPassword() != null) {
            this.password = form.getPassword();
        }
        return this;
    }

    public void increaseExperience(Integer experience) {
        this.experience += experience;
    }
}
