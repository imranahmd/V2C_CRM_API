package com.crm.dto;

import java.io.Serial;

public class BusinessException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;


    private final String statusCode;
    private final String message;

    public BusinessException(String statusCode, String message) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }

}
