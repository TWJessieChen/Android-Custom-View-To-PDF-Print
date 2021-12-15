package com.jc666.ecglibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

/**
 * @author JC666
 * @date 2021/12/15
 * @describe 坐标背景绘制
 */
public abstract class RealRenderer {

    public SoftStrategy mSoftStrategy;

    public ECGPointValue[] mEcgData;

    public Context mContext;

    protected float mDensity;

    protected float mScaleDensity;

    public RealRenderer(@NonNull Context context, @NonNull ECGPointValue[] values){
        this.mContext = context;
        this.mEcgData = values;
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        this.mDensity = dm.density;
        this.mScaleDensity = dm.scaledDensity;
    }

    void setSoftStrategy(@NonNull SoftStrategy softStrategy){
        this.mSoftStrategy = softStrategy;
    }

    public abstract void draw(Canvas canvas);

}
