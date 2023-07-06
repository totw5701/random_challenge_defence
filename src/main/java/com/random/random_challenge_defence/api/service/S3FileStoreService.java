package com.random.random_challenge_defence.api.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.random.random_challenge_defence.api.dto.file.S3UploadFileDto;
import com.random.random_challenge_defence.domain.file.S3File;
import com.random.random_challenge_defence.domain.file.S3FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RequiredArgsConstructor
public class S3FileStoreService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;
    private final S3FileRepository s3FileRepository;

    @Transactional
    public S3UploadFileDto uploadFile(String bucket, MultipartFile file, String dir) throws Exception {
        String uuid = UUID.randomUUID().toString();
        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        String key = dir + uuid;
        try{
            amazonS3Client.putObject(bucket, key, file.getInputStream(), metadata);
        } catch (Exception e){
            throw new Exception("파일 업로드에 실패하였습니다.");
        }

        S3File fileInfo = S3File.builder()
                .url(amazonS3Client.getUrl(bucket, key).toString())
                .createDtm(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                .key(key).build();

        s3FileRepository.save(fileInfo);
        return fileInfo.toDto();
    }

}
