package com.sumit1334.pagetransformer.transformers;

import android.view.View;
import androidx.viewpager.widget.ViewPager;

public class ParallaxTransformer implements ViewPager.PageTransformer{


    @Override
    public void transformPage(View page, float position) {
        int width = page.getWidth();
        if (position < -1) {
            page.setScrollX((int) (width * 0.75 * -1));
        } else if (position <= 1) {
            page.setScrollX((int) (width * 0.75 * position));
        } else {
            page.setScrollX((int) (width * 0.75));
        }
    }
}