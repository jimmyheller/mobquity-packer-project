package com.mobiquityinc.exception;

/**
 * Created by Jimmy Heller on 9/27/2018.
 */
public class APIException extends Exception {
    public APIException(){
        super();
    }

    public APIException(String message){
        super(message);
    }
}
