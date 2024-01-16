package com.random.random_challenge_defence.domain.file.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.random.random_challenge_defence.global.advice.ExceptionCode;
import com.random.random_challenge_defence.global.advice.exception.StackTraceCustomException;
import com.random.random_challenge_defence.domain.file.dto.FileDetailDto;
import com.random.random_challenge_defence.domain.file.entity.File;
import com.random.random_challenge_defence.domain.file.repository.FileRepository;
import com.random.random_challenge_defence.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3FileUploadService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;
    private final FileRepository fileRepository;

    @Transactional
    public FileDetailDto uploadFile(Member member, String bucket, MultipartFile file, String dir) throws Exception {
        String uuid = UUID.randomUUID().toString();
        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        String key = dir + uuid;
        try{
            amazonS3Client.putObject(bucket, key, file.getInputStream(), metadata);
        } catch (Exception e){
            throw new StackTraceCustomException(ExceptionCode.FILE_UPLOAD_FAIL, e);
        }

        File fileInfo = File.builder()
                .url(amazonS3Client.getUrl(bucket, key).toString())
                .member(member)
                .createDtm(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                .fileKey(key).build();

        fileRepository.save(fileInfo);
        return fileInfo.toDto();
    }

    @Transactional
    public FileDetailDto uploadFile(Member member, MultipartFile file, String dir) throws Exception {
        return uploadFile(member, bucket, file, dir);
    }

    @Transactional
    public void deleteFile(File image) {
        amazonS3Client.deleteObject(bucket, image.getFileKey());
        fileRepository.delete(image);
    }
}
