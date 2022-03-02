package com.jc666.ecglibrary;

import android.util.Log;

/**
 * @author JC666
 * @date 2021/12/15
 * @describe TODO
 */
public class LuckySoftStrategy implements SoftStrategy {
    private String TAG = this.getClass().getSimpleName();

    private int pointCount;

    private float maxDataValueForMv;//默认每行所表示的上下最大毫伏数 (maxDataValueForMv,-maxDataValueForMv)

    public LuckySoftStrategy(int pointCount){
        this.pointCount = pointCount;
        this.maxDataValueForMv = 1.0f;
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
        if(pointCount > 30000) {
            return 3;
        } if(pointCount == 5000) {
            //特殊使用方式，統一回傳格子數 6
            return 6;
        } else {
            if (maxDataValueForMv > 2f){
                return 10;
            }else if (maxDataValueForMv > 1.5f){
                return 8;
            }else if (maxDataValueForMv == 1.5f){
                return 6;
            } else {
                return 3;
            }
        }
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
//        return 250;
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
