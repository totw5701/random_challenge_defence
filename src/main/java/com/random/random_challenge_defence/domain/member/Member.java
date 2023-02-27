package com.random.random_challenge_defence.domain.member;

import com.random.random_challenge_defence.api.dto.member.MemberDetailsDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Column(unique = true)
    private String email;
    private String password;
    private String nickname;
    private String picture;
    private String joinDate;
    private String modifyDate;


    public MemberDetailsDto toDetailDto() {
        return MemberDetailsDto.builder()
                .id(this.id)
                .email(this.email)
                .nickname(this.nickname)
                .memberRole(this.memberRole)
                .joinDate(this.joinDate)
                .modifyDate(this.modifyDate)
                .build();
    }
}
