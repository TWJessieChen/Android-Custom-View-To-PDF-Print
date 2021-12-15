package com.jc666.ecglibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * @author JC666
 * @date 2021/12/15
 * @describe TODO
 */
public class LuckySoftRenderer {

    public static final float DEFAULT_MAX_VALUE = Float.NaN;

    private SoftStrategy mSoftStrategy;

    private RealRenderer mDataRenerer;

    private RealRenderer mAxesRenderer;

    private Bitmap softwareBitmap;

    private Canvas softwareCanvas;

    private LuckySoftRenderer(Context context, ECGPointValue[] values, SoftStrategy softStrategy, RealRenderer dataRenderer, RealRenderer axesArenderer) {
        this.mSoftStrategy = softStrategy != null?softStrategy:new LuckySoftStrategy(values.length);
        this.mDataRenerer = dataRenderer != null?dataRenderer:new SoftDataRenderer(context, values);
        this.mAxesRenderer = axesArenderer != null?axesArenderer:new SoftAxesRenderer(context, values);
        this.mDataRenerer.setSoftStrategy(mSoftStrategy);
        this.mAxesRenderer.setSoftStrategy(mSoftStrategy);
    }

    public static LuckySoftRenderer instantiate(@NonNull Context context,@NonNull ECGPointValue[] values) {
        return instantiate(context, values,null,null,null);
    }

    public static LuckySoftRenderer instantiate(@NonNull Context context,
                                                @NonNull ECGPointValue[] values,
                                                @Nullable SoftStrategy softStrategy,
                                                @Nullable RealRenderer dataRenderer,
                                                @Nullable RealRenderer axesArenderer) {
        return new LuckySoftRenderer(context, values,softStrategy,dataRenderer,axesArenderer);
    }

    private void initSoft() {
        softwareCanvas = new Canvas();
        softwareBitmap = Bitmap.createBitmap(mSoftStrategy.pictureWidth(),
                mSoftStrategy.pictureHeight(), Bitmap.Config.ARGB_8888);
        softwareCanvas.setBitmap(softwareBitmap);
        softwareCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    public LuckySoftRenderer setMaxDataValue(float maxDataValue) {
        if (!Float.isNaN(maxDataValue)) {
            if (mSoftStrategy instanceof  LuckySoftStrategy){
                ((LuckySoftStrategy) mSoftStrategy).setMaxDataValueForMv(maxDataValue);
            }
        }
        return this;
    }

    /**
     * 开始绘制
     */
    public Bitmap startRender(){
        this.initSoft();
        softwareCanvas.drawColor(Color.WHITE);
        mAxesRenderer.draw(softwareCanvas);
//        mDataRenerer.draw(softwareCanvas);
        return softwareBitmap;
    }
}
