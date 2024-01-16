package com.random.random_challenge_defence.domain.challengecardmemberpersonality.entity;

import com.random.random_challenge_defence.domain.challengecard.entity.ChallengeCard;
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
public class ChallengeCardMemberPersonality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_card_id")
    private ChallengeCard challengeCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_personality_id")
    private MemberPersonality memberPersonality;
}
