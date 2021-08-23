package com.contact.tracing.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter

public class ResponseDataModel {
    @SerializedName("timestramp")
    private String timeStramp;
    @SerializedName("message")
    private String message;
    @SerializedName("statusCode")
    private String statusCode;
    @SerializedName("traceId")
    private String traceId;
    @SerializedName("body")
    private final BodyResponse bodyResponse;

    public ResponseDataModel(BodyResponse bodyResponse) {
        this.bodyResponse = bodyResponse;
    }
}
