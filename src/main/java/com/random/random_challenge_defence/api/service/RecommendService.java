package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.domain.challengecard.ChallengeCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class RecommendService {

    private final ChallengeCardRepository challengeCardRepository;

    public Long availableRandom(Integer memberLevel) {
        List<Long> idList = challengeCardRepository.findAllUnderMemberLever(memberLevel);

        Random random = new Random();
        int randomIndex = random.nextInt(idList.size());
        return idList.get(randomIndex);
    }
}
