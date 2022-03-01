package com.jc666.customviewtopdfprint;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc666.ecglibrary.ECGPointValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author JC666
 * @date 2021/12/17
 * @describe TODO
 */
public class ECGDataParse {

    private ECGPointValue[] values;

    private ECGPointValue[] valuesOneLeadTest = new ECGPointValue[5000];

    private ECGPointValue[] valuesTest = new ECGPointValue[20000];

    private ECGPointValue[] valuesSixTest = new ECGPointValue[30000];

    private ECGPointValue[] valuesTwelveTest = new ECGPointValue[60000];

    public ECGDataParse(Context context){
        String json = parseJson(context,"ecgData.json");
        Gson gson = new Gson();
        values = gson.fromJson(json,new TypeToken<ECGPointValue[]>(){}.getType());
    }

    private static String parseJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public ECGPointValue[] getValuesOneLeadTest() {
        for(int index=0; index < 5000; index++) {
            valuesOneLeadTest[index] = values[index];
        }
        return valuesOneLeadTest;
    }

    public ECGPointValue[] getValues() {
        for(int index=0; index < 10000; index++) {
            valuesTest[index] = values[index];
            valuesTest[index + 10000] = values[index];
        }
        return valuesTest;
    }

    public ECGPointValue[] getValuesSix() {
        for(int index=0; index < 10000; index++) {
            valuesSixTest[index] = values[index];
            valuesSixTest[index + 10000] = values[index];
            valuesSixTest[index + 20000] = values[index];
        }
        return valuesSixTest;
    }

    public ECGPointValue[] getValuesTwelve() {
        for(int index=0; index < 10000; index++) {
            valuesTwelveTest[index] = values[index];
            valuesTwelveTest[index + 10000] = values[index];
            valuesTwelveTest[index + 20000] = values[index];
            valuesTwelveTest[index + 30000] = values[index];
            valuesTwelveTest[index + 40000] = values[index];
            valuesTwelveTest[index + 50000] = values[index];
        }
        return valuesTwelveTest;
    }
}
