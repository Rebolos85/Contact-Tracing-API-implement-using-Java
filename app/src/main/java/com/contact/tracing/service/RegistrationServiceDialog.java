package com.contact.tracing.service;

import android.app.AlertDialog;
import android.content.Context;
import android.net.NetworkInfo;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.contact.tracing.model.QueryResponse;
import com.galenleo.widgets.CodeInputView;
import com.google.android.material.button.MaterialButton;
import com.royrodriguez.transitionbutton.TransitionButton;


import java.lang.reflect.InvocationTargetException;

import retrofit2.Response;


public interface RegistrationServiceDialog {

    void showSuccessOtpVerification();

    void showFailDialog();

    void showInvalidOtpVerification();

    void showMaintenanceApp();

    void listenersForSuccessOtpVerification(AlertDialog otpDialogVerification, Button otpButtonDialog, ImageView closeOtpDialog);

    void showInternetConnectDialog();

    void handleCancelDialogListeners(TextView cancelFailDialog, AlertDialog dialog);

    void listenersForInvalidOtpVerification(AlertDialog otpDialogVerification, Button otpButtonDialog, ImageView closeOtpDialog);

    void closeNoInternetDialog(ImageView closeInternetButton, MaterialButton wifi, MaterialButton mobileData, AlertDialog dialog);

    boolean isConnectedToInternet();

    void showOtpValidationDialog();

    void attachListenersDialog(AlertDialog dialog, TextView cancelOtp, TransitionButton
            verifyButton, CodeInputView codeInputView);

    void validateServerSentOtpDialog(Response<QueryResponse> response);



}
