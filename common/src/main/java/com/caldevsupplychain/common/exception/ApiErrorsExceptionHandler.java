package com.caldevsupplychain.common.exception;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.caldevsupplychain.common.ws.account.ErrorWS;

@ControllerAdvice
public class ApiErrorsExceptionHandler extends ResponseEntityExceptionHandler {

	public List<ErrorWS> generateErrorWSList(BindingResult errors) {
		return errors.getFieldErrors()
				.stream()
				.map(error -> new ErrorWS(
						error.getCode(),
						error.getDefaultMessage()
				))
				.collect(toList());
	}
}
