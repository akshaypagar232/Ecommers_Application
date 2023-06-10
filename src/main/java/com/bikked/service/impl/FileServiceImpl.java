package com.bikked.service.impl;

import com.bikked.exceptions.BadApiRequest;
import com.bikked.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        String originalFilename = file.getOriginalFilename();

        log.info("Initiated request in service for upload file with filename : {}", originalFilename);

        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = filename + extension;
        String fullPathWithFileName = path + fileNameWithExtension;

        log.info("full image path : {}", fullPathWithFileName);

        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

            File folder = new File(path);

            if (!folder.exists()) {

                folder.mkdirs();
            }

            //upload

            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));

            log.info("Completed request service for upload file with filename : {}", originalFilename);

            return fileNameWithExtension;

        } else {

            throw new BadApiRequest("File with this" + extension + "not allowed !!");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        log.info("Initiated request in service for get Resource with name : {}", name);

        String fullPath = path + File.separator + name;

        FileInputStream inputStream = new FileInputStream(fullPath);

        log.info("Completed request service for get Resource with name : {}", name);

        return inputStream;
    }
}
