package com.contact.tracing.controller.fragment.loginregister;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.contact.tracing.Dashboard;
import com.contact.tracing.R;
import com.contact.tracing.model.LoginModel;
import com.contact.tracing.repository.LoginRepository;
import com.contact.tracing.session.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.royrodriguez.transitionbutton.TransitionButton;


public class LoginFragment extends Fragment {
    private TransitionButton button;
    private TextInputLayout usernameLayout;
    private TextInputLayout passwordLayout;
    private TextInputEditText password;
    private TextInputEditText username;
    private CountDownTimer countTimer;
    private LottieAnimationView lottieAnimationView;
    private TextView forgotPassword;

    public LoginFragment() {

    }

    private void init(View view) {
        button = view.findViewById(R.id.loginButton);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        usernameLayout = view.findViewById(R.id.username_text_layout);
        passwordLayout = view.findViewById(R.id.password_input_layout);
        username.setOnFocusChangeListener(focusChangeListener);
        button.setOnFocusChangeListener(focusChangeListener);
        password.setOnFocusChangeListener(focusChangeListener);
        lottieAnimationView = view.findViewById(R.id.imageAnimation);
        forgotPassword = view.findViewById(R.id.forgotPasswordLabel);
    }

    private final View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                validateOnFocus(v);
                v.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.text_field_active));
            } else {
                validationOnNotFocus(v);
                v.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.text_field_not_active));
            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login,
                container, false);
        init(view);
        SessionManager sessionManager = new SessionManager(view.getContext());
        LoginRepository loginRepository = new LoginRepository(getContext(), usernameLayout, passwordLayout, sessionManager);

        login(view, loginRepository);
        sleep();
        lottieAnimationView.cancelAnimation();


        return view;
    }

    public void forgotPasswordDialog(View v) {
        View mView = getLayoutInflater().inflate(R.layout.send_email_alert_dialog, null);

        TextView cancelButton = mView.findViewById(R.id.cancel_button);
        Button submitEmail = mView.findViewById(R.id.sumbit_email_button);
        final TextInputEditText resendEmail = mView.findViewById(R.id.resend_email_text);
        final TextInputLayout resendEmailLayout = mView.findViewById(R.id.resend_email_layout);
        final String resendEmailText = resendEmail.getText().toString();

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());

        mBuilder.setCancelable(false);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        resendEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    resendEmailLayout.setError(null);
                    resendEmailLayout.setErrorEnabled(true);
                }
            }
        });
        submitEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resendEmailText.isEmpty() && !resendEmail.isFocused()) {
                    resendEmailLayout.setError("Field cannot be empty");
                } else {
                    dialog.cancel();
                    resetPasswordOTP();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });


    }

    private void resetPassword() {
        View mView = getLayoutInflater().inflate(R.layout.change_password_dialog, null);
        Button submitNewPassword = mView.findViewById(R.id.sumbit_new_password);
        TextView cancelOTP = mView.findViewById(R.id.cancel_reset_password);
        final TextInputEditText passwordEditText = mView.findViewById(R.id.reset_password_text);
        final TextInputEditText confirmPasswordEditText = mView.findViewById(R.id.reset_confirm_password_text);
        final TextInputLayout passwordLayout = mView.findViewById(R.id.reset_password_layout);
        final TextInputLayout confirmPasswordLayout = mView.findViewById(R.id.reset_confirm_password_layout);
        final String passwordText = passwordEditText.getText().toString();
        final String confirmPasswordText = confirmPasswordEditText.getText().toString();

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());

        mBuilder.setCancelable(false);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    fixInputLayout(passwordLayout);
                }
            }
        });
        confirmPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    fixInputLayout(confirmPasswordLayout);
                }
            }
        });
        submitNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordText.isEmpty() && !passwordEditText.isFocused()) {
                    setErrorOnField(passwordLayout);
                } else if (passwordText.isEmpty() && !passwordEditText.isFocused()) {
                    fixInputLayout(passwordLayout);
                    fixInputLayout(confirmPasswordLayout);
                } else {
                    fixInputLayout(passwordLayout);
                    fixInputLayout(confirmPasswordLayout);
                }

                if (confirmPasswordText.isEmpty() && !confirmPasswordEditText.isFocused()) {
                    setErrorOnField(confirmPasswordLayout);
                } else if (confirmPasswordText.isEmpty() && confirmPasswordEditText.isFocused()) {
                    fixInputLayout(confirmPasswordLayout);
                    fixInputLayout(passwordLayout);
                } else {
                    fixInputLayout(confirmPasswordLayout);
                    fixInputLayout(passwordLayout);
                }


            }
        });
        cancelOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void resetPasswordOTP() {
        View mView = getLayoutInflater().inflate(R.layout.alert_dialog_otp, null);
        Button verifyOTP = mView.findViewById(R.id.verify_code);
        TextView cancelOTP = mView.findViewById(R.id.cancel_otp);
        TextView resendOTP = mView.findViewById(R.id.resend_otp);

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());

        mBuilder.setCancelable(false);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        cancelOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                resetPassword();
            }
        });
    }

    private void sleep() {
        countTimer = new CountDownTimer(20, 20) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                lottieAnimationView.cancelAnimation();
            }
        }.start();
    }

    private void login(View view, LoginRepository loginRepository) {
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPasswordDialog(v);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()
                ) {
                    usernameLayout.setError("Username cannot be empty");
                    passwordLayout.setError("Password cannot be empty");
                } else {
                    button.startAnimation();
                    validateLogin(v, loginRepository);
                }
            }
        });
    }

    private void validateLogin(View v, LoginRepository loginRepository) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (validateLoginCredentials(v)) {
                    loginRepository.validateUserLoginCredentials(validateLoginModel(username.getText().toString(), password.getText().toString()));
//                    loginRepository.fetchTokenGenerated();
                    button.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);

                } else {
                    button.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                }
            }
        }, 3000);

    }

    public LoginModel validateLoginModel(String username, String password) {
        LoginModel loginModel = new LoginModel();
        loginModel.setUsername(username);
        loginModel.setPassword(password);
        return loginModel;
    }

    public boolean validateLoginCredentials(View view) {
        String usernameInput = username.getText().toString().trim();
        String passwordInput = password.getText().toString().trim();

        if (usernameInput.isEmpty() && !username.isFocused()) {
            setErrorOnField(usernameLayout);
        } else if (usernameInput.isEmpty() && username.isFocused()) {
            fixInputLayout(usernameLayout);
        } else {
            fixInputLayout(usernameLayout);
        }

        if (passwordInput.isEmpty() && !password.isFocused()) {
            setErrorOnField(passwordLayout);
        } else if (passwordInput.isEmpty() && password.isFocused()) {
            fixInputLayout(passwordLayout);
        } else {
            fixInputLayout(passwordLayout);
        }


        return !usernameInput.isEmpty() && !passwordInput.isEmpty();

    }

    private void validationOnNotFocus(View v) {
        if (v.equals(username) && checkIfEmpty(username)) {
            setErrorOnField(usernameLayout);
            usernameLayout.setErrorEnabled(true);
        }
        if (v.equals(password) && checkIfEmpty(password)) {
            setErrorOnField(passwordLayout);
            passwordLayout.setErrorEnabled(true);
        }

        if (checkIfEmpty(username) || checkIfEmpty(password)) {
            button.setEnabled(false);
        }

    }

    private void validateOnFocus(View v) {
        String usernameInput = username.getText().toString();
        String passwordInput = username.getText().toString();
        if (v.equals(username)) {
            usernameLayout.setErrorEnabled(false);
        }
        if (v.equals(password)) {
            passwordLayout.setErrorEnabled(false);
        }
        if (usernameInput.length() > 1 && passwordInput.length() > 1) {
            button.setEnabled(true);
        }
    }

    public boolean checkIfEmpty(TextInputEditText field) {
        return field.getText().toString().isEmpty();

    }

    private void setErrorOnField(TextInputLayout layout) {
        layout.setError("Field cannot be emtpy");
    }

    private void fixInputLayout(TextInputLayout layout) {
        layout.setError(null);
        layout.setErrorEnabled(false);
    }

}
