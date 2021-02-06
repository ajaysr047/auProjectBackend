package com.au.main.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ImageWrapper {

    @NotNull(message = "Employee id cannot be empty!")
    private Integer employeeId;

    @NotBlank(message = "Image file name cannot be blank!")
    private String imageFileName;

    @NotBlank(message = "Image file type cannot be blank!")
    private String imageFileType;

    @NotEmpty(message = "Image data cannot be empty!")
    private byte[] imageFileData;
}
