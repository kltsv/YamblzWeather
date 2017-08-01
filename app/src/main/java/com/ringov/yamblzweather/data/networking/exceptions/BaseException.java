package com.ringov.yamblzweather.data.networking.exceptions;

abstract class BaseException extends RuntimeException {

    BaseException(String message) {
        super(message);
    }
}
