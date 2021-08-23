package com.contact.tracing.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class OtpModel {
    @SerializedName("email")
    private String email;
    @SerializedName("otp")
    private Integer otp;
    @SerializedName("password")
    private String password;
    @SerializedName("username")
    private String username;
    @SerializedName("statusCode")
    private String statusCode;
    @SerializedName("message")
    private String message;
}
