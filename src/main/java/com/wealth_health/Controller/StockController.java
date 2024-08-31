package com.wealth_health.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.wealth_health.Model.Stock;
import com.wealth_health.Service.StockService;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @PutMapping
    public ResponseEntity<Stock> updateStockByUsernameAndIndex(@RequestBody Stock stock) {
        Stock updatedStock = stockService.updateStockByUsernameAndIndex(stock);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/username/{username}/stockIndex/{stockIndex}")
    public ResponseEntity<Void> deleteStockByUsernameAndIndex(@PathVariable String username,
            @PathVariable String stockIndex) {
        stockService.deleteStockByUsernameAndIndex(username, stockIndex);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<List<Stock>> getStocksByUsername(@PathVariable String username) {
        List<Stock> stocks = stockService.getStockByUsername(username);
        if (stocks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(stocks);
    }

}
