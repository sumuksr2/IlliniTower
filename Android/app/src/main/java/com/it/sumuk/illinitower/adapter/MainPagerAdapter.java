package com.it.sumuk.illinitower.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.it.sumuk.illinitower.fragment.BulletinFragment;
import com.it.sumuk.illinitower.fragment.DiningFragment;
import com.it.sumuk.illinitower.fragment.EmptyFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DiningFragment.create();
            case 1:
                return EmptyFragment.create();
            case 2:
                return BulletinFragment.create();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
