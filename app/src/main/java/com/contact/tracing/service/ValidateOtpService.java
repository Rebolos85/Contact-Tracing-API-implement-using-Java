package com.contact.tracing.service;

import android.app.AlertDialog;
import android.widget.TextView;

import com.contact.tracing.model.UserModel;
import com.galenleo.widgets.CodeInputView;
import com.royrodriguez.transitionbutton.TransitionButton;

public interface ValidateOtpService {

    void verifyOtp(TransitionButton verifyButton, CodeInputView codeInputView);


    UserModel setUserRequest(CodeInputView codeInputView);

//    void showOtpValidationDialog();
//    void attachListenersDialog(AlertDialog dialog, TextView cancelOtp, TransitionButton
//            verifyButton, CodeInputView codeInputView);

}
