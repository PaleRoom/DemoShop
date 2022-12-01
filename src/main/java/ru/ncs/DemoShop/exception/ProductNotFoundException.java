package ru.ncs.DemoShop.exception;

public class ProductNotFoundException extends RuntimeException{

    public  ProductNotFoundException(String msg) {super(msg);}

    public  ProductNotFoundException() {super();}
}
