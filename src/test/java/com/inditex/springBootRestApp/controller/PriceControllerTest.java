package com.inditex.springBootRestApp.controller;

import com.inditex.springBootRestApp.exception.CustomExceptions;
import com.inditex.springBootRestApp.model.Price;
import com.inditex.springBootRestApp.service.PriceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceControllerTest {

    @Mock
    private PriceService priceService;

    @InjectMocks
    private PriceController priceController;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private LocalDateTime applicationDate;
    private Long productId;
    private Long chainId;

    @Before
    public void setup() {
        applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        productId = 35455L;
        chainId = 1L;
    }

    @Test
    public void testGetPrice_PriceNotFound() {
        // Arrange
        when(priceService.getValidPrice(applicationDate, productId, chainId))
                .thenThrow(new CustomExceptions.PriceNotFoundException("Price not found"));

        // Act
        try {
            priceController.getPrice(applicationDate, productId, chainId);
        } catch (ResponseStatusException e) {
            // Assert
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
            assertEquals("Price not found", e.getReason());
            return;
        }

        // Fail if no exception is thrown
        throw new AssertionError("Expected ResponseStatusException to be thrown, but nothing was thrown.");
    }

    @Test
    public void testGetPrice1() {
        String url = buildUrl();

        ResponseEntity<Price> response = restTemplate.getForEntity(url, Price.class);

        assertPriceResponse(response, 1, 35.50);
    }

    @Test
    public void testGetPrice2() {
        applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

        String url = buildUrl();

        ResponseEntity<Price> response = restTemplate.getForEntity(url, Price.class);

        assertPriceResponse(response, 2, 25.45);
    }

    @Test
    public void testGetPrice3() {
        applicationDate = LocalDateTime.of(2020, 6, 14, 21, 0);

        String url = buildUrl();

        ResponseEntity<Price> response = restTemplate.getForEntity(url, Price.class);

        assertPriceResponse(response, 1, 35.50);
    }

    @Test
    public void testGetPrice4() {
        applicationDate = LocalDateTime.of(2020, 6, 15, 10, 0);

        String url = buildUrl();

        ResponseEntity<Price> response = restTemplate.getForEntity(url, Price.class);

        assertPriceResponse(response, 3, 30.50);
    }

    @Test
    public void testGetPrice5() {
        applicationDate = LocalDateTime.of(2020, 6, 16, 21, 0);

        String url = buildUrl();

        ResponseEntity<Price> response = restTemplate.getForEntity(url, Price.class);

        assertPriceResponse(response, 4, 38.95);
    }

    private String buildUrl() {
        return "http://localhost:" + port + "/api/prices?applicationDate=" + applicationDate +
                "&productId=" + productId + "&chainId=" + chainId;
    }

    private void assertPriceResponse(ResponseEntity<Price> response, Integer priceList, double price) {
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Price priceResponse = response.getBody();
        assertNotNull(priceResponse);
        assertEquals(productId, priceResponse.getProductId());
        assertEquals(chainId, priceResponse.getBrandId());
        assertEquals(priceList, priceResponse.getPriceList());
        assertEquals(price, priceResponse.getPrice(), 0.001);
        assertEquals("EUR", priceResponse.getCurrency());
    }
}
