package com.random.random_challenge_defence.api.service;

import com.random.random_challenge_defence.advice.ExceptionCode;
import com.random.random_challenge_defence.advice.exception.CustomException;
import com.random.random_challenge_defence.domain.file.File;
import com.random.random_challenge_defence.domain.file.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    private final FileRepository fileRepository;

    public List<File> validateOwner(List<Long> fileIds, String memberEmail) {
        List<File> fileList = getEntityListByIds(fileIds);
        for(File file : fileList) {
            if(!file.getMember().getEmail().equals(memberEmail)) {
                throw new CustomException(ExceptionCode.ACCESS_DENIED);
            }
        }
        return fileList;
    }

    public List<File> getEntityListByIds(List<Long> fileIds) {
        return fileRepository.findByIdIn(fileIds);
    }

}
