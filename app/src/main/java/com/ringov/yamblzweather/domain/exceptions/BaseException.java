package com.ringov.yamblzweather.domain.exceptions;

abstract class BaseException extends RuntimeException {

    BaseException() {
        super();
    }

    BaseException(String message) {
        super(message);
    }
}
