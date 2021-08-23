package com.contact.tracing.repository;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.contact.tracing.Dashboard;
import com.contact.tracing.adapter.retrofit.RetrofitToken;
import com.contact.tracing.adapter.retrofit.RetrofitUserClient;
import com.contact.tracing.model.LoginModel;
import com.contact.tracing.model.responsemodel.loginresponse.QueryLoginModel;

import com.contact.tracing.service.ResponseBodyService;
import com.contact.tracing.service.UserService;
import com.contact.tracing.service.serviceimp.ResponseBodyImp;
import com.contact.tracing.session.SessionManager;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    Context context;
    ResponseBodyService responseBodyService = new ResponseBodyImp();
    private TextInputLayout usernameLayout;
    private TextInputLayout passwordLayout;
    private SessionManager sessionManager;

    public LoginRepository(Context context, TextInputLayout usernameLayout, TextInputLayout passwordLayout, SessionManager sessionManager) {
        this.context = context;
        this.usernameLayout = usernameLayout;
        this.passwordLayout = passwordLayout;
        this.sessionManager = sessionManager;

    }


    public void validateUserLoginCredentials(LoginModel loginModel) {

        responseBodyService.getLoginServerBodyResponse();
        UserService userService = RetrofitUserClient.getRetrofitLoginClient().create(UserService.class);
        Call<QueryLoginModel> validateLoginCredentials = userService.validateLogin(loginModel);


        validateLoginCredentials.enqueue(new Callback<QueryLoginModel>() {
            @Override
            public void onResponse(Call<QueryLoginModel> call, @NotNull Response<QueryLoginModel> response) {
                try {


                    if (response.body().getLoginResponseModel().getStatusCode().equals("401")) {
                        Toast.makeText(context, "Invalid password", Toast.LENGTH_SHORT).show();
                        passwordLayout.setError("Incorrect password");
                    } else if (response.body().getLoginResponseModel().getStatusCode().equals("200")) {
                        Toast.makeText(context, "SUCCESS KA BAY", Toast.LENGTH_SHORT).show();
                        passwordLayout.setError(null);
                        sessionManager.saveUserAuthToken(response.body().getLoginResponseModel().getBodyLoginResponse().getToken());
                        fetchTokenGenerated();
                    } else if (response.body().getLoginResponseModel().getStatusCode().equals("404")) {
                        Toast.makeText(context, "Account credential not found", Toast.LENGTH_SHORT).show();
                    }


                } catch (NullPointerException error) {

                }
            }

            @Override
            public void onFailure(Call<QueryLoginModel> call, Throwable t) {
                Toast.makeText(context, "NO INTERNET ERROR" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void fetchTokenGenerated() {
        UserService tokenGenerated = RetrofitToken.validateOtpToken().create(UserService.class);
        Call<ResponseBody> validateToken = tokenGenerated.validateToken("Bearer " + sessionManager.fetchAuthToken());
        validateToken.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {

                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "SUCCESS IMOHA TOKEN BAY", Toast.LENGTH_SHORT).show();
                        Intent dashbaord = new Intent(context, Dashboard.class);
                        context.startActivity(dashbaord);
                    } else {
                        Toast.makeText(context, "FAILED BAY BETTER LUCK NEXT TIME", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "NO INTERNET " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
