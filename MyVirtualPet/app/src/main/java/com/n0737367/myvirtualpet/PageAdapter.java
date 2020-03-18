package com.n0737367.myvirtualpet;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private int numberOfTabs;

    public PageAdapter(@NonNull FragmentManager fm, int _numberOfTabs) {
        super(fm);
        this.numberOfTabs = _numberOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch(i) {
            case 0: //Pet Tab
                return new PetFragment();
            case 1: //Map Tab
                return new MapFragment();
            case 2: //Options Tab
                return new OptionsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
    return 0;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}

