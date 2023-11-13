package com.akhund.stockservice.service;

import com.akhund.stockservice.dto.*;
import com.akhund.stockservice.exception.StockNotFoundException;
import com.akhund.stockservice.model.Currency;
import com.akhund.stockservice.model.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrumentList;
import ru.tinkoff.invest.openapi.model.rest.Orderbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TinkoffStockService implements StockService {

    private final OpenApi openApi;

    @Async
    public CompletableFuture<MarketInstrumentList> getMarketInstrumentByTicker(String ticker) {

        var context = openApi.getMarketContext();
        return context.searchMarketInstrumentsByTicker(ticker);
    }

    @Override
    public Stock getStockByTicker(String ticker) {

        var cf = getMarketInstrumentByTicker(ticker);
        var list = cf.join().getInstruments();

        if (list.isEmpty()) {
            throw new StockNotFoundException(String.format("Stock %S not found.", ticker));
        }

        var item = list.get(0);
        return new Stock(
                item.getTicker(),
                item.getFigi(),
                item.getName(),
                item.getType().getValue(),
                Currency.valueOf(item.getCurrency().getValue()),
                "TINKOFF"
        );
    }

    public StocksDto getStocksByTickers(TickersDto tickers) {

        List<CompletableFuture<MarketInstrumentList>> marketInstruments = new ArrayList<>();
        tickers.getTickers()
                .forEach(ticker -> marketInstruments.add(getMarketInstrumentByTicker(ticker)));
        List<Stock> stocks = marketInstruments.stream()
                .map(CompletableFuture::join)
                .map(mi -> {
                    if (mi.getInstruments().isEmpty()) {
                        return null;
                    }
                    else {
                        return mi.getInstruments().get(0);
                    }
                })
                .filter(Objects::nonNull)
                .map(mi -> new Stock(
                        mi.getTicker(),
                        mi.getFigi(),
                        mi.getName(),
                        mi.getType().getValue(),
                        Currency.valueOf(mi.getCurrency().getValue()),
                        "TINKOFF"
                ))
                .toList();

        return new StocksDto(stocks);
    }

    @Async
    public CompletableFuture<Optional<Orderbook>> getOrderBookByFigi(String figi) {
        return openApi.getMarketContext()
                .getMarketOrderbook(figi, 0);
    }

    public StocksPricesDto getStocksPrices(FigiesDto figies) {

        List<CompletableFuture<Optional<Orderbook>>> orderBooks = new ArrayList<>();

        figies.getFigies()
                .forEach(figi -> orderBooks.add(getOrderBookByFigi(figi)));

        List<StockPrice> stockPrices = orderBooks.stream()
                .map(CompletableFuture::join)
                .map(orderbook -> orderbook.orElseThrow(() ->
                        new StockNotFoundException("Stock not foumd")
                ))
                .map(orderbook -> new StockPrice(
                        orderbook.getFigi(),
                        orderbook.getLastPrice().doubleValue()
                ))
                .toList();

        return new StocksPricesDto(stockPrices);
    }
}
