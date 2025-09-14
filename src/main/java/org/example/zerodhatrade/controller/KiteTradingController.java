package org.example.zerodhatrade.controller;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Order;
import org.example.zerodhatrade.service.KiteTradingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/zerodha-trade")
public class KiteTradingController {
    private final KiteTradingService kiteTradingService;

    public KiteTradingController(KiteTradingService kiteTradingService) {
        this.kiteTradingService = kiteTradingService;
    }

    @GetMapping("/order")
    public ResponseEntity<?> placeOrder() throws IOException, KiteException {
        Order order = kiteTradingService.placeOrder(1, "TATASTEEL", "BUY");
        return order != null ? ResponseEntity.ok("Order Placed Successfully")
                : ResponseEntity.ok("Unable to place the order");
    }
}
