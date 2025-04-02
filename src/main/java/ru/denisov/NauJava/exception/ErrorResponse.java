package ru.denisov.NauJava.exception;


public class ErrorResponse {
    private String message;

    private ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ErrorResponse create(Throwable e) {
        return new ErrorResponse(e.getMessage());
    }

    public static ErrorResponse create(String message) {
        return new ErrorResponse(message);
    }
}
