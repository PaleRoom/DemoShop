package ru.ncs.DemoShop.exception.customerException;

public class CustomerNotFoundException extends RuntimeException{
    public  CustomerNotFoundException(String msg) {super(msg);}
    public  CustomerNotFoundException() {super();}
}
