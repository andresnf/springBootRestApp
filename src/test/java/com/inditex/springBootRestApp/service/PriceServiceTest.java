package com.inditex.springBootRestApp.service;

import com.inditex.springBootRestApp.exception.CustomExceptions;
import com.inditex.springBootRestApp.model.Price;
import com.inditex.springBootRestApp.repository.PriceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceService priceService;

    @Test
    public void testGetSelectedPrice_ReturnsPrice_Success() {
        // Arrange
        LocalDateTime applicationDate = LocalDateTime.now();
        Long productId = 35455L;
        Long brandId = 1L;
        List<Price> prices = Arrays.asList(
                new Price(1L, LocalDateTime.parse("2020-06-14T00:00:00"), LocalDateTime.parse("2020-12-31T23:59:59"), 1, 35455L, 0, 35.50, "EUR"),
                new Price(1L, LocalDateTime.parse("2020-06-14T15:00:00"), LocalDateTime.parse("2020-06-14T18:30:00"), 2, 35455L, 1, 25.45, "EUR"),
                new Price(1L, LocalDateTime.parse("2020-06-15T00:00:00"), LocalDateTime.parse("2020-06-15T11:00:00"), 3, 35455L, 1, 30.50, "EUR"),
                new Price(1L, LocalDateTime.parse("2020-06-15T16:00:00"), LocalDateTime.parse("2020-12-31T23:59:59"), 4, 35455L, 1, 38.95, "EUR")
        );

        when(priceRepository.findByProductIdAndBrandIdAndDateRange(productId, brandId, applicationDate)).thenReturn(prices);

        // Act
        Price selectedPrice = priceService.getValidPrice(applicationDate, productId, brandId);

        // Assert
        assertNotNull(selectedPrice);
        assertEquals((Integer) 2, selectedPrice.getPriceList());
        assertEquals(25.45, selectedPrice.getPrice(), 0.001);
        assertEquals("EUR", selectedPrice.getCurrency());
    }

    @Test(expected = CustomExceptions.PriceNotFoundException.class)
    public void testGetSelectedPrice_NoPriceFound_ThrowsPriceNotFoundException() {
        // Arrange
        LocalDateTime applicationDate = LocalDateTime.now();
        Long productId = 35455L;
        Long brandId = 1L;

        // Act
        priceService.getValidPrice(applicationDate, productId, brandId);

        // The PriceNotFoundException should be thrown
    }
}

