package com.contact.tracing.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class QueryResponse {
    @SerializedName("response")
    private final ResponseDataModel responseDataModel;

    public QueryResponse(ResponseDataModel responseDataModel) {
        this.responseDataModel = responseDataModel;
    }
}
