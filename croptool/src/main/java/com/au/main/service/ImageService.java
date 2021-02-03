package com.au.main.service;

import com.au.main.request.ImageWrapper;
import com.au.main.response.BulkImageResponse;

import java.util.Set;

public interface ImageService {

    boolean saveToDB(ImageWrapper imageWrapper);
    BulkImageResponse bulkEditSave(Set<ImageWrapper> imageWrapperSet);

}
