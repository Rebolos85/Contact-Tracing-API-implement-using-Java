package com.contact.tracing.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;

public class LoginAndRegisterAdapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> fragmentList = new ArrayList<>();

    public LoginAndRegisterAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }
    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }
}