package com.au.main.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageWrapper {

    private Integer employeeId;

    private String imageFileName;

    private String imageFileType;

    private byte[] imageFileData;
}
