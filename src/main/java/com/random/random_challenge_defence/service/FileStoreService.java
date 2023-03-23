package com.random.random_challenge_defence.service;

import com.random.random_challenge_defence.api.dto.file.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class FileStoreService {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {

        StringBuffer sb = new StringBuffer();
        sb.append(fileDir);
        sb.append("/");
        sb.append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        String dirPath = sb.toString();
        File folder = new File(dirPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        sb.append("/");
        sb.append(fileName);

        return sb.toString();
    }

    private String createStoreFileName(String fileName) {
        int idx = fileName.lastIndexOf(".");
        String ext = fileName.substring(idx + 1);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }


    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return new UploadFile(originalFilename, storeFileName);
    }

    public void deleteFile(String storeFileName) {
        File file = new File(getFullPath(storeFileName));
        if(file.exists()) file.delete();
    }
}
