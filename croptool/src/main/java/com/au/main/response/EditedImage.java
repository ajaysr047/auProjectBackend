package com.au.main.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditedImage {

    private Integer employeeId;

    private byte[] imageFileData;

    private String downloadURI;

    private String message;

    private Boolean isEdited;
}
