package com.it.sumuk.illinitower.view;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.it.sumuk.illinitower.R;

public class TabsView extends FrameLayout implements ViewPager.OnPageChangeListener {

    private ImageView centerImage;
    private ImageView rightImage;
    private ImageView leftImage;
    private View indicator;

    public TabsView(@NonNull Context context){
        this(context, null);
    }

    public TabsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_tabs, this, false);

        centerImage = (ImageView) findViewById(R.id.vt_centerImage);
        leftImage = (ImageView) findViewById(R.id.vt_leftImage);
        rightImage = (ImageView) findViewById(R.id.vt_rightImage);

        indicator = findViewById(R.id.vt_indicator);

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
