package com.inditex.springBootRestApp.service;

import com.inditex.springBootRestApp.exception.CustomExceptions;
import com.inditex.springBootRestApp.model.Price;
import com.inditex.springBootRestApp.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * Get the valid price for a given date, product ID, and chain ID.
     * If a valid price is found, it is returned. Otherwise, a PriceNotFoundException is thrown.
     *
     * @param applicationDate      The date and time.
     * @param productId The product ID.
     * @param chainId   The chain ID.
     * @return The valid price for the given parameters.
     * @throws CustomExceptions.PriceNotFoundException If no valid price is found.
     */
    public Price getValidPrice(LocalDateTime applicationDate, Long productId, Long chainId) {
        List<Price> prices = priceRepository.findByProductIdAndBrandIdAndDateRange(productId, chainId, applicationDate);
        prices.sort(Comparator.comparingInt(Price::getPriority).reversed());

        if (!prices.isEmpty()) {
            return prices.get(0);
        }

        throw new CustomExceptions.PriceNotFoundException("No valid price found for the given parameters");
    }
}
