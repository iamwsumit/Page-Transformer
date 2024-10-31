package com.sumit1334.pagetransformer;

import android.util.Log;
import androidx.viewpager.widget.ViewPager;

import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;

import com.sumit1334.pagetransformer.transformers.CubeOutTransformer;
import com.sumit1334.pagetransformer.transformers.CubeInTransformer;
import com.sumit1334.pagetransformer.transformers.DefaultTransformer;
import com.sumit1334.pagetransformer.transformers.ZoomInTransformer;
import com.sumit1334.pagetransformer.transformers.ZoomOutSlideTransformer;
import com.sumit1334.pagetransformer.transformers.AccordionTransformer;
import com.sumit1334.pagetransformer.transformers.RotateDownTransformer;
import com.sumit1334.pagetransformer.transformers.RotateUpTransformer;
import com.sumit1334.pagetransformer.transformers.BackgroundToForegroundTransformer;
import com.sumit1334.pagetransformer.transformers.ForegroundToBackgroundTransformer;
import com.sumit1334.pagetransformer.transformers.ParallaxTransformer;
import com.sumit1334.pagetransformer.transformers.FlipVerticalTransformer;
import com.sumit1334.pagetransformer.transformers.FlipHorizontalTransformer;
import com.sumit1334.pagetransformer.transformers.DepthPageTransformer;
import com.sumit1334.pagetransformer.transformers.TabletTransformer;
import com.sumit1334.pagetransformer.transformers.StackTransformer;
import com.sumit1334.pagetransformer.transformers.ScaleInOutTransformer;

import java.lang.reflect.Field;

public class PageTransformer extends AndroidNonvisibleComponent implements Component, ViewPager.OnPageChangeListener {
    private final String TAG = "Page Transformer";
    private ViewPager viewPager;

    public PageTransformer(ComponentContainer container) {
        super(container.$form());
        Log.i(TAG, "Extension Initialized");
    }

    @SimpleEvent
    public void ErrorOccurred(final String error) {
        Log.e(TAG, "ErrorOccurred : " + error);
        EventDispatcher.dispatchEvent(this, "ErrorOccurred", error);
    }

    @SimpleEvent
    public void PageScrolled(int position, float offset) {
        EventDispatcher.dispatchEvent(this, "PageScrolled", position + 1, offset);
    }

    @SimpleFunction
    public void Initialize(Component viewPager) {
        this.register(viewPager);
    }

    @SimpleFunction
    public boolean IsInitialized() {
        return viewPager != null;
    }

    @SimpleFunction
    public void SetTransformer(String transformer) {
        if (viewPager != null) {
            viewPager.setPageTransformer(true, null);
            viewPager.setPageTransformer(true, this.getTransformer(transformer));
        }
    }

    @SimpleProperty
    public String Accordion() {
        return "Accordion";
    }

    @SimpleProperty
    public String BackgroundToForeground() {
        return "BackgroundToForeground";
    }

    @SimpleProperty
    public String CubeIn() {
        return "CubeIn";
    }

    @SimpleProperty
    public String CubeOut() {
        return "CubeOut";
    }

    @SimpleProperty
    public String DepthPage() {
        return "DepthPage";
    }

    @SimpleProperty
    public String FlipHorizontal() {
        return "FlipHorizontal";
    }

    @SimpleProperty
    public String FlipVertical() {
        return "FlipVertical";
    }

    @SimpleProperty
    public String ForegroundToBackground() {
        return "ForegroundToBackground";
    }

    @SimpleProperty
    public String Parallax() {
        return "Parallax";
    }

    @SimpleProperty
    public String RotateDown() {
        return "RotateDown";
    }

    @SimpleProperty
    public String RotateUp() {
        return "RotateUp";
    }

    @SimpleProperty
    public String ScaleInOut() {
        return "ScaleInOut";
    }

    @SimpleProperty
    public String Stack() {
        return "Stack";
    }

    @SimpleProperty
    public String Tablet() {
        return "Tablet";
    }

    @SimpleProperty
    public String ZoomIn() {
        return "ZoomIn";
    }

    @SimpleProperty
    public String ZoomOutSlide() {
        return "ZoomOutSlide";
    }

    private ViewPager.PageTransformer getTransformer(String name) {
        switch (name) {
            case "FlipHorizontal":
                return new FlipHorizontalTransformer();
            case "Accordion":
                return new AccordionTransformer();
            case "BackgroundToForeground":
                return new BackgroundToForegroundTransformer();
            case "CubeIn":
                return new CubeInTransformer();
            case "CubeOut":
                return new CubeOutTransformer();
            case "DepthPage":
                return new DepthPageTransformer();
            case "FlipVertical":
                return new FlipVerticalTransformer();
            case "ForegroundToBackground":
                return new ForegroundToBackgroundTransformer();
            case "Parallax":
                return new ParallaxTransformer();
            case "RotateDown":
                return new RotateDownTransformer();
            case "RotateUp":
                return new RotateUpTransformer();
            case "ScaleInOut":
                return new ScaleInOutTransformer();
            case "Stack":
                return new StackTransformer();
            case "Tablet":
                return new TabletTransformer();
            case "ZoomIn":
                return new ZoomInTransformer();
            case "ZoomOutSlide":
                return new ZoomOutSlideTransformer();
            default:
                return new DefaultTransformer();
        }
    }

    private void register(Component component) {
        Field[] fields = component.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().getSimpleName().equalsIgnoreCase(ViewPager.class.getSimpleName())) {
                field.setAccessible(true);
                try {
                    this.viewPager = (ViewPager) field.get(component);
                    this.viewPager.addOnPageChangeListener(this);
                } catch (IllegalAccessException e) {
                    ErrorOccurred(e.getMessage());
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        PageScrolled(i, v);
    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
