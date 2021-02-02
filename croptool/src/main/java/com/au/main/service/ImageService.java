package com.au.main.service;

import com.au.main.request.ImageWrapper;

public interface ImageService {

    boolean saveToDB(Integer employeeId, ImageWrapper imageWrapper);

}
