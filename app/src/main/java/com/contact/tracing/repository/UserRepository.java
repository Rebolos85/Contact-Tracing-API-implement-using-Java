package com.contact.tracing.repository;

import android.content.Context;
import android.widget.Toast;
import com.contact.tracing.adapter.retrofit.RetrofitUserClient;
import com.contact.tracing.model.OtpModel;
import com.contact.tracing.model.QueryResponse;
import com.contact.tracing.model.UserModel;
import com.contact.tracing.service.RegistrationServiceDialog;
import com.contact.tracing.service.ResponseBodyService;
import com.contact.tracing.service.UserService;
import com.contact.tracing.service.serviceimp.ResponseBodyImp;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.contact.tracing.validator.UserCredentialsValidators.isValidPassword;

public class UserRepository {

    private TextInputLayout emailInputLayout;
    private RegistrationServiceDialog registrationServiceDialog;
    private Context context;
    private TextInputEditText firstPassword;
    private TextInputEditText confirmPassword;


    public UserRepository(TextInputLayout emailInputLayout, RegistrationServiceDialog registrationServiceDialog, Context context, TextInputEditText firstPassword, TextInputEditText confirmPassword) {
        this.emailInputLayout = emailInputLayout;
        this.registrationServiceDialog = registrationServiceDialog;
        this.context = context;
        this.firstPassword = firstPassword;
        this.confirmPassword = confirmPassword;
    }

    public void createAccount(UserModel userModel) {
        UserService createAccountRequest = RetrofitUserClient.getRetrofitUserClient().create(UserService.class);


        Call<QueryResponse> createAccount = createAccountRequest.createAccountRequest(userModel);
        createAccount.enqueue(new Callback<QueryResponse>() {
            @Override
            public void onResponse(Call<QueryResponse> call, Response<QueryResponse> response) {
                try {
                    switch (response.body().getResponseDataModel().getStatusCode()) {
                        case "200":
                            registrationServiceDialog.showSuccessOtpVerification();
                            break;
                        case "400":
                            registrationServiceDialog.showInvalidOtpVerification();
                            break;
                    }

                } catch (NullPointerException error) {
                    Toast.makeText(context, "Error " + response.body().getResponseDataModel().getStatusCode(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QueryResponse> call, Throwable t) {
                registrationServiceDialog.showInternetConnectDialog();
            }
        });

    }

    public void generateOtp(OtpModel otpModel) {

        String firstPasswordInput = firstPassword.getText().toString();
        String confirmPasswordInput = confirmPassword.getText().toString();

        ResponseBodyService responseBodyService = new ResponseBodyImp();
        responseBodyService.getServerBodyResponse();
        UserService generateOtpRequest = RetrofitUserClient.getRetrofitUserClient().create(UserService.class);
        Call<com.contact.tracing.model.QueryResponse> generateAnOtp = generateOtpRequest.sentOtp(otpModel);
        generateAnOtp.enqueue(new Callback<QueryResponse>() {
            @Override
            public void onResponse(Call<QueryResponse> call, Response<QueryResponse> response) {

                try {
                    if (response.body().getResponseDataModel().getStatusCode().equals("201") && isValidPassword(firstPasswordInput) && isValidPassword(confirmPasswordInput)) {
                        registrationServiceDialog.showOtpValidationDialog();
                    } else if (response.body().getResponseDataModel().getStatusCode().equals("422")) {
                        registrationServiceDialog.showFailDialog();
                    }
                } catch (NullPointerException error) {
                    emailInputLayout.setError("Please input a valid email");
                    Toast.makeText(context, "Please input a valid email address", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<QueryResponse> call, Throwable t) {
                Toast.makeText(context, "server response " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                registrationServiceDialog.showInternetConnectDialog();

            }
        });


    }


}



