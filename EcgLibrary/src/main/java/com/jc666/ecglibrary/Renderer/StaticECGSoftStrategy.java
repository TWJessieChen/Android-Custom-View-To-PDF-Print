package com.jc666.ecglibrary.Renderer;

import android.util.Log;
import com.jc666.ecglibrary.SoftStrategy;
import com.jc666.ecglibrary.Transformer;

/**
 * @author JC666
 * @date 2021/12/15
 * @describe TODO
 */
public class StaticECGSoftStrategy implements SoftStrategy {
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

    public StaticECGSoftStrategy(int pointCount, int width, int height){
        this.pointCount = pointCount;
        this.maxDataValueForMv = 1.0f;
        this.width = width;
        this.height = height;
    }

    @Override
    public int pictureWidth() {
        Log.d(TAG,"pointsPerRow(): " + pointsPerRow());
        Log.d(TAG,"pixelPerPoint(): " + pixelPerPoint());
        Log.d(TAG,"horizontalPadding(): " + horizontalPadding());
        return Math.round(pointsPerRow() * pixelPerPoint() + horizontalPadding() * 2);//左右边距
    }

    @Override
    public int pictureHeight() {
        return pixelPerCell() * cellCountPerGrid() * gridCountPerRow() * totalRows() + verticalPadding() * 2;//，一小格是1像素,5个小格组成一个大格，总共6个大格每行
    }

    @Override
    public int gridCountPerRow() {
        Log.d(TAG,"gridCountPerRow maxDataValueForMv: " + maxDataValueForMv);
        Log.d(TAG,"gridCountPerRow pointCount: " + pointCount);
        return (height / GRID_WIDTH);
        //return (height/SMALL_GRID_WIDTH);

        //if(pointCount == 5000) {
        //    //特殊使用方式，統一回傳格子數 6
        //    return 5;
        //} else {
        //    if (maxDataValueForMv > 2f){
        //        return 10;
        //    }else if (maxDataValueForMv > 1.5f){
        //        return 8;
        //    }else if (maxDataValueForMv == 1.5f){
        //        return 6;
        //    } else {
        //        return 3;
        //    }
        //}
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

    @Override
    public int pointsPerSecond() {
        return 500; //每秒心律資料多寡個數，目前設定每秒心律有500筆資料
    }

    @Override
    public int pixelPerCell() {
        return 20;//一个小格占5像素
    }

    @Override
    public float pixelPerPoint() {
        return 1f;//0.5像素/每点
    }

    @Override
    public int totalRows() {
        return pointCount%pointsPerRow() == 0?pointCount/pointsPerRow():pointCount/pointsPerRow()+1;//总共12行，2分钟数据
    }

    @Override
    public int horizontalPadding() {
        return 20;//水平方向，左右边距20个像素
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
