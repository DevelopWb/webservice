package com.jhsc.testwebservice;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jhsc.testwebservice.adapter.MyAdapter;
import com.jhsc.testwebservice.utils.PublicUtils;

public class DisplayPicActivity extends Activity {


    private RecyclerView mDisplayPicRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaplay_pic);
        initView();
    }

    private void initView() {

        MyAdapter adapter = new MyAdapter(PublicUtils.mFaceCheckList);
        mDisplayPicRv = (RecyclerView) findViewById(R.id.display_pic_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mDisplayPicRv.setLayoutManager(linearLayoutManager);
        mDisplayPicRv.setAdapter(adapter);

//        if (PublicUtils.faceImageList.size() > 0) {
//            mDisplayLl.removeAllViews();
//            for (FaceCheck faceCheck : PublicUtils.faceImageList) {
//                ImageView imageView = new ImageView(this);
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400, 400);
//
//                imageView.setLayoutParams(params);
//                loadPicture(faceCheck, imageView);
//            }
//
//
//        }


    }

}
