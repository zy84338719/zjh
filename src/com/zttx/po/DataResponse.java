package com.zttx.po;

public class DataResponse {
	private String IsSuccess;
	private Integer ResultCode;
	private String Message;
	
	public String getIsSuccess() {
		return IsSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		IsSuccess = isSuccess;
	}
	public Integer getResultCode() {
		return ResultCode;
	}
	public void setResultCode(Integer resultCode) {
		ResultCode = resultCode;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
}
