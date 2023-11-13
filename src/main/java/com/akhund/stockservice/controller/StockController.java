package com.akhund.stockservice.controller;

import com.akhund.stockservice.dto.FigiesDto;
import com.akhund.stockservice.dto.StocksDto;
import com.akhund.stockservice.dto.StocksPricesDto;
import com.akhund.stockservice.dto.TickersDto;
import com.akhund.stockservice.model.Stock;
import com.akhund.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/stocks/{ticker}")
    public Stock getStock(@PathVariable String ticker) {
        return stockService.getStockByTicker(ticker);
    }

    @PostMapping("/stocks/getStocksByTickers")
    public StocksDto getStocksByTickers(@RequestBody TickersDto tickers) {
        return stockService.getStocksByTickers(tickers);
    }

    @PostMapping("/prices/getPrices")
    public StocksPricesDto getPrices(@RequestBody FigiesDto figies) {
        return stockService.getStocksPrices(figies);
    }
}
