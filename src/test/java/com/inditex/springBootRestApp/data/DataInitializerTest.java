package com.inditex.springBootRestApp.data;

import com.inditex.springBootRestApp.model.Price;
import com.inditex.springBootRestApp.repository.PriceRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class DataInitializerTest {

    @InjectMocks
    private DataInitializer dataInitializer;

    @Mock
    private PriceRepository priceRepository;

    @Captor
    private ArgumentCaptor<List<Price>> priceListCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInitializeData() {
        // Arrange
        List<Price> expectedPrices = Arrays.asList(
                new Price(1L, LocalDateTime.parse("2020-06-14T00:00:00"),
                        LocalDateTime.parse("2020-12-31T23:59:59"), 1, 35455L, 0, 35.50, "EUR"),
                new Price(1L, LocalDateTime.parse("2020-06-14T15:00:00"),
                        LocalDateTime.parse("2020-06-14T18:30:00"), 2, 35455L, 1, 25.45, "EUR"),
                new Price(1L, LocalDateTime.parse("2020-06-15T00:00:00"),
                        LocalDateTime.parse("2020-06-15T11:00:00"), 3, 35455L, 1, 30.50, "EUR"),
                new Price(1L, LocalDateTime.parse("2020-06-15T16:00:00"),
                        LocalDateTime.parse("2020-12-31T23:59:59"), 4, 35455L, 1, 38.95, "EUR")
        );

        // Act
        dataInitializer.initializeData();

        // Assert
        // Capture the argument passed to saveAll() using priceListCaptor
        verify(priceRepository).saveAll(priceListCaptor.capture());

        // Get the captured argument
        List<Price> capturedPrices = priceListCaptor.getValue();
        assertEquals(expectedPrices.size(), capturedPrices.size());

        for (int i = 0; i < expectedPrices.size(); i++) {
            Price expectedPrice = expectedPrices.get(i);
            Price capturedPrice = capturedPrices.get(i);

            // Compare the attributes of expectedPrice and capturedPrice
            assertEquals(expectedPrice.getBrandId(), capturedPrice.getBrandId());
            assertEquals(expectedPrice.getStartDate(), capturedPrice.getStartDate());
            assertEquals(expectedPrice.getEndDate(), capturedPrice.getEndDate());
        }
    }
}

