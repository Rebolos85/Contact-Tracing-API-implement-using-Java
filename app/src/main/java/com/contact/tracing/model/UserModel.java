package com.contact.tracing.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class UserModel {
    @SerializedName("email")
    private String userEmail;
    @SerializedName("password")
    private String password;
    @SerializedName("username")
    private String username;
    @SerializedName("otp")
    private Integer otp;

}
