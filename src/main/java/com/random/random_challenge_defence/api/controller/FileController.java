package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.dto.file.FileDetailDto;
import com.random.random_challenge_defence.api.service.ResponseService;
import com.random.random_challenge_defence.api.service.S3FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final S3FileUploadService s3FileUploadService;
    private final ResponseService responseService;


    @PostMapping
    @RequestMapping("/upload/challenge-card")
    public CommonResponse challengeCardFileUpload(@RequestParam("file")MultipartFile file) {
        try {
            FileDetailDto s3UploadFileDto = s3FileUploadService.uploadFile(file, "challenge-card/");
            return responseService.getResult(s3UploadFileDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseService.getFailResult("403", "파일 업로드에 실패했습니다.");
    }

    @PostMapping
    @RequestMapping("/upload/challenge-log")
    public CommonResponse challengeLogFileUpload(@RequestParam("file")MultipartFile file) {
        try {
            String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
            FileDetailDto s3UploadFileDto = s3FileUploadService.uploadFile(file, "challenge-log/" + today + "/");
            return responseService.getResult(s3UploadFileDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseService.getFailResult("403", "파일 업로드에 실패했습니다.");
    }

}
