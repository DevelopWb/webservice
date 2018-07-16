package com.jhsc.testwebservice;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.jhsc.testwebservice.utils.PublicUtils;

public class DisplayActivity extends Activity {

    private TextView mTvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        initView();
    }

    private void initView() {
        mTvDisplay = (TextView) findViewById(R.id.tv_display);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mTvDisplay.setText(PublicUtils.str);
            }
        }).start();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
