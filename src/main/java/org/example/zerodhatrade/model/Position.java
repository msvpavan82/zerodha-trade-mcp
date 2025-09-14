package org.example.zerodhatrade.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    private int quantity;
    private String tradingSymbol;
    private double lastPrice;
}
