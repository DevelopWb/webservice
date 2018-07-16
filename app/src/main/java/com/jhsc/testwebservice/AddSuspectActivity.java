package com.jhsc.testwebservice;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jhsc.testwebservice.bean.FaceImage;
import com.jhsc.testwebservice.utils.ImageUtil;
import com.jhsc.testwebservice.utils.WebServiceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import static com.jhsc.testwebservice.utils.ImageUtil.adjustPhotoRotation;
import static com.jhsc.testwebservice.utils.ImageUtil.isHasSdcard;
import static com.jhsc.testwebservice.utils.ImageUtil.readPictureDegree;
import static com.jhsc.testwebservice.utils.ImageUtil.saveCroppedImage;

public class AddSuspectActivity extends Activity implements View.OnClickListener {

    private ImageView mSuspectPhotoIv;
    private EditText mSuspectNameEt;
    private EditText mSuspectIDEt;
    private EditText mSuspectPhoneEt;
    private EditText mSuspectImeiEt;
    private EditText mSuspectImsiEt;
    private EditText mSuspectUserImeiEt;
    private EditText mSuspectRegcodeEt;
    /**
     * 添      加
     */
    private TextView mAddSuspect;
    public String SDCARD_ROOT_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/.suspectphoto";
    public String IMAGE_CAPTURE_NAME = "/test.jpg";
    private String suspectPhotoPath;
    private Gson mGson;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_suspect);
        mGson = new Gson();
        sp = getSharedPreferences("ADDSUSPECT", MODE_PRIVATE);
        initView();
    }

    private void putValueToSp(String key, String value) {
        SharedPreferences.Editor et = sp.edit();
        et.putString(key, value);
        et.commit();
    }

    private String getValueFromSp(String key) {
        return sp.getString(key, "");
    }

    private void initView() {
        mSuspectPhotoIv = (ImageView) findViewById(R.id.suspect_photo_iv);
        mSuspectPhotoIv.setOnClickListener(this);
        mSuspectNameEt = (EditText) findViewById(R.id.suspect_name_et);
        mSuspectNameEt.setText(getValueFromSp("NAME"));
        mSuspectIDEt = (EditText) findViewById(R.id.suspect_ID_et);
        mSuspectIDEt.setText(getValueFromSp("ID"));
        mSuspectPhoneEt = (EditText) findViewById(R.id.suspect_phone_et);
        mSuspectPhoneEt.setText(getValueFromSp("PHONE"));
        mSuspectImeiEt = (EditText) findViewById(R.id.suspect_imei_et);
        mSuspectImeiEt.setText(getValueFromSp("IMEI"));
        mSuspectImsiEt = (EditText) findViewById(R.id.suspect_imsi_et);
        mSuspectImsiEt.setText(getValueFromSp("IMSI"));
        mSuspectUserImeiEt = (EditText) findViewById(R.id.suspect_user_imei_et);
        mSuspectUserImeiEt.setText(getValueFromSp("USERIMEI"));
        mSuspectRegcodeEt = (EditText) findViewById(R.id.suspect_regcode_et);
        mSuspectRegcodeEt.setText(getValueFromSp("REGCODE"));
        mAddSuspect = (TextView) findViewById(R.id.add_suspect);
        mAddSuspect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.suspect_photo_iv:
                File file = new File(SDCARD_ROOT_PATH);
                if (!file.exists()) {
                    file.mkdirs();
                }
                // 照相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (isHasSdcard()) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                            SDCARD_ROOT_PATH, IMAGE_CAPTURE_NAME)));
                }
                startActivityForResult(intent, 1);
                break;
            case R.id.add_suspect:
                String name = mSuspectNameEt.getText().toString().trim();
                String id = mSuspectIDEt.getText().toString().trim();
                String phone = mSuspectPhoneEt.getText().toString().trim();
                String imei = mSuspectImeiEt.getText().toString().trim();
                String imsi = mSuspectImsiEt.getText().toString().trim();
                String userImei = mSuspectUserImeiEt.getText().toString().trim();
                String regCode = mSuspectRegcodeEt.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(id) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(imei) || TextUtils.isEmpty(imsi) || TextUtils.isEmpty(userImei) || TextUtils.isEmpty(regCode)) {
                    Toast.makeText(this, "所有项目必须填写", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String value = getSuspectInfoForAdd(suspectPhotoPath, name, id, phone, imei, imsi, userImei, regCode);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result = WebServiceUtil.questToWebService("addOrUpdateSuspectInfo", getRequestHashMap(value));
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(result);
                            String Result = obj.getString("Result");
                            if (Result != null) {
                                if (Result.equals("ok")) {
                                    mHandler.sendEmptyMessage(1);
                                    return;
                                }
                                if (Result.equals("updateok")) {
                                    mHandler.sendEmptyMessage(2);
                                    return;
                                }
                                if (Result.equals("图片为空")) {
                                    mHandler.sendEmptyMessage(3);
                                    return;
                                }
                                if (Result.equals("没有检测到人脸")) {
                                    mHandler.sendEmptyMessage(4);
                                    return;
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }

    private HashMap<String, String> getRequestHashMap(String value) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("strfaceImage", value);
        return map;
    }

    /**
     * 获取将要添加的嫌疑人的信息
     *
     * @return
     */
    private String getSuspectInfoForAdd(String path, String name, String id, String phone, String imei, String imsi, String userImei, String regCode) {
        FaceImage faceImage = new FaceImage();
        if (path!=null&&!TextUtils.isEmpty(path)) {
            byte[] bytes = ImageUtil.image2byte(path);
            String hha = bytes.toString();
           String b = hha;
            faceImage.setFaceData(bytes);//设置图片，内容为buye数组
        }
        faceImage.setStrName(name);//嫌疑人姓名
        faceImage.setSiJian(id);//嫌疑人身份证号码
        faceImage.setPhone(phone);//嫌疑人手机号
        faceImage.setIMEI(imei);//嫌疑人手机的IMEI号
        faceImage.setIMSI(imsi);//嫌疑人手机的IMSI号
        faceImage.setAddImei(userImei);//侦查员手机的IMEI号
        faceImage.setAddRegCode(WebServiceUtil.RegistCode);//软件的注册码
        putValueToSp("NAME",name);
        putValueToSp("ID",id);
        putValueToSp("PHONE",phone);
        putValueToSp("IMEI",imei);
        putValueToSp("IMSI",imsi);
        putValueToSp("USERIMEI",userImei);
        putValueToSp("REGCODE",WebServiceUtil.RegistCode);
        String str = mGson.toJson(faceImage);
        return str;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bitmap mBitmap = null;
            if (requestCode == 1) {
                if (isHasSdcard()) {
                    String imagePath = SDCARD_ROOT_PATH
                            + IMAGE_CAPTURE_NAME;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
                    int i = readPictureDegree(imagePath);
                    if (i != 0) {
                        mBitmap = adjustPhotoRotation(bitmap, i);
                    } else {
                        mBitmap = bitmap;
                    }
                    suspectPhotoPath = saveCroppedImage(SDCARD_ROOT_PATH, mBitmap);
                    Glide.with(this).load(suspectPhotoPath).into(mSuspectPhotoIv);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(AddSuspectActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(AddSuspectActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(AddSuspectActivity.this, "添加嫌疑人，图片不能为空", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(AddSuspectActivity.this, "没有检测到人脸，请确认嫌疑人头像", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };


}
