package com.au.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;

    @Column
    private String employeeName;

    @Column
    private String email;

    @Column
    private String password;

    @Column(length = 100000)
    private  byte[] editedImage;

    @OneToMany(mappedBy = "employeeId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Image> newImages = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @OneToMany(mappedBy = "manager")
    @JsonIgnore
    private  Set<Employee> subordinateEmployees = new HashSet<>();

    @Column
    private String role;

    public Employee(String employeeName, String email, String password, String role, byte[] editedImage) {
        this.employeeName = employeeName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.editedImage = editedImage;
    }
}
