package com.akhund.stockservice.controller;

import com.akhund.stockservice.dto.*;
import com.akhund.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/stocks/{ticker}")
    public StocksDto getStock(@PathVariable String ticker) {
        return stockService.getStocksByTicker(ticker);
    }

    @PostMapping("/stocks/getStocksByTickers")
    public StocksDto getStocksByTickers(@RequestBody TickersDto tickers) {
        return stockService.getStocksByTickers(tickers);
    }

    @GetMapping("/prices/{figi}")
    public StockPrice getPrice(@PathVariable String figi) {
        return stockService.getStockPriceByFigi(figi);
    }
    @PostMapping("/prices/getPrices")
    public StocksPricesDto getPrices(@RequestBody FigiesDto figies) {
        return stockService.getStocksPricesByFigies(figies);
    }
}
