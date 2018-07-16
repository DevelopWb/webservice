package com.jhsc.testwebservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jhsc.testwebservice.R;
import com.jhsc.testwebservice.bean.FaceImage;
import com.jhsc.testwebservice.utils.ImageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王sir} on 2017/8/2.
 * application
 */

public class SuspectAdapter extends RecyclerView.Adapter<SuspectAdapter.ViewHolder> {
    List<FaceImage> arrays = new ArrayList<>();
    private String REQUEST_PIC_PATH = "/mnt/sdcard/DCIM/Camera/.suspectphoto/";
    Context context;

    public SuspectAdapter(List<FaceImage> arrays) {
        this.arrays = arrays;
    }

    @Override
    public SuspectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_pic_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SuspectAdapter.ViewHolder holder, int position) {
        FaceImage faceImage = arrays.get(position);
        loadPicture(faceImage, holder.mDisplayIv);
        holder.mNameTv.setText("姓名：" + faceImage.getStrName());
        holder.mCardIdTv.setText("身份证号：" + faceImage.getSiJian());
        holder.mPhoneTv.setText("手机号：" + faceImage.getPhone());
        holder.mImeiTv.setText("IMEI：" + faceImage.getIMEI());
        holder.mImsiTv.setText("IMSI：" + faceImage.getIMSI());
        holder.compareAddr_tv.setText("捕获地点：" + faceImage.getComparPlace());
    }

    /**
     * 加载图片
     *
     * @param imageView
     */
    private void loadPicture(FaceImage faceImage, ImageView imageView) {
        File file_path = new File(REQUEST_PIC_PATH);
        if (!file_path.exists()) {
            file_path.mkdir();
        }
        byte[] data = faceImage.getFaceData();
        ImageUtil.byte2image(data, REQUEST_PIC_PATH ,faceImage.getSiJian() + ".png");
        File file = new File(REQUEST_PIC_PATH + faceImage.getSiJian() + ".png");
        if (file.exists()) {
            Glide.with(context).load(REQUEST_PIC_PATH + faceImage.getSiJian() + ".png").into(imageView);
        }
    }

    @Override
    public int getItemCount() {
        return arrays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mDisplayIv;
        private TextView mNameTv;
        private TextView mCardIdTv;
        private TextView mPhoneTv;
        private TextView mImeiTv;
        private TextView mImsiTv;
        private TextView mCompareTimeTv;
        private TextView mCompareValueTv;
        private TextView compareAddr_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            mDisplayIv = (ImageView) itemView.findViewById(R.id.display_iv);
            mNameTv = (TextView) itemView.findViewById(R.id.name_tv);
            mCardIdTv = (TextView) itemView.findViewById(R.id.cardId_tv);
            mPhoneTv = (TextView) itemView.findViewById(R.id.phone_tv);
            mImeiTv = (TextView) itemView.findViewById(R.id.imei_tv);
            mImsiTv = (TextView) itemView.findViewById(R.id.imsi_tv);
            mCompareTimeTv = (TextView) itemView.findViewById(R.id.compareTime_tv);
            mCompareValueTv = (TextView) itemView.findViewById(R.id.compareValue_tv);
            compareAddr_tv = (TextView) itemView.findViewById(R.id.compareAddr_tv);

        }
    }
}
