package com.jc666.ecglibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import androidx.annotation.NonNull;
import java.util.List;

/**
 * @author JC666
 * @date 2021/12/15
 * @describe 坐标背景绘制
 */
public abstract class BriteMEDRealRenderer {

    public SoftStrategy mSoftStrategy;

    public List<ECGReportDataFormat> mEcgData;

    public Context mContext;

    protected float mDensity;

    protected float mScaleDensity;

    protected DisplayMetrics mDisplayMetrics;

    public BriteMEDRealRenderer(@NonNull Context context, @NonNull List<ECGReportDataFormat> values){
        this.mContext = context;
        this.mEcgData = values;
        this.mDisplayMetrics = context.getResources().getDisplayMetrics();
        this.mDensity = mDisplayMetrics.density;
        this.mScaleDensity = mDisplayMetrics.scaledDensity;
    }

    void setSoftStrategy(@NonNull SoftStrategy softStrategy){
        this.mSoftStrategy = softStrategy;
    }

    public abstract void draw(Canvas canvas);

}
