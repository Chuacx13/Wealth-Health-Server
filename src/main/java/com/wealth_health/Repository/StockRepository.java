package com.wealth_health.Repository;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wealth_health.Model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByUsername(String username);

    List<Stock> findAllByUsername(String username);

    Optional<Stock> findByUsernameAndStockIndex(String username, String stockIndex);
}
