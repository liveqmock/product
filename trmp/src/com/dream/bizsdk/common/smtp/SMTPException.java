package com.dream.bizsdk.common.smtp;

public class SMTPException extends Exception {

    public SMTPException() {
    }

    public SMTPException(String description) {
        super(description);
    }
}
