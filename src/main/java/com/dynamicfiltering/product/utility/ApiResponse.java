package com.dynamicfiltering.product.utility;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private String endpoint;
    private String message;
    private Integer errorCode;
    private T data;
    private String timestamp;

    public ApiResponse() {
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    public ApiResponse(String endpoint, String message, Integer errorCode, T data) {
        this();
        this.endpoint = endpoint;
        this.message = message;
        this.errorCode = errorCode;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(String endpoint, T data) {
        return new ApiResponse<>(endpoint, "Request was successful", null, data);
    }

    public static <T> ApiResponse<T> error(String endpoint, String message, Integer errorCode) {
        return new ApiResponse<>(endpoint, message, errorCode, null);
    }

    // Getters and setters
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
