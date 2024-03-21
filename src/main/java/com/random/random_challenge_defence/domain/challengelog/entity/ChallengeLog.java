package com.random.random_challenge_defence.domain.challengelog.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogStatus;
import com.random.random_challenge_defence.domain.challengelog.dto.ChallengeLogDetailDto;
import com.random.random_challenge_defence.domain.challengelog.dto.ChallengeLogSubGoalDetailDto;
import com.random.random_challenge_defence.domain.file.dto.FileDetailDto;
import com.random.random_challenge_defence.domain.challengecard.entity.ChallengeCard;
import com.random.random_challenge_defence.domain.challengelogsubgoal.entity.ChallengeLogSubGoal;
import com.random.random_challenge_defence.domain.file.entity.File;
import com.random.random_challenge_defence.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.text.SimpleDateFormat;
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
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_card_id")
    private ChallengeCard challengeCard;

    @OneToMany(mappedBy = "challengeLog", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<File> evidenceImages = new ArrayList<>();

    private String startDtm;
    private String endDtm;

    @OneToMany(mappedBy = "challengeLog", fetch = FetchType.LAZY, orphanRemoval = true)
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
