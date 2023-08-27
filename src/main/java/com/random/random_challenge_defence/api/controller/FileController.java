package com.random.random_challenge_defence.api.controller;

import com.random.random_challenge_defence.api.dto.common.CommonResponse;
import com.random.random_challenge_defence.api.dto.file.FileDetailDto;
import com.random.random_challenge_defence.api.service.MemberService;
import com.random.random_challenge_defence.api.service.ResponseService;
import com.random.random_challenge_defence.api.service.S3FileUploadService;
import com.random.random_challenge_defence.domain.member.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    private final MemberService memberService;


    @PostMapping("/upload/challenge-card")
    @ApiOperation(value = "챌린지 카드 이미지 파일 업로드", notes = "챌린지 카드의 이미지 파일을 업로드합니다.")
    public CommonResponse challengeCardFileUpload(@RequestParam("file")MultipartFile file) {
        try {
            Member member = memberService.getLoginMember();
            FileDetailDto s3UploadFileDto = s3FileUploadService.uploadFile(member, file, "challenge-card/");
            return responseService.getResult(s3UploadFileDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseService.getFailResult("403", "파일 업로드에 실패했습니다.");
    }

    @PostMapping("/upload/challenge-log")
    @ApiOperation(value = "챌린지 로그 이미지 파일 업로드", notes = "챌린지 로그의 인증 이미지 파일을 업로드합니다.")
    public CommonResponse challengeLogFileUpload(@RequestParam("file")MultipartFile file) {
        try {
            Member member = memberService.getLoginMember();
            String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
            FileDetailDto s3UploadFileDto = s3FileUploadService.uploadFile(member, file, "challenge-log/" + today + "/");
            return responseService.getResult(s3UploadFileDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseService.getFailResult("403", "파일 업로드에 실패했습니다.");
    }

}
