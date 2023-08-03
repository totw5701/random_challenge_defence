package com.random.random_challenge_defence.domain.challengelog;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.random.random_challenge_defence.api.dto.challengelog.ChallengeLogDetailDto;
import com.random.random_challenge_defence.api.dto.challengelog.ChallengeLogSubGoalDetailDto;
import com.random.random_challenge_defence.api.dto.challengelog.ChallengeLogUpdateDto;
import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import com.random.random_challenge_defence.domain.challengelogsubgoal.ChallengeLogSubGoal;
import com.random.random_challenge_defence.domain.file.S3File;
import com.random.random_challenge_defence.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.text.SimpleDateFormat;
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

    private String evidence;

    @Enumerated(EnumType.STRING)
    private ChallengeLogStatus status;

    private String review;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChallengeCard challengeCard;

    @OneToOne
    private S3File evidenceImage;

    private String startDtm;
    private String endDtm;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_log_id")
    private List<ChallengeLogSubGoal> challengeLogSubGoals;

    public void setChallengeLogSubGoals(List<ChallengeLogSubGoal> subGoals) {
        this.challengeLogSubGoals = subGoals;
    }

    public ChallengeLogDetailDto toDetailDto() {

        List<ChallengeLogSubGoalDetailDto> collect = challengeLogSubGoals.stream().map((s) -> s.toDetail()).collect(Collectors.toList());

        return ChallengeLogDetailDto.builder()
                .id(this.id)
                .evidence(this.evidence)
                .status(this.status)
                .review(this.review)
                .memberId(this.member.getId())
                .challengeDetailDto(this.challengeCard.toDetailDto())
                .challengeLogSubGoalDetailDtos(collect)
                .image(this.evidenceImage != null ? this.evidenceImage.toDto() : null)
                .build();
    }

    public void update(ChallengeLogUpdateDto form) {
        if(form.getEvidence() != null) {
            this.evidence = form.getEvidence();
        }
        if(form.getStatus() != null) {
            this.status = form.getStatus();
        }
        if(form.getReview() != null) {
            this.review = form.getReview();
        }
    }

    public void updateImage(S3File image) {
        this.evidenceImage = image;
    }

    public void challengeSuccess(){
        this.status = ChallengeLogStatus.SUCCESS;
        this.endDtm = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

}
