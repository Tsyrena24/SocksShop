package com.sockshop.sockshopcoursework3.service.impl;

import com.sockshop.sockshopcoursework3.service.FileSocksService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
@Service
public class FileSocksServiceImpl implements FileSocksService {
    @Value("src/main/resources")
    private String socksFilePath;
    @Value("socks.json")
    private String socksFileName;

    @Override
    public boolean cleanData() {
        try {
            Path path = Path.of(socksFilePath, socksFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveToFile(String json) {
        try {
            cleanData();
            Files.writeString(Path.of(socksFilePath, socksFileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFile() {
        try {
            return Files.readString(Path.of(socksFilePath, socksFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public File getDataFile() {
        return new File(socksFilePath + "/" + socksFileName);
    }

    @Override
    public boolean uploadDataFile(MultipartFile file) {
        cleanData();
        File fileData = getDataFile();
        try (FileOutputStream fos = new FileOutputStream(fileData)) {
            IOUtils.copy(file.getInputStream(), fos);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }







}
