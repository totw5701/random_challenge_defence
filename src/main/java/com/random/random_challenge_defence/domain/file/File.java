package com.random.random_challenge_defence.domain.file;

import com.random.random_challenge_defence.api.dto.file.EvidenceDetailDto;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLog;
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

    @ManyToOne
    private ChallengeLog challengeLog;

    @OneToOne
    private ChallengeCard challengeCard;

    public EvidenceDetailDto toDto() {
        return EvidenceDetailDto.builder()
                .id(this.id)
                .key(this.key)
                .createDtm(this.createDtm)
                .url(this.url).build();
    }

}
