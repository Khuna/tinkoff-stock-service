package com.akhund.stockservice.service;

import com.akhund.stockservice.dto.*;
import com.akhund.stockservice.model.Stock;

public interface StockService {

    Stock getStockByTicker(String ticker);
    StocksDto getStocksByTickers(TickersDto tickers);
    StocksPricesDto getStocksPrices(FigiesDto figies);
}
