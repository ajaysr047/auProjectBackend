package com.au.main.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    boolean saveToDB(Integer employeeId, MultipartFile image);

}
