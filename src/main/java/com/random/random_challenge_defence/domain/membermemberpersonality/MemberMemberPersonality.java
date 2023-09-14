package com.random.random_challenge_defence.domain.membermemberpersonality;

import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.memberpersonality.MemberPersonality;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberMemberPersonality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private MemberPersonality memberPersonality;
}
