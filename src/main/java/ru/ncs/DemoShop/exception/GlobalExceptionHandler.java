package ru.ncs.DemoShop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ncs.DemoShop.exception.customerException.CustomerNotCreatedException;
import ru.ncs.DemoShop.exception.customerException.CustomerNotFoundException;
import ru.ncs.DemoShop.exception.productException.ProductNotCreatedException;
import ru.ncs.DemoShop.exception.productException.ProductNotFoundException;
import ru.ncs.DemoShop.exception.productException.ProductNotUpdatedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<ErrorDetails> handleException(CustomerNotCreatedException e) {
        ErrorDetails response = new ErrorDetails(
                e.getMessage(),
                e.getClass().getSimpleName(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400
    }

    @ExceptionHandler
    private ResponseEntity<ErrorDetails> handleException(CustomerNotFoundException e) {
        ErrorDetails response = new ErrorDetails(
                "Customer with this ID not found!",
                e.getClass().getSimpleName(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404
    }

    @ExceptionHandler
    private ResponseEntity<ErrorDetails> handleException(ProductNotCreatedException e) {
        ErrorDetails response = new ErrorDetails(
                e.getMessage(),
                e.getClass().getSimpleName(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400
    }

    @ExceptionHandler
    private ResponseEntity<ErrorDetails> handleException(ProductNotFoundException e) {
        ErrorDetails response = new ErrorDetails(
                "Product with this ID not found!",
                e.getClass().getSimpleName(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404
    }

    @ExceptionHandler
    private ResponseEntity<ErrorDetails> handleException(ProductNotUpdatedException e) {
        ErrorDetails response = new ErrorDetails(
                e.getMessage(),
                e.getClass().getSimpleName(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400
    }

    @ExceptionHandler
    private ResponseEntity<ErrorDetails> handleException(Exception e) {
        ErrorDetails response = new ErrorDetails(
                e.getMessage(),
                e.getClass().getSimpleName(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400
    }
}

