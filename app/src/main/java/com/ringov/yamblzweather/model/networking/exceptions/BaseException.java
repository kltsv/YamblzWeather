package com.ringov.yamblzweather.model.networking.exceptions;

abstract class BaseException extends RuntimeException {

    BaseException(String message) {
        super(message);
    }
}
