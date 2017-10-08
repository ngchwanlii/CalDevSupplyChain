package com.caldevsupplychain.common.ws.account;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorsWS implements Serializable {

	private List<ErrorWS> errors;

	public ApiErrorsWS(List<ErrorWS> errors) {
		this.errors = errors;
	}

	public ApiErrorsWS(String errorCode, String message) {
		this.errors = Lists.newArrayList(new ErrorWS(errorCode, message));
	}
}
