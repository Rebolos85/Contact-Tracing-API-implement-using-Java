package com.contact.tracing.service.serviceimp;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import com.contact.tracing.model.UserModel;
import com.contact.tracing.repository.UserRepository;
import com.galenleo.widgets.CodeInputView;
import com.google.android.material.textfield.TextInputEditText;
import com.royrodriguez.transitionbutton.TransitionButton;


public class ValidateOtpImp  {


    private  static TextInputEditText email;
    private static TextInputEditText password;
    private Context context;
    private static TextInputEditText username;
    private static UserRepository userRepository;
    public ValidateOtpImp(Context context, UserRepository userRepository, TextInputEditText username, TextInputEditText email, TextInputEditText password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.context = context;
        ValidateOtpImp.userRepository = userRepository;

    }


    public static void verifyOtp(TransitionButton verifyButton, CodeInputView codeInputView) {
        android.os.Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                verifyButton.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);

                userRepository.createAccount(setUserRequest(codeInputView));

            }
        }, 3000);

    }


    public static UserModel setUserRequest(CodeInputView codeInputView) {
        UserModel userModel = new UserModel();
        userModel.setUserEmail(email.getText().toString());
        userModel.setPassword(password.getText().toString());
        userModel.setOtp(Integer.parseInt(codeInputView.getText()));
        userModel.setUsername(username.getText().toString());

        return userModel;
    }


}
