package com.random.random_challenge_defence.api.dto.file;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class S3UploadFileDto {

    private Long id;
    private String key;
    private String url;

}
