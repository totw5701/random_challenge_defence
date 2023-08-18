package com.random.random_challenge_defence.domain.file;

import com.random.random_challenge_defence.api.dto.file.FileDetailDto;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLog;
import com.random.random_challenge_defence.domain.member.Member;
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
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String key;
    private String url;
    private String createDtm;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChallengeLog challengeLog;

    @OneToOne
    private ChallengeCard challengeCard;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public FileDetailDto toDto() {

        Long challengeCardId = null;
        Long challengeLogId = null;

        if(this.challengeCard != null) {
            challengeCardId = this.challengeCard.getId();
        }
        if(this.challengeLog != null) {
            challengeLogId = this.challengeLog.getId();
        }

        return FileDetailDto.builder()
                .id(this.id)
                .key(this.key)
                .createDtm(this.createDtm)
                .memberId(this.member.getId())
                .challengeCardId(challengeCardId)
                .challengeLogId(challengeLogId)
                .url(this.url).build();
    }

    public void assignChallengeLog(ChallengeLog challengeLog) {
        this.challengeLog = challengeLog;
    }

    public void assignChallengeCard(ChallengeCard challengeCard) {
        this.challengeCard = challengeCard;
    }

}
