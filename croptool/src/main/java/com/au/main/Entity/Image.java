package com.au.main.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageId;

    @Column
    private String imageFileName;

    @Column
    private String imageFileType;

    @Lob
    private byte[] imageFileData;

    @Column
    private Integer employeeId;

}
