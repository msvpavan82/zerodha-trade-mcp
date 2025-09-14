package org.example.zerodhatrade.controller;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Order;
import jakarta.annotation.PostConstruct;
import org.example.zerodhatrade.model.Position;
import org.example.zerodhatrade.model.Trade;
import org.example.zerodhatrade.model.TradeDetails;
import org.example.zerodhatrade.service.KiteTradingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/zerodha-trade")
public class KiteTradingController {
    private final KiteTradingService kiteTradingService;

    public KiteTradingController(KiteTradingService kiteTradingService) {
        this.kiteTradingService = kiteTradingService;
    }

    @PostMapping("/place-order")
    public ResponseEntity<Trade> placeOrder(@RequestBody TradeDetails tradeDetails) throws IOException, KiteException {
        Order order = kiteTradingService.placeOrder(tradeDetails.getQuantity(),
                    tradeDetails.getTradingSymbol(), tradeDetails.getTransactionType());
        return order != null ? ResponseEntity.ok(new Trade(order.orderId))
                : ResponseEntity.ok(new Trade("-1"));
    }

    @GetMapping("/get-holdings")
    public ResponseEntity<List<Position>> getAllHoldings() throws IOException, KiteException {
        List<Position> positionList = kiteTradingService.getHoldings();
        return !positionList.isEmpty() ? ResponseEntity.ok(positionList)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(positionList);
    }
}
