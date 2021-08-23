package com.contact.tracing.controller.fragment.loginregister;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.contact.tracing.R;
import com.contact.tracing.model.OtpModel;
import com.contact.tracing.repository.UserRepository;
import com.contact.tracing.service.PasswordRequirementsDialog;
import com.contact.tracing.service.RegistrationServiceDialog;
import com.contact.tracing.service.serviceimp.PasswordDialogImp;
import com.contact.tracing.service.serviceimp.RegisterDialogImp;
import com.contact.tracing.service.serviceimp.ValidateOtpImp;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.royrodriguez.transitionbutton.TransitionButton;

import static com.contact.tracing.validator.UserCredentialsValidators.isValidPassword;


public class RegisterFragment extends Fragment {

    private TextInputEditText username, completeName, firstPassword, confirmPassword;
    private TextInputEditText email;
    private TextInputLayout usernameLayout, completenameLayout, emailLayout, firstPasswordLayout, confirmPasswordLayout;
    private TransitionButton registerButton;
    private LottieAnimationView lottieAnimationView;
    private CountDownTimer countTimer;


    public RegisterFragment() {
        // Required empty public constructor
    }

    private final OnFocusChangeListener focusChangeListener = new OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {

                validateOnFocus(v);
                v.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.text_field_active));
            } else {
                validateOnNotFocus(v);
                v.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.text_field_not_active));
            }
        }
    };


    private void init(View view) {
        username = view.findViewById(R.id.reg_username);
        completeName = view.findViewById(R.id.reg_full_name);
        email = view.findViewById(R.id.reg_email);
        firstPassword = view.findViewById(R.id.reg_password);
        confirmPassword = view.findViewById(R.id.reg_retype_password);

        usernameLayout = view.findViewById(R.id.reg_username_text_layout);
        completenameLayout = view.findViewById(R.id.reg_full_name_text_layout);
        emailLayout = view.findViewById(R.id.reg_email_text_layout);
        firstPasswordLayout = view.findViewById(R.id.reg_password_text_layout);
        confirmPasswordLayout = view.findViewById(R.id.reg_retype_password_text_layout);
        registerButton = view.findViewById(R.id.reg_button);
        lottieAnimationView = view.findViewById(R.id.imageAnimation);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View registerView = inflater.inflate(R.layout.fragment_register, container, false);
        init(registerView);
        RegistrationServiceDialog registrationServiceDialog = new RegisterDialogImp(getContext(), emailLayout, firstPassword, confirmPassword);
        UserRepository userRepository = new UserRepository(emailLayout, registrationServiceDialog, getContext(), firstPassword, confirmPassword);
        ValidateOtpImp validateOtpImp = new ValidateOtpImp(getContext(), userRepository, username, email, firstPassword);

        sleep();
        attachListeners(registerView, userRepository);
        return registerView;
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


    public void attachListeners(View view, UserRepository sentOtp) {
        username.setOnFocusChangeListener(focusChangeListener);
        completeName.setOnFocusChangeListener(focusChangeListener);
        email.setOnFocusChangeListener(focusChangeListener);
        firstPassword.setOnFocusChangeListener(focusChangeListener);
        confirmPassword.setOnFocusChangeListener(focusChangeListener);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean hasEmptyFullName = false, hasEmptyUsername = false, hasEmptyEmail = false,
                        hasEmptyFirstPassword = false, hasEmptyConfirmPassword = false, hasNotEqualPassword = false;

                if (!firstPassword.getText().toString().equals(confirmPassword.getText().toString())) {
                    confirmPasswordLayout.setError("Those, password doesn't match");
                    confirmPasswordLayout.setErrorEnabled(true);
                    hasNotEqualPassword = true;
                }

                if (username.getText().toString().isEmpty()) {
                    usernameLayout.setErrorEnabled(true);
                    usernameLayout.setError("Username cannot be empty");
                    hasEmptyUsername = true;
                }
                if (completeName.getText().toString().isEmpty()) {
                    completenameLayout.setErrorEnabled(true);
                    completenameLayout.setError("Complete cannot be empty");
                    hasEmptyFullName = true;
                }
                if (email.getText().toString().isEmpty()) {
                    emailLayout.setErrorEnabled(true);
                    emailLayout.setError("Email cannot be empty");
                    hasEmptyEmail = true;
                }
                if (firstPassword.getText().toString().isEmpty()) {
                    firstPasswordLayout.setErrorEnabled(true);
                    firstPasswordLayout.setError("First password cannot be empty");
                    hasEmptyFirstPassword = true;
                }
                if (confirmPassword.getText().toString().isEmpty()) {
                    confirmPasswordLayout.setErrorEnabled(true);
                    confirmPasswordLayout.setError("Confirm password cannot be empty");
                    hasEmptyConfirmPassword = true;
                }

                if (!(hasEmptyFullName && hasEmptyConfirmPassword && hasEmptyFirstPassword && hasEmptyUsername && hasEmptyEmail)) {
                    if (!(hasNotEqualPassword)) {
                        registerButton.startAnimation();
                        registrationButtonHandler(v, sentOtp);
                    }
                }
            }

        });

    }

    private void registrationButtonHandler(View view, UserRepository sentOtp) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (validateInputs(view)) {
                    sentOtp.generateOtp(sendOtpRequest(email.getText().toString(), firstPassword.getText().toString(), username.getText().toString()));
                    registerButton.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                }
            }
        }, 5000);
    }


    public boolean validateInputs(View view) {
        PasswordRequirementsDialog passwordDialog = new PasswordDialogImp(getContext());
        String usernameText = username.getText().toString().trim();
        String fullnameText = completeName.getText().toString().trim();
        String emailText = email.getText().toString().trim();
        String firstPasswordText = firstPassword.getText().toString().trim();
        String confirmPasswordText = confirmPassword.getText().toString().trim();


        if (usernameText.isEmpty() && !username.isFocused()) {
            setErrorOnField(usernameLayout);
        } else if (usernameText.isEmpty() && username.isFocused()) {
            fixInputLayout(usernameLayout);
        } else if (usernameText.length() <= 3) {
            setErrorWeakUsername(usernameLayout);
        } else {
            fixInputLayout(usernameLayout);
        }

        if (fullnameText.isEmpty() && !completeName.isFocused()) {
            setErrorOnField(completenameLayout);
        } else if (fullnameText.isEmpty() && completeName.isFocused()) {
            fixInputLayout(completenameLayout);
        } else {
            fixInputLayout(completenameLayout);
        }

        if (emailText.isEmpty() && !email.isFocused()) {
            setErrorOnField(emailLayout);
        } else if (emailText.isEmpty() && email.isFocused()) {
            fixInputLayout(emailLayout);
        } else {
            fixInputLayout(emailLayout);
        }

        if (firstPasswordText.isEmpty() && !firstPassword.isFocused()) {
            setErrorOnField(firstPasswordLayout);
        } else if (firstPasswordText.isEmpty() && firstPassword.isFocused() && isValidPassword(firstPasswordText)) {
            fixInputLayout(firstPasswordLayout);
        } else {
            fixInputLayout(firstPasswordLayout);
        }

        if (confirmPasswordText.isEmpty() && !confirmPassword.isFocused()) {
            setErrorOnField(confirmPasswordLayout);
        } else if (confirmPasswordText.isEmpty() && confirmPassword.isFocused() && isValidPassword(confirmPasswordText)) {
            fixInputLayout(confirmPasswordLayout);

            feedbackNotEqualPassword(confirmPasswordLayout);
        } else if (!(isValidPassword(firstPasswordText) && isValidPassword(confirmPasswordText))) {
            passwordDialog.showRequirementsPassword();

        } else {
            fixInputLayout(confirmPasswordLayout);
        }

        if (!firstPasswordText.equals(confirmPasswordText)) {
            feedbackNotEqualPassword(confirmPasswordLayout);
        } else {
            fixInputLayout(confirmPasswordLayout);
        }


        return !usernameText.isEmpty() && !fullnameText.isEmpty() && !emailText.isEmpty()
                && !firstPasswordText.isEmpty() && !confirmPasswordText.isEmpty() && firstPasswordText.equals(confirmPasswordText) &&
                usernameText.length() >= 4 || usernameText.length() == 60 && isValidPassword(firstPasswordText) && isValidPassword(confirmPasswordText);
    }

    public boolean checkIfEmpty(TextInputEditText field) {
        return field.getText().toString().isEmpty();
    }

    public void setErrorOnField(TextInputLayout layout) {
        layout.setError("Field cannot be emtpy");
    }

    public void feedbackNotEqualPassword(TextInputLayout passwordInput) {
        passwordInput.setError("Those, password didn't match.Try Again");
    }

    public void fixInputLayout(TextInputLayout layout) {
        layout.setError(null);
        layout.setErrorEnabled(false);
    }

    public void setErrorWeakUsername(TextInputLayout weakUsernameInput) {
        weakUsernameInput.setError("Username must be greater that 3 characters");
    }

    public void validateOnFocus(View v) {
        if (v.equals(username)) {
            usernameLayout.setErrorEnabled(false);
        }
        if (v.equals(completeName)) {
            completenameLayout.setErrorEnabled(false);
        }
        if (v.equals(email)) {
            emailLayout.setErrorEnabled(false);
        }
        if (v.equals(firstPassword)) {

            firstPasswordLayout.setErrorEnabled(false);
        }
        if (v.equals(confirmPassword)) {

            confirmPasswordLayout.setErrorEnabled(false);
        }
    }

    public void validateOnNotFocus(View v) {
        if (v.equals(username) && checkIfEmpty(username)) {
            setErrorOnField(usernameLayout);
            usernameLayout.setErrorEnabled(true);
        }
        if (v.equals(completeName) && checkIfEmpty(completeName)) {
            setErrorOnField(completenameLayout);
            completenameLayout.setErrorEnabled(true);
        }
        if (v.equals(email) && checkIfEmpty(email)) {
            setErrorOnField(emailLayout);
            emailLayout.setErrorEnabled(true);
        }
        if (v.equals(firstPassword) && checkIfEmpty(firstPassword)) {
            setErrorOnField(firstPasswordLayout);
            firstPasswordLayout.setErrorEnabled(true);
        }
        if (v.equals(confirmPassword) && checkIfEmpty(confirmPassword)) {
            setErrorOnField(confirmPasswordLayout);
            confirmPasswordLayout.setErrorEnabled(true);
        }
    }


    public OtpModel sendOtpRequest(String email, String password, String username) {
        OtpModel otpModel = new OtpModel();
        otpModel.setEmail(email);
        otpModel.setPassword(password);
        otpModel.setUsername(username);
        return otpModel;
    }


}


