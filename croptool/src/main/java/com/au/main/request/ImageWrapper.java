package com.au.main.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageWrapper {

    private String imageFileName;

    private String imageFileType;

    private byte[] imageFileData;
}