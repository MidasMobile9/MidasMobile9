package com.test.midasmobile9.util;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * 사용방법
 *
 * ProgressBarShow.getProgressBarShowSingleton(getContext()).show(뷰그룹);
 *
 * 로 ProgressBar 보이게
 *
 * ProgressBarShow.getProgressBarShowSingleton(getContext()).remove(뷰그룹);
 *
 * 로 ProgressBar 안보이게
 */

public class ProgressBarShow {
    private static ProgressBarShow progressBarShow;
    private static ProgressBar progressBar;
    private boolean isShown;
    private Context context;

    private ProgressBarShow(Context context){
        this.context = context;
        progressBar = new ProgressBar(context, null);
        createProgressBar();
        isShown = false;
    }

    private void createProgressBar(){
        LinearLayout.LayoutParams progressBarLayoutParam =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

        progressBarLayoutParam.gravity = Gravity.CENTER;

        progressBar.setLayoutParams(progressBarLayoutParam);
    }

    public static ProgressBarShow getProgressBarShowSingleton(Context context){
        if(progressBarShow == null){
            progressBarShow = new ProgressBarShow(context);
        }
        return progressBarShow;
    }

    public void show(ViewGroup vg){
        try{
            if(!isShown) {
                isShown = true;
                vg.addView(progressBar, 0);
            }
        } catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    public void remove(ViewGroup vg){
        try{
            if(isShown) {
                isShown = false;
                vg.removeView(progressBar);
            }
        } catch (IllegalStateException e){
            e.printStackTrace();
        }
    }
}
