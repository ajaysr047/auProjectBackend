package com.au.main.response;

import com.au.main.request.ImageWrapper;
import lombok.Getter;
import lombok.Setter;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class BulkImageResponse {
    private boolean isSuccess;

    private Set<ImageWrapper> responseEditedImages = new LinkedHashSet<>();

    private String message;
}
