package com.contact.tracing.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class LoginModel {
    @SerializedName("password")
    private String password;
    @SerializedName("username")
    private String username;
}
