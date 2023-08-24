package com.random.random_challenge_defence.testtool;


import com.random.random_challenge_defence.domain.challengecard.ChallengeCard;
import com.random.random_challenge_defence.domain.challengecardcategory.ChallengeCardCategory;
import com.random.random_challenge_defence.domain.challengecardsubgoal.ChallengeCardSubGoal;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLog;
import com.random.random_challenge_defence.domain.challengelog.ChallengeLogStatus;
import com.random.random_challenge_defence.domain.file.File;
import com.random.random_challenge_defence.domain.member.Member;
import com.random.random_challenge_defence.domain.member.MemberRole;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestTools {

    public Member createDummyMember(MemberRole role, Long id, String email) {

        Member member = Member.builder()
                .id(id)
                .email(email)
                .password("dummyPwd")
                .memberRole(role)
                .nickname("dummyNickname")
                .picture("")
                .build();
        return member;
    }

    public File createDummyS3File() {
        return File.builder()
                .key("test_key")
                .createDtm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
                .url("https://velog.velcdn.com/images/totw5701/post/79e1f118-7ff2-44be-ba05-01c7c693245f/image.png")
                .id(1L)
                .build();
    }

    public ChallengeCardCategory createChallengeCardCategory() {
        return ChallengeCardCategory.builder()
                .description("test challenge card category")
                .title("test category")
                .id(1L).build();
    }



    public ChallengeCard createDummyChallengeCard(File file, ChallengeCardCategory challengeCardCategory ) {

        ChallengeCard challengeCard = ChallengeCard.builder()
                .challengeCardCategory(challengeCardCategory)
                .image(file)
                .assignScore(5)
                .createDtm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
                .difficulty(5)
                .finalGoal("해발 1500m에 위치하기")
                .id(1L).description("가장 높은곳에 위치해보자").build();

        ChallengeCardSubGoal challengeCardSubGoal = ChallengeCardSubGoal.builder()
                .challengeCard(challengeCard)
                .subGoal("집에서 가장 가까우면서 해발 1500m가 높은 위치를 찾기")
                .id(1L).build();

        List<ChallengeCardSubGoal> subGoals = new ArrayList<>();
        subGoals.add(challengeCardSubGoal);

        challengeCard.assignSubGoals(subGoals);

        return challengeCard;
    }

    public ChallengeLog createDummyChallengeLog(Member member, ChallengeCard challengeCard) {
        return ChallengeLog.builder()
                .challengeCard(challengeCard)
                .status(ChallengeLogStatus.READY)
                .member(member)
                .startDtm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
                .build();
    }


}
