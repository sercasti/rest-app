package ar.com.bank.services.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiMessage {

    private HttpStatus status;
    private String messageCode;
    private String message;
    private List<String> errors;

    /**
     * @param status
     * @param errorCode
     * @param message
     * @param errors
     */
    public ApiMessage(HttpStatus status, String messageCode, String message, List<String> errors) {
        super();
        this.status = status;
        this.messageCode = messageCode;
        this.message = message;
        this.errors = errors;
    }

    /**
     * @param status
     * @param messageCode
     * @param message
     */
    public ApiMessage(HttpStatus status, String messageCode, String message) {
        super();
        this.status = status;
        this.messageCode = messageCode;
        this.message = message;
    }

    public void addError(String error) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(error);
    }

    /**
     * @return the status
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    /**
     * @return the messageCode
     */
    public String getMessageCode() {
        return messageCode;
    }

    /**
     * @param messageCode the messageCode to set
     */
    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the errors
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * @param errors the errors to set
     */
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

}
