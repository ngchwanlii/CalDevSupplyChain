package com.caldevsupplychain.common.ws.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorsWS implements Serializable {

    private List<ErrorWS> errors;

    public ApiErrorsWS(List<ErrorWS> errors) {
        this.errors = errors;
    }


    /*
        @Description:   Be consistent, return an array of error even though it is just one errorCode
                        easier for front-end to use array.map((c) -> doSomething)) to handle all the errorCode
        @return:        List<ErrorWS>
    */
    public ApiErrorsWS(String errorCode, String message) {
        this.errors = Lists.newArrayList(new ErrorWS(errorCode, message));
    }

}
