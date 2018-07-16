package com.jhsc.testwebservice;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jhsc.testwebservice.adapter.SuspectAdapter;
import com.jhsc.testwebservice.utils.PublicUtils;

public class SynchronousSuspectListActivity extends Activity {

    private RecyclerView mSynchromousSuspectRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronous_suspect_list);
        initView();
    }

    private void initView() {
        SuspectAdapter adapter = new SuspectAdapter(PublicUtils.faceImageList);
        mSynchromousSuspectRv = (RecyclerView) findViewById(R.id.synchromous_suspect_rv);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mSynchromousSuspectRv.setAdapter(adapter);
        mSynchromousSuspectRv.setLayoutManager(manager);
    }
}
