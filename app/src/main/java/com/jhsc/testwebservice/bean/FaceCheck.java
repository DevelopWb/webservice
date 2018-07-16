package com.jhsc.testwebservice.bean;

import java.io.Serializable;
import java.util.Arrays;


/**
 * @author Administrator
 *
 */
public class FaceCheck implements Serializable {

	private static final long serialVersionUID = 1L;


	private Integer 	FaceCheckID		;//
	private byte[]  	FaceFeature1	;//人脸特征
	private Integer 	outplane1		;
	private Integer 	face_num1		;
	private byte[]  	ThisFace		;
	private Integer 	Faceid			;//嫌疑人ID
	private String    	tmTime			;
	private Float   	fXSD			;//比对结果
	private Integer 	nHostID			;
	private Integer 	nIndex			;
	private Integer 	nShow			;
	private Integer 	nChuLi			;
	private String  	strCurUser		;
	private String    	CtmTime			;

	private FaceImage faceImage;

	public Integer getFaceCheckID() {
		return FaceCheckID;
	}

	public byte[] getFaceFeature1() {
		return FaceFeature1;
	}

	public Integer getOutplane1() {
		return outplane1;
	}

	public Integer getFace_num1() {
		return face_num1;
	}

	public byte[] getThisFace() {
		return ThisFace;
	}

	public Integer getFaceid() {
		return Faceid;
	}

	public String getTmTime() {
		return tmTime;
	}

	public Float getfXSD() {
		return fXSD;
	}

	public Integer getnHostID() {
		return nHostID;
	}

	public Integer getnIndex() {
		return nIndex;
	}

	public Integer getnShow() {
		return nShow;
	}

	public Integer getnChuLi() {
		return nChuLi;
	}

	public String getStrCurUser() {
		return strCurUser;
	}

	public String getCtmTime() {
		return CtmTime;
	}

	public FaceImage getFaceImage() {
		return faceImage;
	}

	public void setFaceCheckID(Integer faceCheckID) {
		FaceCheckID = faceCheckID;
	}

	public void setFaceFeature1(byte[] faceFeature1) {
		FaceFeature1 = faceFeature1;
	}

	public void setOutplane1(Integer outplane1) {
		this.outplane1 = outplane1;
	}

	public void setFace_num1(Integer face_num1) {
		this.face_num1 = face_num1;
	}

	public void setThisFace(byte[] thisFace) {
		ThisFace = thisFace;
	}

	public void setFaceid(Integer faceid) {
		Faceid = faceid;
	}

	public void setTmTime(String tmTime) {
		this.tmTime = tmTime;
	}

	public void setfXSD(Float fXSD) {
		this.fXSD = fXSD;
	}

	public void setnHostID(Integer nHostID) {
		this.nHostID = nHostID;
	}

	public void setnIndex(Integer nIndex) {
		this.nIndex = nIndex;
	}

	public void setnShow(Integer nShow) {
		this.nShow = nShow;
	}

	public void setnChuLi(Integer nChuLi) {
		this.nChuLi = nChuLi;
	}

	public void setStrCurUser(String strCurUser) {
		this.strCurUser = strCurUser;
	}

	public void setCtmTime(String ctmTime) {
		CtmTime = ctmTime;
	}

	public void setFaceImage(FaceImage faceImage) {
		this.faceImage = faceImage;
	}

	@Override
	public String toString() {
		return "FaceCheck [FaceCheckID=" + FaceCheckID + ", outplane1=" + outplane1 + ", face_num1=" + face_num1
				+ ", ThisFace=" + Arrays.toString(ThisFace) + ", Faceid=" + Faceid + ", tmTime=" + tmTime + ", fXSD="
				+ fXSD + ", nHostID=" + nHostID + ", nIndex=" + nIndex + ", nShow=" + nShow + ", nChuLi=" + nChuLi
				+ ", strCurUser=" + strCurUser + ", CtmTime=" + CtmTime + ", faceImage=" + faceImage + "]";
	}
}
