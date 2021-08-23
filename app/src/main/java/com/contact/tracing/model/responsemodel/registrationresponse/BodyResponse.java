package com.contact.tracing.model.responsemodel.registrationresponse;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class BodyResponse {
    @SerializedName("email")
    private String email;
}
