package com.superrrr.franky.venta.exception;

public class IdempotencyKeyRequeridaException extends RuntimeException{
    public IdempotencyKeyRequeridaException(String message){ super(message);}
}
