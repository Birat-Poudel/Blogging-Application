package com.biratpoudel.blog.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message)
    {
        super(message);
    }

}
