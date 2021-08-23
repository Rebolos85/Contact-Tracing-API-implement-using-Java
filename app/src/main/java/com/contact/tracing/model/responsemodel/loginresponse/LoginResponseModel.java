package com.contact.tracing.model.responsemodel.loginresponse;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class LoginResponseModel {
    @SerializedName("statusCode")
    private String statusCode;
    @SerializedName("message")
    private String message;
    @SerializedName("body")
    private  TokenBodyResponse bodyLoginResponse;

    public LoginResponseModel(TokenBodyResponse bodyLoginResponse) {
        this.bodyLoginResponse = bodyLoginResponse;
    }


}
