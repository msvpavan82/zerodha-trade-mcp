package org.example.zerodhatrade;

import org.example.zerodhatrade.service.KiteTradingService;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.List;

@SpringBootApplication
public class ZerodhaTradeApplication {

    public static void main(String[] args){
        SpringApplication.run(ZerodhaTradeApplication.class, args);
    }

    @Bean
    public List<ToolCallback> zerodhaTools(KiteTradingService kiteTradingService) {
        return List.of(ToolCallbacks.from(kiteTradingService));
    }

}
