package com.contact.tracing.service.serviceimp;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;


import android.os.CountDownTimer;


import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.contact.tracing.R;
import com.contact.tracing.service.RegistrationServiceDialog;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;

import com.contact.tracing.model.QueryResponse;

import com.contact.tracing.service.RegistrationServiceDialog;
import com.contact.tracing.service.ResponseBodyService;

import com.contact.tracing.service.TimerOtp;

import com.galenleo.widgets.CodeInputView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.royrodriguez.transitionbutton.TransitionButton;
import static com.contact.tracing.service.serviceimp.ValidateOtpImp.verifyOtp;
import retrofit2.Response;
import static com.contact.tracing.validator.UserCredentialsValidators.isValidPassword;



public class RegisterDialogImp implements RegistrationServiceDialog {

    private Context context;
    private TextInputLayout emailLayout;
    private TextInputEditText firstPassword;
    private TextInputEditText confirmPassword;

    public RegisterDialogImp(Context context, TextInputLayout emailLayout, TextInputEditText firstPassword, TextInputEditText confirmPassword) {
        this.context = context;
        this.emailLayout = emailLayout;
        this.firstPassword = firstPassword;
        this.confirmPassword = confirmPassword;


    }

    @Override
    public void showSuccessOtpVerification() {
        View successOtpDialog = View.inflate(context, R.layout.success_registration_dialog, null);
        Button button = successOtpDialog.findViewById(R.id.continue_button);
        ImageView imageView = successOtpDialog.findViewById(R.id.close_dialog_sucess);

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        final AlertDialog.Builder verifiedOtpDialog = new AlertDialog.Builder(context);
        verifiedOtpDialog.setCancelable(false);
        verifiedOtpDialog.setView(successOtpDialog);
        final AlertDialog dialog = verifiedOtpDialog.create();

        dialog.show();
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        listenersForSuccessOtpVerification(dialog, button, imageView);
        Log.d("MainActivity", "SUCCESS");

    }

    @Override
    public void showInvalidOtpVerification() {
        View failedOtpVerification = LayoutInflater.from(context).inflate(R.layout.failed_otp_feedback_dialog, null);
        Button tryAgainOtp = failedOtpVerification.findViewById(R.id.try_again);
        ImageView closeError = failedOtpVerification.findViewById(R.id.close_dialog_error);
        final AlertDialog.Builder verifiedOtpDialog = new AlertDialog.Builder(context);
        verifiedOtpDialog.setCancelable(false);
        verifiedOtpDialog.setView(failedOtpVerification);
        final AlertDialog dialog = verifiedOtpDialog.create();
        dialog.show();
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        listenersForInvalidOtpVerification(dialog, tryAgainOtp, closeError);
        Log.d("MainActivity", "INVALID OTP");
    }

    @Override
    public void listenersForSuccessOtpVerification(AlertDialog otpDialogVerification, Button otpButtonDialog, ImageView closeOtpDialog) {
        otpButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpDialogVerification.cancel();
            }
        });
        closeOtpDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpDialogVerification.cancel();
            }
        });
    }

    @Override
    public void showFailDialog() {

        View failDialog = View.inflate(context, R.layout.alert_dialog_registration_feedback, null);
        ImageView imageView = failDialog.findViewById(R.id.feedback_icons);
        TextView header = failDialog.findViewById(R.id.header_description);
        header.setText(R.string.exist_email);

        TextView description = failDialog.findViewById(R.id.description);
        TextView cancelDialog = failDialog.findViewById(R.id.cancel_dialog);
        description.setText(R.string.email_exist);

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        final AlertDialog.Builder showFailDialog = new AlertDialog.Builder(context);


        showFailDialog.setCancelable(false);

        showFailDialog.setView(failDialog);
        final AlertDialog dialog = showFailDialog.create();

        dialog.show();
        handleCancelDialogListeners(cancelDialog, dialog);

        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }


    @Override
    public void showMaintenanceApp() {
        View maintenanceDialog = View.inflate(context, R.layout.dialog_maintenance, null);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        final AlertDialog.Builder showMaintenanceDialog = new AlertDialog.Builder(context, R.style.MyDialogTheme);

        showMaintenanceDialog.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        showMaintenanceDialog.setView(maintenanceDialog);
        final AlertDialog dialog = showMaintenanceDialog.create();
        dialog.show();
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }


    @Override
    public void showInternetConnectDialog() {
        View noInternetDialog = View.inflate(context, R.layout.no_internet_dialog, null);
        ImageView closeDialog = noInternetDialog.findViewById(R.id.close_button_dialog);
        MaterialButton wifi = noInternetDialog.findViewById(R.id.wifi_button);
        MaterialButton mobileData = noInternetDialog.findViewById(R.id.mobile_data);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        final AlertDialog.Builder showInternetDialog = new AlertDialog.Builder(context);


        showInternetDialog.setView(noInternetDialog);
        final AlertDialog dialog = showInternetDialog.create();
        dialog.show();

        closeNoInternetDialog(closeDialog, wifi, mobileData, dialog);

    }

    @Override
    public void handleCancelDialogListeners(TextView cancelFailDialog, AlertDialog dialog) {
        cancelFailDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }


    @Override
    public void listenersForInvalidOtpVerification(AlertDialog otpDialogVerification, Button otpButtonDialog, ImageView closeOtpDialog) {
        otpButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpDialogVerification.cancel();
            }
        });
        closeOtpDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpDialogVerification.cancel();
            }
        });
    }

    @Override
    public void closeNoInternetDialog(ImageView closeInternetButton, MaterialButton wifi, MaterialButton mobileData, AlertDialog dialog) {
        closeInternetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isConnectedToInternet()) {
                    WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

                    wifi.setWifiEnabled(true);
                }
            }
        });
        mobileData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                ComponentName cName = new ComponentName("com.android.phone", "com.android.phone.Settings");
                intent.setComponent(cName);
                context.startActivity(intent);


            }
        });

    }

    @Override
    public boolean isConnectedToInternet() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();

        if (isConnected) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(true);
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void showOtpValidationDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View otpVerificationDialog = layoutInflater.inflate(R.layout.alert_dialog_otp, null);

        TextView countDownText = (TextView) otpVerificationDialog.findViewById(R.id.count_down_timer);
        ImageView imageView = (ImageView) otpVerificationDialog.findViewById(R.id.alarm_image);
        TextView otpDescription = otpVerificationDialog.findViewById(R.id.otp_text);
        TimerOtp timerOtp = new TimerImp(countDownText, otpDescription, imageView);
        timerOtp.startOtpCountDown();

        final CodeInputView codeInputView = otpVerificationDialog.findViewById(R.id.verify_code);
        TransitionButton verifyCodeButton = otpVerificationDialog.findViewById(R.id.verify_button);
        TextView cancelOTP = otpVerificationDialog.findViewById(R.id.cancel_otp);
        TextView resendOTP = otpVerificationDialog.findViewById(R.id.resend_otp);

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);

        mBuilder.setCancelable(false);

        mBuilder.setView(otpVerificationDialog);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        attachListenersDialog(dialog, cancelOTP, verifyCodeButton, codeInputView);


    }

    @Override
    public void attachListenersDialog(AlertDialog dialog, TextView cancelOtp, TransitionButton verifyButton, CodeInputView codeInputView) {
        cancelOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyButton.startAnimation();

                verifyOtp(verifyButton, codeInputView);

            }
        });
    }


    @Override
    public void validateServerSentOtpDialog(Response<QueryResponse> response) {

        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)

                .setTitleText("Please wait!")
                .setContentText("Your request is still on process just wait for the server to response :)");
        pDialog.show();


        pDialog.setCancelable(false);
        new CountDownTimer(5000, 1000) {

            int counter = -1;

            @Override
            public void onTick(long millisUntilFinished) {

                counter++;
                switch (counter) {
                    case 0:
                        pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.blue_btn_bg_color));
                        break;
                    case 1:
                        pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorDark));
                        break;
                    case 3:
                        pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.swipe_text_color));
                    case 4:
                        pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.button_color));
                        break;
                    case 5:
                        pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.successColor));
                        break;

                }


            }

            @Override
            public void onFinish() {
                counter = -1;

                try {
                    ResponseBodyService responseBodyService = new ResponseBodyImp();
                    responseBodyService.getServerBodyResponse();
                    String firstPasswordInput = firstPassword.getText().toString();
                    String secondPasswordInput = confirmPassword.getText().toString();
                    if (response.body().getResponseDataModel().getStatusCode().equals("201") && isValidPassword(firstPasswordInput) && isValidPassword(secondPasswordInput)) {

                        pDialog.setTitleText("SUCCESS")


                                .setContentText("Your Otp has been sent to your email")
                                .setConfirmText("OK")


                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        showOtpValidationDialog();
                        pDialog.cancel();

                    } else if (response.body().getResponseDataModel().getStatusCode().equals("422")) {

                        pDialog.setTitleText("Email exist")
                                .setConfirmText("OK")
                                .setContentText("Please enter another email")
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                    }


                } catch (NullPointerException error) {
                    Toast.makeText(context, "Please check your email input", Toast.LENGTH_SHORT).show();
                    emailLayout.setError("Please input a correct email");

                }
            }

        }.

                start();

    }

}
