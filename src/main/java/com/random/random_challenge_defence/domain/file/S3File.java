package com.random.random_challenge_defence.domain.file;

import com.random.random_challenge_defence.api.dto.file.S3UploadFileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class S3File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String key;
    private String url;
    private String createDtm;

    public S3UploadFileDto toDto() {
        return S3UploadFileDto.builder()
                .createDtm(this.createDtm)
                .key(this.key)
                .url(this.url).build();
    }
}
