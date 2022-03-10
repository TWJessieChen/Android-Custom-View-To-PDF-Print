package com.jc666.ecglibrary.print;

import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import androidx.annotation.NonNull;
import com.jc666.ecglibrary.SoftStrategy;
import java.util.List;

/**
 * @author JC666
 * @date 2021/03/10
 * BriteMEDPrintRenderer
 * 
 */
public abstract class BriteMEDPrintRenderer {

    public SoftStrategy mSoftStrategy;

    public List<ECGReportPrintDataFormat> mEcgData;

    public Context mContext;

    protected float mDensity;

    protected float mScaleDensity;

    protected DisplayMetrics mDisplayMetrics;

    public BriteMEDPrintRenderer(@NonNull Context context, @NonNull List<ECGReportPrintDataFormat> values){
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
