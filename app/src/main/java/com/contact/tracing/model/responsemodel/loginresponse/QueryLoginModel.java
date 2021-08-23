package com.contact.tracing.model.responsemodel.loginresponse;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class QueryLoginModel {

    @SerializedName("response")
    private  LoginResponseModel loginResponseModel;


    public QueryLoginModel(LoginResponseModel loginResponseModel) {
        this.loginResponseModel = loginResponseModel;
    }
}
