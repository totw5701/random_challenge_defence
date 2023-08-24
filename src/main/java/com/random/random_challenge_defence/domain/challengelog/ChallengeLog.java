package com.random.random_challenge_defence.domain.challengelog;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.random.random_challenge_defence.api.dto.challengelog.ChallengeLogDetailDto;
import com.random.random_challenge_defence.api.dto.challengelog.ChallengeLogSubGoalDetailDto;
import com.random.random_challenge_defence.api.dto.file.FileDetailDto;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import com.random.random_challenge_defence.domain.challengelogsubgoal.ChallengeLogSubGoal;
import com.random.random_challenge_defence.domain.file.File;
import com.random.random_challenge_defence.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // Challenge 객체를 JSON으로 바꿀때 다른 객체에게 참조 받는 경우 ID 만 넘긴다.
public class ChallengeLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    private ChallengeLogStatus status;

    private String review;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChallengeCard challengeCard;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_log_id")
    private List<File> evidenceImages = new ArrayList<>();

    private String startDtm;
    private String endDtm;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ChallengeLogSubGoal> challengeLogSubGoals;

    public void setChallengeLogSubGoals(List<ChallengeLogSubGoal> subGoals) {
        this.challengeLogSubGoals = subGoals;
    }

    public ChallengeLogDetailDto toDetailDto() {

        List<ChallengeLogSubGoalDetailDto> collect = this.challengeLogSubGoals.stream().map((s) -> s.toDetail()).collect(Collectors.toList());
        List<FileDetailDto> evidences = new ArrayList<>();
        if (this.evidenceImages != null) {
            evidences = this.evidenceImages.stream().map(s -> s.toDto()).collect(Collectors.toList());
        }
        return ChallengeLogDetailDto.builder()
                .id(this.id)
                .fileDetailDto(evidences)
                .status(this.status)
                .review(this.review)
                .memberId(this.member.getId())
                .challengeCardId(this.challengeCard.getId())
                .challengeLogSubGoalDetailDtos(collect)
                .build();
    }

    public void challengeSuccess(){
        this.status = ChallengeLogStatus.SUCCESS;
        this.endDtm = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public void challengeSkip() {
        this.status = ChallengeLogStatus.PAUSE;
    }

    public void challengeRetry() {
        this.status = ChallengeLogStatus.READY;
        this.startDtm = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

}
