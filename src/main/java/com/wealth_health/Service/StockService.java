package com.wealth_health.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wealth_health.Model.Stock;
import com.wealth_health.Repository.StockRepository;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock updateStockByUsernameAndIndex(Stock stock) {

        Optional<Stock> existingStockOpt = stockRepository.findByUsernameAndStockIndex(stock.getUsername(),
                stock.getStockIndex());

        if (existingStockOpt.isPresent()) {
            Stock existingStock = existingStockOpt.get();

            // Update stock count and average price
            double currTotalPrice = existingStock.getStockPrice() * existingStock.getStockCount();
            double newAdditionalPrice = stock.getStockPrice() * stock.getStockCount();
            int newCount = stock.getStockCount() + existingStock.getStockCount();
            existingStock.setStockCount(newCount);
            existingStock.setStockPrice((currTotalPrice + newAdditionalPrice) / newCount);

            return stockRepository.save(existingStock);
        } else {
            return stockRepository.save(stock);
        }
    }

    public void deleteStockById(Long id) {
        stockRepository.deleteById(id);
    }

    public List<Stock> getStockByUsername(String username) {
        return stockRepository.findAllByUsername(username);
    }
}
