package ru.ncs.DemoShop.exception.customerException;

public class CustomerEmailAlreadyExistsException extends RuntimeException{
    public CustomerEmailAlreadyExistsException(String msg) {super(msg);}
}
