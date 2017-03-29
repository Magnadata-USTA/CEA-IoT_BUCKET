package co.edu.usta.telco.iot.exception;

/**
 */
public class BusinessException extends Throwable {
    public BusinessException(String message, Exception exception) {
        super(message, exception);
    }
}
