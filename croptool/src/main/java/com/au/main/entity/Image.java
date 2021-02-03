package com.au.main.entity;

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

    @Column(length = 3000)
    private byte[] imageFileData;

    @Column
    private Integer employeeId;

}
