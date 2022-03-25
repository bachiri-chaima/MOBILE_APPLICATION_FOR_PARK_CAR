package com.example.gariliapllication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {

    private loginActivity context;
    int totaltabs=2;

    public LoginAdapter( FragmentManager fm, loginActivity context, int totaltabs) {
        super(fm);
        this.context = context;
        totaltabs = totaltabs;
    }
    @Override
    public int getCount() {
        return totaltabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0 :  login_tab_fragment login_tab_fragment = new login_tab_fragment();
                return login_tab_fragment;
            case 1 :  sign_tab_fragment sign_tab_fragment = new sign_tab_fragment();
                return sign_tab_fragment;

            default: return null;
        }
    }
}
