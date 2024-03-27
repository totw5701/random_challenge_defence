package com.random.random_challenge_defence.domain.file.service;

import com.random.random_challenge_defence.global.advice.ExceptionCode;
import com.random.random_challenge_defence.global.advice.exception.CustomException;
import com.random.random_challenge_defence.domain.file.entity.File;
import com.random.random_challenge_defence.domain.file.repository.FileRepository;
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
        List<File> fileList = fileRepository.findByIdIn(fileIds);
        fileList.forEach((file) -> {
            if(!file.getMember().getEmail().equals(memberEmail)) {
                throw new CustomException(ExceptionCode.ACCESS_DENIED);
            }
        });
        return fileList;
    }
}
