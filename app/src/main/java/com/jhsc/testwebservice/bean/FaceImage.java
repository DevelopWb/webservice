package com.jhsc.testwebservice.bean;

import java.io.Serializable;


public class FaceImage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer 	faceID		    ;//人员id
	private String	 	Shijian		    ;//时间
	private byte[] 		faceFeature	    ;//人脸特征
	private byte[] 		faceData	    ;//人员照片
	private String 		strName		    ;//人员姓名
	private String      siJian		    ;//身份证号
	private String 		phone		    ;//手机号码
	private String 		IMEI		    ;//嫌疑人Imei
	private String 		IMSI		    ;//嫌疑人Imsi
	private String 		AddImei			;//USER imei 
	private String 		AddRegCode	;//USER注册码
	private String 		captureDeviceID	;//捕获设备标识号 
	private String 		comparPlace 	;//视频捕获地点地点
	private String      CapturePhonePlace ;//号码捕获设备的地点 
	
	private String startTime;//开始时间
	private String endTime;//结束时间
	public Integer getFaceID() {
		return faceID;
	}
	public void setFaceID(Integer faceID) {
		this.faceID = faceID;
	}
	public byte[] getFaceFeature() {
		return faceFeature;
	}
	public void setFaceFeature(byte[] faceFeature) {
		this.faceFeature = faceFeature;
	}
	public byte[] getFaceData() {
		return faceData;
	}
	public void setFaceData(byte[] faceData) {
		this.faceData = faceData;
	}
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	public String getSiJian() {
		return siJian;
	}
	public void setSiJian(String siJian) {
		this.siJian = siJian;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIMEI() {
		return IMEI;
	}
	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}
	public String getIMSI() {
		return IMSI;
	}
	public void setIMSI(String iMSI) {
		IMSI = iMSI;
	}
	public String getAddImei() {
		return AddImei;
	}
	public void setAddImei(String addImei) {
		AddImei = addImei;
	}
	public String getAddRegCode() {
		return AddRegCode;
	}
	public void setAddRegCode(String addRegCode) {
		AddRegCode = addRegCode;
	}
	public String getCaptureDeviceID() {
		return captureDeviceID;
	}
	public void setCaptureDeviceID(String captureDeviceID) {
		this.captureDeviceID = captureDeviceID;
	}
	public String getComparPlace() {
		return comparPlace;
	}
	public void setComparPlace(String comparPlace) {
		this.comparPlace = comparPlace;
	}
	public String getCapturePhonePlace() {
		return CapturePhonePlace;
	}
	public void setCapturePhonePlace(String capturePhonePlace) {
		CapturePhonePlace = capturePhonePlace;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getShijian() {
		return Shijian;
	}

	public void setShijian(String shijian) {
		Shijian = shijian;
	}

	@Override
	public String toString() {
		return "FaceImage{" +
				"faceID=" + faceID +
				", Shijian='" + Shijian + '\'' +
				", strName='" + strName + '\'' +
				", siJian='" + siJian + '\'' +
				", phone='" + phone + '\'' +
				", IMEI='" + IMEI + '\'' +
				", IMSI='" + IMSI + '\'' +
				", AddImei='" + AddImei + '\'' +
				", AddRegCode='" + AddRegCode + '\'' +
				", captureDeviceID='" + captureDeviceID + '\'' +
				", comparPlace='" + comparPlace + '\'' +
				", CapturePhonePlace='" + CapturePhonePlace + '\'' +
				", startTime='" + startTime + '\'' +
				", endTime='" + endTime + '\'' +
				'}';
	}
}
