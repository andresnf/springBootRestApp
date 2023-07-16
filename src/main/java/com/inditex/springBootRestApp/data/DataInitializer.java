package com.inditex.springBootRestApp.data;

import com.inditex.springBootRestApp.model.Price;
import com.inditex.springBootRestApp.repository.PriceRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer {

    private final PriceRepository priceRepository;

    public DataInitializer(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @PostConstruct
    // Executed after the application has started
    public void initializeData() {
        // Create a list of Price objects to be saved in the database
        List<Price> prices = Arrays.asList(
                new Price(1L, LocalDateTime.parse("2020-06-14T00:00:00"),
                        LocalDateTime.parse("2020-12-31T23:59:59"), 1, 35455L, 0, 35.50, "EUR"),
                new Price(1L, LocalDateTime.parse("2020-06-14T15:00:00"),
                        LocalDateTime.parse("2020-06-14T18:30:00"), 2, 35455L, 1, 25.45, "EUR"),
                new Price(1L, LocalDateTime.parse("2020-06-15T00:00:00"),
                        LocalDateTime.parse("2020-06-15T11:00:00"), 3, 35455L, 1, 30.50, "EUR"),
                new Price(1L, LocalDateTime.parse("2020-06-15T16:00:00"),
                        LocalDateTime.parse("2020-12-31T23:59:59"), 4, 35455L, 1, 38.95, "EUR")
        );

        // Save all Price objects in bulk
        priceRepository.saveAll(prices);
    }
}
