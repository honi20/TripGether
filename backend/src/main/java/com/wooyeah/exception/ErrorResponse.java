package com.wooyeah.exception;

public class ErrorResponse {

	private int httpStatus;
	private String message;
	private StringBuilder sb = new StringBuilder();
	
	public ErrorResponse(ErrorCode errorCode) {
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
	
	public static ErrorResponse of(ErrorCode errorCode) {
		return new ErrorResponse(errorCode);
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		/**
		 * {
		 * "httpStatus": 400,
		 * "message": "error"
		 * }
		 */
		sb.append("{")
		.append("\"httpStatus\": ")
		.append(this.httpStatus)
		.append(", ")
		.append("\"message\": ")
		.append(this.message)
		.append("\" }");
		
		return sb.toString();
	}
}
