package com.example.myapplication.art_dev;

import java.io.Serializable;

public class User  implements Serializable {
    private static final long serialVersionUID = 7697216180651090765L;

    public User(String userId, String sex, boolean isMale) {
        this.userId = userId;
        this.sex = sex;
        this.isMale = isMale;
    }

    private String userId;
    private String sex;
    private boolean isMale;

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", sex='" + sex + '\'' +
                ", isMale=" + isMale +
                '}';
    }


}
