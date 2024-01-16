package com.random.random_challenge_defence.domain.membermemberpersonality.entity;

import com.random.random_challenge_defence.domain.member.entity.Member;
import com.random.random_challenge_defence.domain.memberpersonality.entity.MemberPersonality;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_personality_id")
    private MemberPersonality memberPersonality;
}
