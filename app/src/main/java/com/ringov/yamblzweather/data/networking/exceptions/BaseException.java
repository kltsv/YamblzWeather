package com.ringov.yamblzweather.data.networking.exceptions;

abstract class BaseException extends RuntimeException {

    BaseException() {
        super();
    }

    BaseException(String message) {
        super(message);
    }
}
