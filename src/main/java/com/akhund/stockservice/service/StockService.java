package com.akhund.stockservice.service;

import com.akhund.stockservice.dto.*;

public interface StockService {

    StocksDto getStocksByTicker(String ticker);
    StocksDto getStocksByTickers(TickersDto tickers);
    StockPrice getStockPriceByFigi(String figi);
    StocksPricesDto getStocksPricesByFigies(FigiesDto figies);
}
