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
import com.jhsc.testwebservice.bean.FaceCheck;
import com.jhsc.testwebservice.bean.FaceImage;
import com.jhsc.testwebservice.utils.ImageUtil;

import java.io.File;
import java.util.List;

/**
 * Created by ${王sir} on 2017/7/26.
 * application
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    List<FaceCheck> arrays;
    Context context;
    private String REQUEST_PIC_PATH = "/mnt/sdcard/DCIM/Camera/.suspectPics/"+System.currentTimeMillis()+"/";
    private String REQUEST_PIC_PATH_ = "/mnt/sdcard/DCIM/Camera/.suspectPics";
    public MyAdapter(List<FaceCheck> arrays) {
        this.arrays =arrays;
        File file = new File(REQUEST_PIC_PATH_);
        if (file.exists()) {
            deleteAllFiles(file);
        }

    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.display_pic_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        FaceCheck faceCheck = arrays.get(position);
        loadPicture(position,faceCheck,holder.mDisplayIv);
       FaceImage faceImage =  faceCheck.getFaceImage();
       holder.mNameTv.setText("姓名："+faceImage.getStrName());
       holder.mCardIdTv.setText("身份证号："+faceImage.getSiJian());
       holder.mPhoneTv.setText("手机号："+faceImage.getPhone());
       holder.mImeiTv.setText("IMEI："+faceImage.getIMEI());
       holder.mImsiTv.setText("IMSI："+faceImage.getIMSI());
       holder.compareAddr_tv.setText("捕获地点："+faceImage.getComparPlace());
       holder.mCompareTimeTv.setText("比对时间："+faceCheck.getTmTime());
       holder.mCompareValueTv.setText("相似度："+faceCheck.getfXSD()*100+"%");
    }
    private void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }
    @Override
    public int getItemCount() {
        return arrays.size();
    }

    /**
     * 加载图片
     * @param faceCheck
     * @param imageView
     */
    private void loadPicture(int position,FaceCheck faceCheck, ImageView imageView) {
        File file_path = new File(REQUEST_PIC_PATH);
        if (!file_path.exists()) {
            file_path.mkdirs();
        }
        byte[] data = faceCheck.getThisFace();
        ImageUtil.byte2image(data, REQUEST_PIC_PATH , position + ".png");
        File file = new File(REQUEST_PIC_PATH , position + ".png");
        if (file.exists()) {
            Glide.with(context).load(REQUEST_PIC_PATH +position + ".png").into(imageView);
        }
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
