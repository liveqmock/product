package com.cots.mail;

public class SmtpException extends Exception {

    public SmtpException() {
    }

    public SmtpException(String description) {
        super(description);
    }
}
