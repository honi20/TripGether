package com.wooyeah.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wooyeah.exception.ErrorCode;
import com.wooyeah.exception.ErrorResponse;
import com.wooyeah.exception.InvalidIdException;
import com.wooyeah.exception.NotFoundUserException;

@RestControllerAdvice(annotations = RestController.class)
public class ControllerExceptionHandler {

	@ExceptionHandler(value = {NotFoundUserException.class})
	public ResponseEntity<?> notFoundUserHandler(NullPointerException e) {

		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_FOUND_USER);
		
		return ResponseEntity.badRequest().body(errorResponse);
	}
	
	@ExceptionHandler(value = {InvalidIdException.class})
	public ResponseEntity<?> invalidEmailHandler(NullPointerException e) {
		
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_ID);
		
		return ResponseEntity.badRequest().body(errorResponse);
	}
}
