package ru.ncs.DemoShop.exception.orderException;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String msg) {super(msg);}
    public OrderNotFoundException() {super();}
}
