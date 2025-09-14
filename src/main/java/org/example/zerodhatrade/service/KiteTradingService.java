package org.example.zerodhatrade.service;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.kiteconnect.utils.Constants;
import com.zerodhatech.models.Order;
import com.zerodhatech.models.OrderParams;
import com.zerodhatech.models.User;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KiteTradingService {
    @Value("${kite.clientId}")
    private String clientId;

    @Value("${kite.apiKey}")
    private String apiKey;

    @Value("${kite.api.secret}")
    private String apiSecret;

    @Value("${kite.access.token}")
    private String accessToken;

    @Value("${kite.request.token}")
    private String requestToken;

    private static final Logger logger = LoggerFactory.getLogger(KiteTradingService.class);

    @PostConstruct
    public KiteConnect initializeKiteConnect(){
        KiteConnect kiteSdk = new KiteConnect(apiKey);
        kiteSdk.setUserId(clientId);
        kiteSdk.setAccessToken(accessToken);
        try {
            String userName = kiteSdk.getProfile().userName;
            logger.warn(userName);
        } catch(IOException | KiteException e) {
            logger.warn("Access Token Expired!");
            generateSession(kiteSdk);
        }
        return kiteSdk;
    }

    @Tool(name = "place-order", description = "place order on zerodha")
    public Order placeOrder(int quantity, String tradingSymbol, String transactionType) {
        OrderParams orderParams = new OrderParams();
        orderParams.quantity = quantity;
        orderParams.orderType = Constants.ORDER_TYPE_MARKET;
        orderParams.tradingsymbol = tradingSymbol;
        orderParams.product = Constants.PRODUCT_CNC;
        orderParams.exchange = Constants.EXCHANGE_BSE;
        orderParams.transactionType = (transactionType.equals("BUY") ? Constants.TRANSACTION_TYPE_BUY : Constants.TRANSACTION_TYPE_SELL);

        KiteConnect kiteSdk = initializeKiteConnect();
        try {
            return kiteSdk.placeOrder(orderParams, Constants.VARIETY_AMO);
        } catch (IOException | KiteException e) {
            logger.error("Error encountered while placing the order...");
            return null;
        }
    }

    public void generateSession(KiteConnect kiteSdk) {
        try {
            logger.info(kiteSdk.getLoginURL());
            User user = kiteSdk.generateSession(requestToken, apiSecret);
            logger.info("Access Token: {}", user.accessToken);
        } catch(IOException | KiteException e) {
            logger.warn("Check for the Proper Request Token");
        }
    }
}
