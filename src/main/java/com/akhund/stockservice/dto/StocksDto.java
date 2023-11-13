package com.akhund.stockservice.dto;

import com.akhund.stockservice.model.Stock;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StocksDto {
    private List<Stock> stocks;
}
