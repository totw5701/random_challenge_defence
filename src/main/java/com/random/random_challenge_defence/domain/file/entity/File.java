package com.random.random_challenge_defence.domain.file.entity;

import com.random.random_challenge_defence.domain.file.dto.FileDetailDto;
import com.random.random_challenge_defence.domain.challengelog.entity.ChallengeLog;
import com.random.random_challenge_defence.domain.member.entity.Member;
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
    private String fileKey;
    private String url;
    private String createDtm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_log_id")
    private ChallengeLog challengeLog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public FileDetailDto toDto() {

        Long challengeCardId = null;
        Long challengeLogId = null;

        if(this.challengeLog != null) {
            challengeLogId = this.challengeLog.getId();
        }

        return FileDetailDto.builder()
                .id(this.id)
                .key(this.fileKey)
                .createDtm(this.createDtm)
                .memberId(this.member.getId())
                .challengeCardId(challengeCardId)
                .challengeLogId(challengeLogId)
                .url(this.url).build();
    }

    public void assignChallengeLog(ChallengeLog challengeLog) {
        this.challengeLog = challengeLog;
    }
}
