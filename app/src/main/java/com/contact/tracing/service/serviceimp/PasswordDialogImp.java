package com.contact.tracing.service.serviceimp;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.contact.tracing.R;
import com.contact.tracing.service.PasswordRequirementsDialog;

public class PasswordDialogImp implements PasswordRequirementsDialog {
    private Context context;

    public PasswordDialogImp(Context context) {
        this.context = context;
    }

    @Override
    public void showRequirementsPassword() {
        View requirementsDialog = View.inflate(context, R.layout.password_feedback_dialog, null);
        TextView showBulletList = requirementsDialog.findViewById(R.id.password_list);
        TextView cancelLabel = requirementsDialog.findViewById(R.id.cancel_button_dialog);
        String passwordList = "• Password must have 8 characters<br/>" +
                "• Password must have capital & lower case<br/>" +
                "• Password must have  Special characters<br/>" +
                "•Password must have Digits";
        showBulletList.setText(Html.fromHtml(passwordList));
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        final AlertDialog.Builder showFailDialog = new AlertDialog.Builder(context);
        showFailDialog.setCancelable(false);

        showFailDialog.setView(requirementsDialog);
        final AlertDialog dialog = showFailDialog.create();
        dialog.show();
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        handleCancelDialogPassword(cancelLabel, dialog);
    }

    @Override
    public void handleCancelDialogPassword(TextView cancelDialog, AlertDialog dialogCancel) {
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCancel.cancel();
            }
        });
    }
}
