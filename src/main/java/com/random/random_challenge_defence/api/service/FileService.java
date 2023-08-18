package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.domain.challengelog.ChallengeLog;
import com.random.random_challenge_defence.domain.file.File;
import com.random.random_challenge_defence.domain.file.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public List<File> getFileListByIds(List<Long> fileIds) {
        return fileRepository.findByIdIn(fileIds);
    }

    @Transactional
    public void assignChallengeLog(File file, ChallengeLog challengeLog) {
        file.assignChallengeLog(challengeLog);
    }
}
