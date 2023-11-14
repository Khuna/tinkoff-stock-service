package com.akhund.stockservice.service;

import com.akhund.stockservice.dto.*;
import com.akhund.stockservice.exception.StockNotFoundException;
import com.akhund.stockservice.model.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.InstrumentShort;
import ru.tinkoff.piapi.core.InvestApi;
import static ru.tinkoff.piapi.core.utils.MapperUtils.quotationToBigDecimal;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TinkoffStockService implements StockService {

    private final InvestApi investApi;

    @Async
    public CompletableFuture<List<InstrumentShort>> getMarketInstrumentByTicker(String ticker) {

        var instrumentsService = investApi.getInstrumentsService();
        return instrumentsService.findInstrument(ticker);
    }

    @Override
    public StocksDto getStocksByTicker(String ticker) {

        var instrumentsService = investApi.getInstrumentsService();
        var cf = instrumentsService.findInstrument(ticker);
        var list = cf.join();

        if (list.isEmpty()) {
            throw new StockNotFoundException(String.format("Stock %S not found.", ticker));
        }

        List<Stock> stocks= new ArrayList<>();

        list.forEach(item -> stocks.add(new Stock(
                item.getTicker(),
                item.getFigi(),
                item.getName(),
                item.getInstrumentType(),
                "TINKOFF"
        )));

        return new StocksDto(stocks);
    }

    public StocksDto getStocksByTickers(TickersDto tickers) {

        var instrumentsService = investApi.getInstrumentsService();

        List<CompletableFuture<List<InstrumentShort>>>  instrumentShorts = new ArrayList<>();
        tickers.getTickers()
                .forEach(ticker -> instrumentShorts.add(instrumentsService.findInstrument(ticker)));
        List<Stock> stocks = instrumentShorts.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .map(item -> new Stock(
                        item.getTicker(),
                        item.getFigi(),
                        item.getName(),
                        item.getInstrumentType(),
                        "TINKOFF"
                ))
                .toList();

        return new StocksDto(stocks);
    }

    public StockPrice getStockPriceByFigi(String figi) {

        var cf = investApi.getMarketDataService().getLastPrices(List.of(figi));
        var list = cf.join();

        if (list.isEmpty()) {
            throw new StockNotFoundException(String.format("Figi %S not found.", figi));
        }
        else {
            return new StockPrice(figi, quotationToBigDecimal(list.get(0).getPrice()));
        }


    }

    public StocksPricesDto getStocksPricesByFigies(FigiesDto figies) {

//        List<CompletableFuture<Optional<Orderbook>>> orderBooks = new ArrayList<>();
//
//        figies.getFigies()
//                .forEach(figi -> orderBooks.add(getOrderBookByFigi(figi)));
//
//        List<StockPrice> stockPrices = orderBooks.stream()
//                .map(CompletableFuture::join)
//                .map(orderbook -> orderbook.orElseThrow(() ->
//                        new StockNotFoundException("Stock not foumd")
//                ))
//                .map(orderbook -> new StockPrice(
//                        orderbook.getFigi(),
//                        orderbook.getLastPrice().doubleValue()
//                ))
//                .toList();
//
//        return new StocksPricesDto(stockPrices);

        return null;
    }
}
