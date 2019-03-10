package com.it.sumuk.illinitower.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.it.sumuk.illinitower.R;

public class DiningFragment extends BaseFragment {

    public static DiningFragment create(){
        return new DiningFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_dining;
    }

    @Override
    public void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    }
}
