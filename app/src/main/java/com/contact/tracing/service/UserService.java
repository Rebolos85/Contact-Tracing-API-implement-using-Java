package com.contact.tracing.service;

import com.contact.tracing.model.LoginModel;
import com.contact.tracing.model.OtpModel;
import com.contact.tracing.model.QueryResponse;
import com.contact.tracing.model.UserModel;
import com.contact.tracing.model.responsemodel.loginresponse.QueryLoginModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface UserService {

    @POST("/api/poms/register")
    Call<QueryResponse> createAccountRequest(@Body UserModel userModel);

    @POST("/api/poms/register/generate")
    Call<QueryResponse> sentOtp(@Body OtpModel otpModel);

    @POST("/api/poms/auth")
    Call<QueryLoginModel> validateLogin(@Body LoginModel loginModel);


    @GET("/api/auth/hello")
    Call<ResponseBody> validateToken(@Header("Authorization") String userTokenGenerated);


}
