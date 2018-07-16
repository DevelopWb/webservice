package com.jhsc.testwebservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jhsc.testwebservice.bean.FaceCheck;
import com.jhsc.testwebservice.bean.FaceImage;
import com.jhsc.testwebservice.utils.PublicUtils;
import com.jhsc.testwebservice.utils.WebServiceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.jhsc.testwebservice.utils.WebServiceUtil.questToWebService;


public class MainActivity extends Activity implements OnClickListener {

    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private TextView mTv4;
    private TextView mTv5;
    private TextView mTv6;
    private TextView mTv7;
    private TextView mTv8;
    private TextView mTv9;
    private TextView mTv10;
    private TextView mTv11;
    private TextView mTv12;
    private String TAG = "MainActivity";
    private ExecutorService singleThreadExecutor;
    private Gson mGson;
    String path = "/mnt/sdcard/DCIM/Camera/yanggong.jpg";
    String path1 = "/mnt/sdcard/DCIM/Camera/yanggong1.jpg";
    boolean changeToPic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        singleThreadExecutor = Executors.newSingleThreadExecutor();
        initView();
        mGson = new Gson();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:
                singleThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        changeToPic = true;
                        String request = questToWebService("getFaceMatchByAddressAndTime", getArgumentsOfResult());
                        sendMsg(request, 1);
                    }
                });
                break;

            case R.id.tv2:
                singleThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        changeToPic = true;
                        String request = questToWebService("getFaceMatchBySuspectListAndTime", getSuspectListAndTimeInfoArguments2());
                        sendMsg(request, 1);
                    }
                });
                break;
            case R.id.tv3:
                singleThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        changeToPic = false;
                        String request = questToWebService("getNoTranslationPhoneInfoBySuspectListAndTime", getSuspectListAndTimeInfoArguments());
                        sendMsg(request, 1);
                    }
                });
                break;
            case R.id.tv4:
                singleThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        changeToPic = false;
                        String request = questToWebService("getTranslationPhoneInfoBySuspectListAndTime", getSuspectListAndTimeInfoArguments());
                        sendMsg(request, 1);
                    }
                });
                break;
            case R.id.tv5:
                singleThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        changeToPic = false;
                        String request = questToWebService("getAllPhoneInfoBySuspectListAndTime", getSuspectListAndTimeInfoArguments());
                        sendMsg(request, 1);
                    }
                });
                break;
            case R.id.tv6:
                singleThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        changeToPic = false;
                        String request = questToWebService("getNoTranslationPhoneInfoByCaptureDeviceID", getInfoByCaptureDeviceIDArguments());
                        sendMsg(request, 1);
                    }
                });
                break;
            case R.id.tv7:
                singleThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        changeToPic = false;
                        String request = questToWebService("getTranslationPhoneInfoByCaptureDeviceID", getInfoByCaptureDeviceIDArguments());
                        sendMsg(request, 1);
                    }
                });
                break;
            case R.id.tv8:
                Intent intent8 = new Intent(MainActivity.this, AddSuspectActivity.class);
                startActivity(intent8);
                break;
            case R.id.tv9:
                singleThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        changeToPic = false;
                        String request = questToWebService("deleteSuspectInfoBySuspectNameAndSuspectIdNumber", getRequestHashMap11());
                        sendMsg(request, 1);
                    }
                });
                break;

            case R.id.tv10:
                singleThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        changeToPic = false;
                        String request = questToWebService("setFaceAddressBySuspectList", getRequestHashMapForSetFaceAddr());
                        sendMsg(request, 1);
                    }
                });

                break;
            case R.id.tv11:
                singleThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        changeToPic = false;
                        String request = questToWebService("setPhoneAddressBySuspectList", getRequestHashMapForSetPhoneAddr());
                        sendMsg(request, 1);
                    }
                });
                break;
            case R.id.tv12:
                singleThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        String request = questToWebService("synchronousSuspectList", getRequestHashMapForSynchronous());
                        sendMsg(request, 3);
                    }
                });
                break;
            default:
                break;

        }
    }

    /**
     * 获取将要删除的嫌疑人的信息
     *
     * @return
     */
    private String getSuspectInfoForDel() {
        FaceImage faceImage = new FaceImage();
        faceImage.setStrName("王彬");//嫌疑人姓名
        faceImage.setSiJian("371325198702280001");//嫌疑人身份证号码
        faceImage.setAddImei("355009076401902");//侦查员手机的IMEI号
        faceImage.setAddRegCode(WebServiceUtil.RegistCode);//软件的注册码
        return mGson.toJson(faceImage);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    private String getEndTime() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 获取通过SuspectListAndTime条件的方法对应的参数
     *
     * @return
     */
    private HashMap<String, String> getSuspectListAndTimeInfoArguments() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("suspectsList", getSuspectListToString());
        map.put("setMonitorAddress", "海淀区增光路90号");
        map.put("captureDeviceID", "2017062800");
        map.put("startTime", "2017-07-10 10:30:30");
        map.put("endTime", getEndTime());
        map.put("userImei", "355009076401902");
        map.put("userRegistCode", WebServiceUtil.RegistCode);
        return map;
    }

    /**
     * 获取通过SuspectListAndTime条件的方法对应的参数
     *
     * @return
     */
    private HashMap<String, String> getSuspectListAndTimeInfoArguments2() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("suspectsList", getSuspectListToString2());
        map.put("setMonitorAddress", "海淀区增光路90号");
        map.put("startTime", "2017-07-01 10:30:30");
        map.put("endTime", getEndTime());
        map.put("userImei", "355009076401902");
        map.put("userRegistCode", WebServiceUtil.RegistCode);
        return map;
    }




    /**
     * 人脸对比结果条件
     *
     * @return
     */
    private HashMap<String, String> getArgumentsOfResult() {
        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("suspectsList", getSuspectListInfoForSetFaceAddr());
        map.put("SetMonitorAddress", "海淀区增光路90号");
        map.put("startTime", "2018-3-20 10:30:30");
        map.put("endTime", getEndTime());
        map.put("userImei", "355009076401902");
        map.put("userRegistCode", WebServiceUtil.RegistCode);
        return map;
    }


    /**
     * 获取嫌疑人列表
     *
     * @return
     */
    private String getSuspectListToString() {
        List<FaceImage> faceImages = new ArrayList<FaceImage>();
        FaceImage faceImage1 = new FaceImage();
        faceImage1.setStrName("王彬");
        faceImage1.setPhone("15311810032");
        faceImage1.setIMEI("860562030358470");
        faceImage1.setIMSI("460017366903209");
        faceImage1.setSiJian("371325198702280001");
        faceImage1.setCapturePhonePlace("海淀区增光路90号");
        faceImages.add(faceImage1);
//        FaceImage faceImage2 = new FaceImage();
//        faceImage2.setStrName("刘华强");
//        faceImage2.setPhone("15311810034");
//        faceImage2.setIMEI("857694857364524");
//        faceImage2.setIMSI("460012837465869704");
//        faceImage2.setSiJian("371345198402021234");
//        faceImage2.setCapturePhonePlace("海淀区增光路30号");
//        faceImages.add(faceImage2);
        return mGson.toJson(faceImages);
    }

    /**
     * 获取嫌疑人列表
     *
     * @return
     */
    private String getSuspectListToString2() {
        List<FaceImage> faceImages = new ArrayList<FaceImage>();
//        FaceImage faceImage1 = new FaceImage();
//        faceImage1.setStrName("强强");
//        faceImage1.setPhone("17838374653");
//        faceImage1.setIMEI("857694857364525");
//        faceImage1.setIMSI("460012837465869745");
//        faceImage1.setSiJian("371323199111112539");
//        faceImage1.setComparPlace("海淀区增光路30号");
//        faceImages.add(faceImage1);
        FaceImage faceImage2 = new FaceImage();
        faceImage2.setStrName("王彬");
        faceImage2.setPhone("15311810032");
        faceImage2.setIMEI("1234567890");
        faceImage2.setIMSI("1234567890");
        faceImage2.setSiJian("371325198702280001");
        faceImage2.setComparPlace("海淀区增光路90号");
        faceImages.add(faceImage2);
//        FaceImage faceImage3 = new FaceImage();
//        faceImage3.setStrName("杨工");
//        faceImage3.setPhone("15311810003");
//        faceImage3.setIMEI("460005253673679");
//        faceImage3.setIMSI("868756022578170");
//        faceImage3.setSiJian("371325198702280003");
//        faceImage3.setComparPlace("龙轩宾馆506");
//        faceImages.add(faceImage3);
        return mGson.toJson(faceImages);
    }

    /**
     * 获取通过CaptureDeviceID条件对应方法的参数
     *
     * @return
     */
    private HashMap<String, String> getInfoByCaptureDeviceIDArguments() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("captureDeviceID", "2017062800");
        map.put("startTime", "2018-02-18 10:30:30");
        map.put("endTime", getEndTime());
        map.put("userImei", "355009076401902");
        map.put("userRegistCode", WebServiceUtil.RegistCode);
        return map;
    }

    private HashMap<String, String> getRequestHashMap11() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("strfaceImage", getSuspectInfoForDel());
        return map;
    }

    private HashMap<String, String> getRequestHashMapForSetFaceAddr() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("suspectsList", getSuspectListInfoForSetFaceAddr());
        return map;
    }

    private HashMap<String, String> getRequestHashMapForSetPhoneAddr() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("suspectsList", getSuspectListInfoForSetPhoneAddr());
        return map;
    }
    private HashMap<String, String> getRequestHashMapForSynchronous() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("suspectsList", getSuspectListInfoForSynchronous());
        return map;
    }

    /**
     * 获取设置人脸地址的嫌疑人列表
     *
     * @return
     */
    private String getSuspectListInfoForSetFaceAddr() {
        List<FaceImage> faceImages = new ArrayList<FaceImage>();
        FaceImage faceImage1 = new FaceImage();
        faceImage1.setStrName("王彬");
        faceImage1.setPhone("15311810032");
        faceImage1.setIMEI("1234567890");
        faceImage1.setIMSI("1234567890");
        faceImage1.setSiJian("371325198702280001");
        faceImage1.setComparPlace("海淀区增光路90号");
        faceImages.add(faceImage1);


//        FaceImage faceImage2 = new FaceImage();
//        faceImage2.setStrName("果冻");
//        faceImage2.setPhone("15311810002");
//        faceImage2.setIMEI("857694857360002");
//        faceImage2.setIMSI("460012837465860002");
//        faceImage2.setSiJian("371325198702280002");
//        faceImage2.setComparPlace("海淀区增光路80号");
//        faceImages.add(faceImage2);
//        FaceImage faceImage3 = new FaceImage();
//        faceImage3.setStrName("杨工");
//        faceImage3.setPhone("15311810003");
//        faceImage3.setIMEI("460005253673679");
//        faceImage3.setIMSI("868756022578170");
//        faceImage3.setSiJian("371325198702280003");
//        faceImage3.setComparPlace("龙轩宾馆506");
//        faceImages.add(faceImage3);
//        FaceImage faceImage4 = new FaceImage();
//        faceImage4.setStrName("全");
//        faceImage4.setPhone("15311810038");
//        faceImage4.setIMEI("857694857364528");
//        faceImage4.setIMSI("460012837465869708");
//        faceImage4.setSiJian("371345198402021238");
//        faceImage4.setComparPlace("海淀区增光路30号");
//        faceImages.add(faceImage4);
//        FaceImage faceImage5 = new FaceImage();
//        faceImage5.setStrName("闫硕");
//        faceImage5.setPhone("15311810037");
//        faceImage5.setIMEI("857694857364527");
//        faceImage5.setIMSI("460012837465869707");
//        faceImage5.setSiJian("371345198402021237");
//        faceImage5.setComparPlace("海淀区增光路30号");
//        faceImages.add(faceImage5);
        return mGson.toJson(faceImages);
    }

    /**
     * 获取设置捕捉号码的地址的嫌疑人列表
     *
     * @return
     */
    private String getSuspectListInfoForSetPhoneAddr() {
        List<FaceImage> faceImages = new ArrayList<FaceImage>();
        FaceImage faceImage1 = new FaceImage();
        faceImage1.setStrName("王彬");
        faceImage1.setPhone("15311810032");
        faceImage1.setIMEI("1234567890");
        faceImage1.setIMSI("1234567890");
        faceImage1.setSiJian("371325198702280001");
        faceImage1.setCapturePhonePlace("海淀区增光路90号");
        faceImages.add(faceImage1);
//        FaceImage faceImage3 = new FaceImage();
//        faceImage3.setStrName("杨工");
//        faceImage3.setPhone("15311810003");
//        faceImage3.setIMEI("460005253673679");
//        faceImage3.setIMSI("868756022578170");
//        faceImage3.setSiJian("371325198702280003");
//        faceImage3.setCapturePhonePlace("龙轩宾馆555");
//        faceImages.add(faceImage3);
//        FaceImage faceImage2 = new FaceImage();
//        faceImage2.setStrName("刘华强");
//        faceImage2.setPhone("15311810034");
//        faceImage2.setIMEI("857694857364524");
//        faceImage2.setIMSI("460012837465869704");
//        faceImage2.setSiJian("371345198402021234");
//        faceImage2.setCapturePhonePlace("海淀区增光路40号");
//        faceImages.add(faceImage2);
        return mGson.toJson(faceImages);
    }
    private String getSuspectListInfoForSynchronous() {
        List<FaceImage> faceImages = new ArrayList<FaceImage>();
        FaceImage faceImage1 = new FaceImage();
        faceImage1.setStrName("王彬");
        faceImage1.setSiJian("371325198702280001");
        faceImages.add(faceImage1);
//        FaceImage faceImage2 = new FaceImage();
//        faceImage2.setStrName("小李");
//        faceImage2.setSiJian("456693164578164458");
//        faceImages.add(faceImage2);
        return mGson.toJson(faceImages);
    }
    private void initView() {
        mTv1 = (TextView) findViewById(R.id.tv1);
        mTv1.setOnClickListener(this);
        mTv2 = (TextView) findViewById(R.id.tv2);
        mTv2.setOnClickListener(this);
        mTv3 = (TextView) findViewById(R.id.tv3);
        mTv3.setOnClickListener(this);
        mTv4 = (TextView) findViewById(R.id.tv4);
        mTv4.setOnClickListener(this);
        mTv5 = (TextView) findViewById(R.id.tv5);
        mTv5.setOnClickListener(this);
        mTv6 = (TextView) findViewById(R.id.tv6);
        mTv6.setOnClickListener(this);
        mTv7 = (TextView) findViewById(R.id.tv7);
        mTv7.setOnClickListener(this);
        mTv8 = (TextView) findViewById(R.id.tv8);
        mTv8.setOnClickListener(this);
        mTv9 = (TextView) findViewById(R.id.tv9);
        mTv9.setOnClickListener(this);
        mTv10 = (TextView) findViewById(R.id.tv10);
        mTv10.setOnClickListener(this);
        mTv11 = (TextView) findViewById(R.id.tv11);
        mTv11.setOnClickListener(this);
        mTv12 = (TextView) findViewById(R.id.tv12);
        mTv12.setOnClickListener(this);
    }



    /**
     * 发送handler消息
     *
     * @param obj
     * @param i
     */
    private void sendMsg(String obj, int i) {
        Message msg = new Message();
        msg.obj = obj;
        msg.what = i;
        mHandler.sendMessage(msg);
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 1:
                    if (changeToPic) {

                        String result = (String) msg.obj;
                        if (result==null|| TextUtils.isEmpty(result)) {
                            Toast.makeText(MainActivity.this, "服务器返回异常", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject obj = null;
                        JSONArray list = null;
                        try {
                            obj = new JSONObject(result);
                            String Result = obj.getString("Result");
                            if (Result != null) {
                                if (!Result.equals("ok")) {
                                    Toast.makeText(MainActivity.this, Result, Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            list = obj.getJSONArray("Model");
                            List<FaceCheck> faceCheckList = null;
                            faceCheckList = PublicUtils.fromJsonList(mGson, list.toString(), FaceCheck.class);
                                if (Result.equals("ok")) {
                                    PublicUtils.mFaceCheckList.clear();
                                    PublicUtils.mFaceCheckList = faceCheckList;
                                }else{
                                    Toast.makeText(MainActivity.this, Result, Toast.LENGTH_SHORT).show();
                                    return;
                                }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(MainActivity.this, DisplayPicActivity.class);
                        startActivity(intent);
                    } else {
                        String result = (String) msg.obj;
                        PublicUtils.str=result;
                        Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                        startActivity(intent);
                    }

                    break;
                case 2:
                    Toast.makeText(MainActivity.this, "图片不存在", Toast.LENGTH_SHORT).show();
                    break;

                case 3:
                    String result = (String) msg.obj;
                    JSONObject obj = null;
                    JSONArray list = null;
                    try {
                        obj = new JSONObject(result);
                        String Result = obj.getString("Result");

                        list = obj.getJSONArray("Model");
                        List<FaceImage> faceImagesList = null;
                        faceImagesList = PublicUtils.fromJsonList(mGson, list.toString(), FaceImage.class);
                        if (Result != null) {
                            if (Result.equals("ok")) {
                                PublicUtils.faceImageList = faceImagesList;
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(MainActivity.this, SynchronousSuspectListActivity.class);
                    startActivity(intent);
                    break;
                case 8:
//                    String request = questToWebService("addOrUpdateSuspectInfo", getRequestHashMap10())

                    break;
                default:
                    break;
            }

        }
    };
}

