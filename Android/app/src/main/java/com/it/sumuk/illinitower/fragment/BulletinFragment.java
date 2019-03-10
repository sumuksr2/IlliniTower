package com.it.sumuk.illinitower.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.it.sumuk.illinitower.R;

public class BulletinFragment extends BaseFragment {

    public static BulletinFragment create(){
        return new BulletinFragment();
    }

    @Override
    public int getLayoutResId() {
        return (R.layout.fragment_bulletin);
    }

    @Override
    public void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    }
}
