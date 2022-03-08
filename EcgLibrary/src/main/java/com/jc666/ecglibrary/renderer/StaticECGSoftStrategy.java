package com.jc666.ecglibrary.renderer;

import android.util.Log;
import com.jc666.ecglibrary.Transformer;
import com.jc666.ecglibrary.view.StaticSoftStrategy;

/**
 * @author JC666
 * @date 2021/12/15
 * @describe TODO
 */
public class StaticECGSoftStrategy implements StaticSoftStrategy {
    private String TAG = this.getClass().getSimpleName();

    private int pointCount;

    private int width;

    private int height;

    /**
     * 小网格的宽高
     */
    private int SMALL_GRID_WIDTH = 10;

    /**
     * 网格的宽高
     */
    private int GRID_WIDTH = 30;

    private float maxDataValueForMv;//默认每行所表示的上下最大毫伏数 (maxDataValueForMv,-maxDataValueForMv)

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public StaticECGSoftStrategy(int pointCount, int width, int height){
        this.pointCount = pointCount;
        this.maxDataValueForMv = 1.0f;
        this.width = width;
        this.height = height;
        //Log.d(TAG,"width: " + width);
        //Log.d(TAG,"height: " + height);
    }

    @Override
    public int pictureWidth() {
        //Log.d(TAG,"pointsPerRow(): " + pointsPerRow());
        //Log.d(TAG,"pixelPerPoint(): " + pixelPerPoint());
        //Log.d(TAG,"horizontalPadding(): " + horizontalPadding());
        //Log.d(TAG,"pictureWidth(): " + Math.round(pointsPerRow() * pixelPerPoint() + horizontalPadding() * 2));
        return Math.round(pointsPerRow() * pixelPerPoint() + horizontalPadding() * 2);//左右边距
    }

    @Override
    public int pictureHeight() {
        //Log.e(TAG,"pixelPerCell(): " + pixelPerCell());
        //Log.e(TAG,"cellCountPerGrid(): " + cellCountPerGrid());
        //Log.e(TAG,"gridCountPerRow(): " + gridCountPerRow());
        //Log.e(TAG,"totalRows(): " + totalRows());
        //Log.e(TAG,"verticalPadding(): " + verticalPadding());
        //Log.e(TAG,"pictureWidth(): " + ((int) pixelPerCell() * cellCountPerGrid() * gridCountPerRow() * totalRows() + verticalPadding() * 2));
        return (int) pixelPerCell() * cellCountPerGrid() * gridCountPerRow() * totalRows() + verticalPadding() * 2;//，一小格是1像素,5个小格组成一个大格，总共6个大格每行
    }

    @Override
    public int gridCountPerRow() {
        Log.d(TAG,"gridCountPerRow maxDataValueForMv: " + maxDataValueForMv);
        Log.d(TAG,"gridCountPerRow pointCount: " + pointCount);
        Log.d(TAG,"gridCountPerRow (height / GRID_WIDTH): " + (height / GRID_WIDTH));
        if((height / GRID_WIDTH) < 4) {
            return 4;
        }
        return (height / GRID_WIDTH);
    }

    @Override
    public int cellCountPerGrid() {
        return 5;
    }

    @Override
    public int pointsPerRow() {
        return pointsPerSecond() * secondsPerRow();// 500/s,10s数据
    }

    @Override
    public int secondsPerRow() {
        return 10;
    }

    /**
     * 數值會影響寬度可以多長
     */
    @Override
    public int pointsPerSecond() {
        if((height / GRID_WIDTH) < 4) {
            return 600;
        }
        return 500; //每秒心律資料多寡個數，目前設定每秒心律有500筆資料
    }

    @Override
    public float pixelPerCell() {
        if((height / GRID_WIDTH) < 4) {
            return 4F;
        }
        return (height / GRID_WIDTH);    //一个小格占5像素，控制格子大小
    }

    @Override
    public float pixelPerPoint() {
        //if((height / GRID_WIDTH) < 4) {
        //    return 1.5f;
        //}
        return 1f;//0.5像素/每点
    }

    @Override
    public int totalRows() {
        Log.d(TAG, "TotalRows: " + (pointCount%pointsPerRow() == 0?pointCount/pointsPerRow():pointCount/pointsPerRow()+1));
        return pointCount%pointsPerRow() == 0?pointCount/pointsPerRow():pointCount/pointsPerRow()+1;//总共12行，2分钟数据
    }

    @Override
    public int horizontalPadding() {
        return 0;//水平方向，左右边距20个像素
    }

    @Override
    public int verticalPadding() {
        return 0;
    }

    @Override
    public float maxDataValueForMv() {
        return maxDataValueForMv;
    }

    @Override
    public int cellCountsPerMv() {
        return 10;
    }

    @Override
    public Transformer getTransformer() {
        return new Transformer() {};
    }

    public void setMaxDataValueForMv(float maxDataValueForMv) {
        this.maxDataValueForMv = maxDataValueForMv;
    }
}
