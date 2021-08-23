package com.contact.tracing.service;

import android.app.AlertDialog;
import android.widget.TextView;

public interface PasswordRequirementsDialog {

    void showRequirementsPassword();

    void handleCancelDialogPassword(TextView cancelDialog, AlertDialog dialogCancel);

}
