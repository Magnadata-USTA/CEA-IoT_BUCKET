package co.edu.usta.telco.iot.exception;

import javax.mail.MessagingException;

/**
 */
public class BusinessException extends Throwable {
    public BusinessException(String message, MessagingException exception) {
        super(message, exception);
    }
}
