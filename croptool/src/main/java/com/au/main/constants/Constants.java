package com.au.main.constants;


public class Constants {

    private Constants(){
        throw new IllegalStateException("Constants utility class!");
    }

    private static final String[] EDITED_IMAGE_FILE_NAMES = {"Chris_edited.jpg", "paul walker_edited.jpg", "Sherlock_edited.jpg", "tom_edited.jpg", "tony_edited.jpg" };

    public static final String EDITED_IMAGE_FOLDER = "/dummyResponseImages/";

    public static final Integer FAILURE_EMPLOYEE_ID = -1;

    public static final String EMPTY_RESPONSE_STRING = "Nil";

    public static final int BOUND_FOR_DUMMY_IMAGES = 4;

    public static final int BOUND_OFFSET = 1;

    public static final String MANAGER_ROLE = "manager";

    public static final String EMPLOYEE_ROLE = "employee";


    public static final String SIGNUP_SUCCESS_MESSAGE = "Sign up successful!";

    public static final String SIGNUP_EMAIL_DUPLICATE_MESSAGE = "Email is duplicate";

    public static final String LOGIN_SUCCESS_MESSAGE = "Login Success!";

    public static final String LOGIN_FAILED_MESSAGE = "Login failed!, Invalid credentials";

    public static final String LOGIN_FAILED_ROLE = "Invalid!";

    public static final String SUBORDINATE_SUCCESS_MESSAGE = "Subordinates fetch success!";

    public static final String SUBORDINATE_FAILURE_MESSAGE = "Subordinates fetch failed!";

    public static final String IMAGE_SAVED_MESSAGE = "Received image saved!";

    public static final String INVALID_USER_MESSAGE = "User invalid!";

    public static final String IMAGE_EDIT_FAILED_MESSAGE = "Couldn't edit image";

    public static final String DUMMY_IMAGE_TYPE = "image/jpeg";

    public static final String BULK_IMAGE_FAILURE_MESSAGE = "Process couldn't be completed!";

    public static final String BULK_IMAGE_SUCCESS_MESSAGE = "Process successfully completed!";

    public static final String ADMIN_GET_ALL_DATA_SUCCESS_MESSAGE = "All employee data fetched successfully!";

    public static final String ADMIN_GET_ALL_DATA_FAILURE_MESSAGE = "All employee data fetch failed!!";

    public static final String ASSIGN_MANAGER_SUCCESS_MESSAGE = "Manager successfully assigned to employee!";

    public static final String ASSIGN_MANAGER_FAILURE_MESSAGE = "Failed to assign manager to employee!";

    public static String[] getEditedImageFileNames(){
        return EDITED_IMAGE_FILE_NAMES;
    }

}
