package com.contact.tracing.model.responsemodel.loginresponse;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class TokenBodyResponse {
    @SerializedName("token")
    private String token;
}
