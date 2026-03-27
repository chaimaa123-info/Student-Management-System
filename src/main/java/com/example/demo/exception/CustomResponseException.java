package com.example.demo.exception;

public class CustomResponseException extends RuntimeException {
    
    public CustomResponseException(String message) {
        super(message);
    }
    
    public CustomResponseException(String message, Throwable cause) {
        super(message, cause);
    }
    
    // Méthode statique pour créer une exception "Resource Not Found"
    public static CustomResponseException ResourceNotFound(String message) {
        return new CustomResponseException(message);
    }


    public static CustomResponseException BadCredentials() {
        return new CustomResponseException("Bad Credentials");
}

 public static CustomResponseException BadRequest(String message) {
        return new CustomResponseException(message);
}
}