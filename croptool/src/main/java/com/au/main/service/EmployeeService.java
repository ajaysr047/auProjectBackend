package com.au.main.service;

import com.au.main.request.Credentials;
import com.au.main.request.EmployeeSignUp;
import com.au.main.request.ImageWrapper;
import com.au.main.response.EditedImage;
import com.au.main.response.LoginResponse;
import com.au.main.response.SignupResponse;
import com.au.main.response.SubordinatesResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;


public interface EmployeeService {
    SignupResponse addEmployee(EmployeeSignUp employee) throws IOException, NoSuchAlgorithmException;
    LoginResponse login(Credentials credentials);
    EditedImage getEditedImage(ImageWrapper imageWrapper);
    SubordinatesResponse getSubordinates(Integer managerId);
}
