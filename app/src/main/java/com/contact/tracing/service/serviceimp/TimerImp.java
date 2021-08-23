package com.contact.tracing.service.serviceimp;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.contact.tracing.R;
import com.contact.tracing.service.TimerOtp;


public class TimerImp implements TimerOtp {
    private CountDownTimer countDownTimer;
    private long timeLeftInMillSeconds = 300000;
    private TextView countDownOtp;
    private ImageView alarmIcon;
    private TextView otpDescription;

    public TimerImp(TextView otpDescription, TextView countDownOtp, ImageView alarmIcon) {
        this.countDownOtp = countDownOtp;
        this.alarmIcon = alarmIcon;
        this.otpDescription = otpDescription;
    }

    @Override
    public void startOtpCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillSeconds = millisUntilFinished;
                updateOtpTimerCountDown();
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }


    @Override
    public void updateOtpTimerCountDown() {
        String timeLeft;
        int minutes = (int) timeLeftInMillSeconds / 60000;
        int seconds = (int) timeLeftInMillSeconds % 60000 / 1000;

        timeLeft = "" + minutes;
        timeLeft += ":";

        if (seconds < 10) timeLeft += "0";
        timeLeft += seconds;

//        if (minutes == -1 && seconds == 0) {
//            otpDescription.setVisibility(View.VISIBLE);
//            otpDescription.setText(R.string.otp_expires);
//
//        } else {
            otpDescription.setText(timeLeft);

            alarmIcon.setVisibility(View.VISIBLE);
            alarmIcon.setImageResource(R.drawable.alarm_on);
        }
    }

