package com.akhund.stockservice.model;

public enum Currency {

    RUB("RUB"),
    USD("USD"),
    EUR("EUR"),
    GBP("GBP"),
    HKD("HKD"),
    CHF("CHF"),
    JPY("JPY"),
    CNY("CNY"),
    TRY("TRY");

    private String curruncy;

    Currency(String curruncy) {
        this.curruncy = curruncy;
    }
}
