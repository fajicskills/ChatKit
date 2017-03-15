package com.fajicskills.chatkit.util;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

/**
 * Created by angebagui on 15/03/2017.
 */

public class ViewUtil {


    @SuppressWarnings("unchecked")
    public static <T extends View> T inflateStub(@NonNull View parent, @IdRes int stubId) {
        return (T)((ViewStub)parent.findViewById(stubId)).inflate();
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T findById(@NonNull View parent, @IdRes int resId) {
        return (T) parent.findViewById(resId);
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T findById(@NonNull Activity parent, @IdRes int resId) {
        return (T) parent.findViewById(resId);
    }


    @SuppressWarnings("unchecked")
    public static <T extends View> T  inflate(@NonNull LayoutInflater inflater
                                            ,@NonNull ViewGroup parent,
                                              @LayoutRes  int layoutResId) {
        return (T) inflater.inflate(layoutResId,parent,false);
    }
}
