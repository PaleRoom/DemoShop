package ru.ncs.DemoShop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ncs.DemoShop.exception.customerException.CustomerErrorResponse;
import ru.ncs.DemoShop.exception.customerException.CustomerNotCreatedException;
import ru.ncs.DemoShop.exception.customerException.CustomerNotFoundException;
import ru.ncs.DemoShop.exception.customerException.CustomerNotUpdatedException;
import ru.ncs.DemoShop.exception.productException.ProductErrorResponse;
import ru.ncs.DemoShop.exception.productException.ProductNotCreatedException;
import ru.ncs.DemoShop.exception.productException.ProductNotFoundException;
import ru.ncs.DemoShop.exception.productException.ProductNotUpdatedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<CustomerErrorResponse> handleException(CustomerNotCreatedException e) {
        CustomerErrorResponse response = new CustomerErrorResponse(
                e.getMessage(),
                e.getClass().getSimpleName(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 401
    }

    @ExceptionHandler
    private ResponseEntity<CustomerErrorResponse> handleException(CustomerNotFoundException e) {
        CustomerErrorResponse response = new CustomerErrorResponse(
                "Customer with this ID not found!",
                e.getClass().getSimpleName(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404
    }

    @ExceptionHandler
    private ResponseEntity<CustomerErrorResponse> handleException(CustomerNotUpdatedException e) {
        CustomerErrorResponse response = new CustomerErrorResponse(
                e.getMessage(),
                e.getClass().getSimpleName(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 401
    }

//    @ExceptionHandler
//    private ResponseEntity<CustomerErrorResponse> handleException(Exception e) {
//        CustomerErrorResponse response = new CustomerErrorResponse(
//                e.getMessage(),
//                e.getClass().getSimpleName(),
//                System.currentTimeMillis()
//        );
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 401
//    }

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

