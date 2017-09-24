package com.caldevsupplychain.common.exception;

import com.caldevsupplychain.common.type.ErrorCode;
import com.caldevsupplychain.common.ws.account.ApiErrorsWS;
import com.caldevsupplychain.common.ws.account.ErrorWS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Slf4j
@ControllerAdvice
public class ApiControllerExceptionHandler extends ResponseEntityExceptionHandler {

    // Def: handle unsuccessful submit form and fields etc.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status,
            WebRequest request
    ) {

        BindingResult bindingResult = ex
                .getBindingResult();

        List<ErrorWS> errorWSList = bindingResult
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorWS (
                        error.getCode(),
                        error.getDefaultMessage()
                ))
                .collect(toList());

        return new ResponseEntity<>(new ApiErrorsWS(errorWSList), HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
