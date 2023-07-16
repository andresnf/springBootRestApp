package com.inditex.springBootRestApp.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PRICES")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Price ID")
    private Long id;

    @Column(name = "BRAND_ID")
    @Schema(description = "Brand ID")
    private Long brandId;

    @Column(name = "START_DATE")
    @Schema(description = "Start date")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    @Schema(description = "End date")
    private LocalDateTime endDate;

    @Column(name = "PRICE_LIST")
    @Schema(description = "Price list")
    private Integer priceList;

    @Column(name = "PRODUCT_ID")
    @Schema(description = "Product ID")
    private Long productId;

    @Column(name = "PRIORITY")
    @Schema(description = "Priority")
    private Integer priority;

    @Column(name = "PRICE")
    @Schema(description = "Price")
    private Double price;

    @Column(name = "CURR")
    @Schema(description = "Currency")
    private String currency;

    public Price() {
    }

    public Price(Long brandId, LocalDateTime startDate, LocalDateTime endDate, Integer priceList, Long productId, Integer priority, Double price, String currency) {
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceList = priceList;
        this.productId = productId;
        this.priority = priority;
        this.price = price;
        this.currency = currency;
    }

    public Long getBrandId() {
        return brandId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Integer getPriceList() {
        return priceList;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getPriority() {
        return priority;
    }

    public Double getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }
}
