package com.example.demo.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.common.exception.NotFoundException;
import com.example.demo.common.dto.ErrorMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestControllerExceptionAdvice {

	@ExceptionHandler(value = {NotFoundException.class})
	public ResponseEntity<Object> notFoundExceptionHandler(RuntimeException ex) {
		log.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(HttpStatus.NOT_FOUND.toString(), ex.getMessage()));
	}

}
