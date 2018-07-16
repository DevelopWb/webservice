package com.jhsc.testwebservice.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jhsc.testwebservice.bean.FaceCheck;
import com.jhsc.testwebservice.bean.FaceImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王sir} on 2017/7/24.
 * application
 */

public class PublicUtils {


    public static List<FaceCheck> mFaceCheckList = new ArrayList<>();
    public static List<FaceImage> faceImageList = new ArrayList<>();
    public static String str = "";

       /**
     * 推荐使用这个
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> ArrayList<T> fromJsonList(Gson gson,String json, Class<T> cls) {
        ArrayList<T> mList = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            mList.add(gson.fromJson(elem, cls));
        }
        return mList;
    }

}
