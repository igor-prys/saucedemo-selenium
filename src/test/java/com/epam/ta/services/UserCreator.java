package com.epam.ta.services;

import com.epam.ta.models.User;

public class UserCreator {

    public static final String USER_USERNAME = "user.username";
    public static final String USER_PASSWORD = "user.password";

    public static User withCredentialsFromProperty() {
        return new User(TestDataReader.getProperty(USER_USERNAME).orElse(""),
                TestDataReader.getProperty(USER_PASSWORD).orElse(""));
    }
}
