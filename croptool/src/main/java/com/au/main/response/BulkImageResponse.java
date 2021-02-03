package com.au.main.response;

import com.au.main.request.ImageWrapper;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class BulkImageResponse {
    private Boolean isSuccess;

    private Set<ImageWrapper> responseEditedImages = new HashSet<>();

    private String message;
}
