package com.random.random_challenge_defence.api.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.random.random_challenge_defence.api.dto.file.EvidenceDetailDto;
import com.random.random_challenge_defence.domain.file.File;
import com.random.random_challenge_defence.domain.file.FileRepository;
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
    public EvidenceDetailDto uploadFile(String bucket, MultipartFile file, String dir) throws Exception {
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

        File fileInfo = File.builder()
                .url(amazonS3Client.getUrl(bucket, key).toString())
                .createDtm(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                .key(key).build();

        fileRepository.save(fileInfo);
        return fileInfo.toDto();
    }

    @Transactional
    public EvidenceDetailDto uploadFile(MultipartFile file, String dir) throws Exception {
        return uploadFile(bucket, file, dir);
    }

}
