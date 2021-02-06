package com.au.main.service;

import com.au.main.request.ImageWrapper;
import com.au.main.response.BulkImageResponse;

import java.util.LinkedHashSet;

public interface ImageService {

    boolean saveToDB(ImageWrapper imageWrapper);
    BulkImageResponse bulkEditSave(LinkedHashSet<ImageWrapper> imageWrapperSet);

}
