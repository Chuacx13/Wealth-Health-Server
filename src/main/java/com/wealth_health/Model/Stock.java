package com.wealth_health.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", initialValue = 1, allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String stockIndex;
    @Column(nullable = false)
    private double stockPrice;
    @Column(nullable = false)
    private int stockCount;

    public Stock(String username, String stockIndex, double stockPrice, int stockCount) {
        this.username = username;
        this.stockIndex = stockIndex;
        this.stockPrice = stockPrice;
        this.stockCount = stockCount;
    }

    public Stock() {
    }
}
