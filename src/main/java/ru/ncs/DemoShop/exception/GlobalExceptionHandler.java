package ru.ncs.DemoShop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<ProductErrorResponse> handleException(ProductNotCreatedException e) {
        ProductErrorResponse response = new ProductErrorResponse(
                e.getMessage(),
                e.getClass().getSimpleName(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 401
    }

    @ExceptionHandler
    private ResponseEntity<ProductErrorResponse> handleException(ProductNotFoundException e) {
        ProductErrorResponse response = new ProductErrorResponse(
                "Product with this ID not found!",
                e.getClass().getSimpleName(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404
    }

    @ExceptionHandler
    private ResponseEntity<ProductErrorResponse> handleException(ProductNotUpdatedException e) {
        ProductErrorResponse response = new ProductErrorResponse(
                e.getMessage(),
                e.getClass().getSimpleName(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 401
    }

    @ExceptionHandler
    private ResponseEntity<ProductErrorResponse> handleException(Exception e) {
        ProductErrorResponse response = new ProductErrorResponse(
                e.getMessage(),
                e.getClass().getSimpleName(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 401
    }
}

