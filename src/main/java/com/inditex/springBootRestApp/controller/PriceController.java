package com.inditex.springBootRestApp.controller;

import com.inditex.springBootRestApp.exception.CustomExceptions;
import com.inditex.springBootRestApp.model.Price;
import com.inditex.springBootRestApp.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping(value="/prices")
    @Operation(summary = "Get Price", description = "Get the valid price for a given date, product ID, and chain ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price found",
                    content = @Content(schema = @Schema(implementation = Price.class))),
            @ApiResponse(responseCode = "404", description = "Price not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Price> getPrice(
            @Parameter(description = "Date and time", required = true, example = "2020-06-14T10:00:00")
            @RequestParam("applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
            @Parameter(description = "Product ID", required = true, example = "35455")
            @RequestParam("productId") Long productId,
            @Parameter(description = "Chain ID", required = true, example = "1")
            @RequestParam("chainId") Long chainId) {

        try {
            // Get the valid price for the given parameters from the PriceService
            Price selectedPrice = priceService.getValidPrice(applicationDate, productId, chainId);
            return ResponseEntity.ok(selectedPrice);
        } catch (CustomExceptions.PriceNotFoundException e) {
            // If PriceNotFoundException is thrown, return a 404 Not Found status with the exception message
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
