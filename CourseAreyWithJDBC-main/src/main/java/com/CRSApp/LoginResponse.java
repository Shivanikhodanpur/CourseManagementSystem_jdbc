package com.CRSApp;

public class LoginResponse {

	private Object curUserObject;
	private int responseCode;

	public Object getCurUserObject() {
		return curUserObject;
	}

	public void setCurUserObject(Object curUserObject) {
		this.curUserObject = curUserObject;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
}