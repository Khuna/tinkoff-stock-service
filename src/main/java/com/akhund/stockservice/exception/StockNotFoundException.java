package com.akhund.stockservice.exception;

public class StockNotFoundException extends RuntimeException {
    public StockNotFoundException(String message) {
        super(message);
    }
}
