package com.jhsc.testwebservice.utils;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import static android.content.ContentValues.TAG;

public class WebServiceUtil {
    // 定义webservice的命名空间
//    public static  String WSDL_URI = "http://192.168.10.159:9696/WebService/webservice/FaceCheckWebService?wsdl";//wsdl 的uri
    private static  String WSDL_URI = "http://218.246.35.199:9696/WebService/webservice/FaceCheckWebService?wsdl";//wsdl 的uri
    public static String NAME_SPACE = "http://service.cxf.aast.com/";//namespace
    public static String  RegistCode = "9Y6G8I73";



    /**
     * 添加或者更新修改当前嫌疑人基本信息
     */
    public static String questToWebService(String methodName,HashMap<String,String> map) {
        String result = "";
        //（1）创建HttpTransportSE对象，该对象用于调用WebService操作
        HttpTransportSE httpTransportSE = new HttpTransportSE(WSDL_URI);
        //（2）创建SoapSerializationEnvelope对象
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //（3）创建SoapObject对象，创建该对象时需要传入所要调用的Web Service的命名空间和WebService方法名
        SoapObject request = new SoapObject(NAME_SPACE, methodName);
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()){
            String key = (String) iterator.next();
            String value = map.get(key);
            request.addProperty(key,value);
        }
        //（5）调用SoapSerializationEnvelope的setOutputSoapObject()方法，或者直接对bodyOut属性赋值，
        //将前两步创建的SoapObject对象设为SoapSerializationEnvelope的传出SOAP消息体
        envelope.bodyOut = request;
        try {
            //（6）调用对象的call()方法，并以SoapSerializationEnvelope作为参数调用远程的web service
            httpTransportSE.call(methodName, envelope);//调用
            if (envelope.getResponse() != null) {
                result = envelope.getResponse().toString().trim();
            }
            Log.e(TAG, methodName+result );
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        //解析该对象，即可获得调用web service的返回值

        return result;
    }



}
