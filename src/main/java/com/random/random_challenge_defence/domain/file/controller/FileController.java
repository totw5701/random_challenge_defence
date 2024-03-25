package com.random.random_challenge_defence.domain.file.controller;

import com.random.random_challenge_defence.global.result.CommonResponse;
import com.random.random_challenge_defence.domain.file.dto.FileDetailDto;
import com.random.random_challenge_defence.global.result.ResponseService;
import com.random.random_challenge_defence.domain.file.service.S3FileUploadService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final S3FileUploadService s3FileUploadService;
    private final ResponseService responseService;

    @PostMapping("/upload/challenge-log")
    @ApiOperation(value = "챌린지 로그 이미지 파일 업로드", notes = "챌린지 로그의 인증 이미지 파일을 업로드합니다.")
    public CommonResponse<FileDetailDto> challengeLogFileUpload(@RequestParam("file") MultipartFile file) {
        FileDetailDto s3UploadFileDto = s3FileUploadService.uploadFile(file);
        return responseService.getResult(s3UploadFileDto);
    }
}
