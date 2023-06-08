package com.random.random_challenge_defence.service;

import com.random.random_challenge_defence.api.dto.file.UploadFile;
import com.random.random_challenge_defence.api.service.FileStoreService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class FileStoreServiceTest {

    @Autowired
    FileStoreService fileStoreService;

    @Test
    void fileStore() throws IOException {

        // Given
        File img = new File("D:/NAS/Jakesalad.jpg");
        MultipartFile mf = new MockMultipartFile("image","Jakesalad.jpg", "img",new FileInputStream(img));

        // When
        UploadFile uploadFile = fileStoreService.storeFile(mf);

        // Then
        Assertions.assertThat(uploadFile.getUploadFileName()).isEqualTo("Jakesalad.jpg");
        Assertions.assertThat(uploadFile.getStoreFileName()).isNotNull();

        File file = new File("D:/NAS/rcd/evidence/" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "/" + uploadFile.getStoreFileName());
        Assertions.assertThat(file.length()).isEqualTo(img.length());

    }

}