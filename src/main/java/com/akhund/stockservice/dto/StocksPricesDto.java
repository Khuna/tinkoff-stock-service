package com.akhund.stockservice.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@AllArgsConstructor
@Value
public class StocksPricesDto {
    private List<StockPrice> stockPrices;
}
