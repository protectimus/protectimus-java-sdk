/**
 * Copyright (C) 2013-2016 Protectimus Solutions <support@protectimus.com>
 *
 * This file is a part of the Protectimus.
 *
 * Protectimus can not be copied and/or distributed without the express
 * permission of Protectimus Solutions LLP.
 */
package com.protectimus.api.sdk.exceptions;

public class ProtectimusApiException extends Exception {

	private static final long serialVersionUID = 6507605421894258322L;
	private ErrorCode errorCode;
	private String developerMessage;
	private int httpResponseStatusCode;

	public ProtectimusApiException(String message, String developerMessage,
			Throwable cause, ErrorCode errorCode) {
		super(message, cause);
		this.developerMessage = developerMessage;
		this.errorCode = errorCode;
	}

	public ProtectimusApiException(String message, String developerMessage,
			ErrorCode errorCode) {
		super(message);
		this.developerMessage = developerMessage;
		this.errorCode = errorCode;
	}

	public ProtectimusApiException(String message, String developerMessage,
			Throwable cause, ErrorCode errorCode, int httpResponseStatusCode) {
		super(message, cause);
		this.developerMessage = developerMessage;
		this.errorCode = errorCode;
		this.httpResponseStatusCode = httpResponseStatusCode;
	}

	public ProtectimusApiException(String message, String developerMessage,
			ErrorCode errorCode, int httpResponseStatusCode) {
		super(message);
		this.developerMessage = developerMessage;
		this.errorCode = errorCode;
		this.httpResponseStatusCode = httpResponseStatusCode;
	}

	public enum ErrorCode {

		// duplicated exceptions
		ALREADY_EXIST(1001),

		// size exceptions
		INVALID_PARAMETER_LENGTH(2001),

		// database exceptions
		DB_ERROR(3001),

		// unregistered exceptions
		UNREGISTERED_NAME(4001),

		// missing information exceptions
		MISSING_PARAMETER(5001), MISSING_DB_ENTITY(5002),

		// invalid information exceptions
		INVALID_PARAMETER(6001), INVALID_URL_PATTERN(6002),

		// access restriction exceptions
		ACCESS_RESTRICTION(7001),

		// internal server error
		INTERNAL_SERVER_ERROR(8001),

		// unknown error exception
		UNKNOWN_ERROR(9001);

		private final int code;

		private ErrorCode(int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public int getHttpResponseStatusCode() {
		return httpResponseStatusCode;
	}

}