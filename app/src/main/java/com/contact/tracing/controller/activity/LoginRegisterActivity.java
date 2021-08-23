package com.contact.tracing.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.contact.tracing.R;
import com.contact.tracing.adapter.LoginAndRegisterAdapter;
import com.contact.tracing.controller.fragment.loginregister.LoginFragment;
import com.contact.tracing.controller.fragment.loginregister.RegisterFragment;

public class LoginRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);

        LoginAndRegisterAdapter pagerAdapter = new LoginAndRegisterAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new LoginFragment());
        pagerAdapter.addFragment(new RegisterFragment());


        viewPager.setAdapter(pagerAdapter);
    }


}