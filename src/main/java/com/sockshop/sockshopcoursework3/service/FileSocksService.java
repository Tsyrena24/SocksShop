package com.sockshop.sockshopcoursework3.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileSocksService {
    boolean cleanData();

    boolean saveToFile(String json);

    String readFile();

    File getDataFile();

    boolean uploadDataFile(MultipartFile file);
}
