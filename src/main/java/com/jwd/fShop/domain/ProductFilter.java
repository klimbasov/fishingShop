package com.jwd.fShop.domain;

import java.util.Objects;

public class ProductFilter {

    private static final String DEFAULT_NAME = null;
    private static final Integer DEFAULT_ID = null;
    private static final Float DEFAULT_LOW_PRICE = null;
    private static final Float DEFAULT_HIGH_PRICE = null;
    private static final Integer DEFAULT_PRODUCT_TYPE = null;
    private static final Boolean DEFAULT_VISIBILITY = null;

    private final String name;
    private final Integer id;
    private final Float lowPrice;
    private final Float highPrice;
    private final Integer productType;
    private final Boolean visibility;

    ProductFilter(final Builder builder) {
        this.name = builder.name;
        this.id = builder.id;
        this.lowPrice = builder.lowPrice;
        this.highPrice = builder.highPrice;
        this.productType = builder.productType;
        this.visibility = builder.visibility;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public Float getLowPrice() {
        return lowPrice;
    }

    public Float getHighPrice() {
        return highPrice;
    }

    public Integer getProductType() {
        return productType;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public boolean isName() {
        return !Objects.equals(name, DEFAULT_NAME);
    }

    public boolean isId() {
        return !Objects.equals(id, DEFAULT_ID);
    }

    public boolean isLowPrice() {
        return !Objects.equals(lowPrice, DEFAULT_LOW_PRICE);
    }

    public boolean isHighPrice() {
        return !Objects.equals(highPrice, DEFAULT_HIGH_PRICE);
    }

    public boolean isProductType() {
        return !Objects.equals(productType, DEFAULT_PRODUCT_TYPE);
    }

    public boolean isVisibility() {
        return !Objects.equals(visibility, DEFAULT_VISIBILITY);
    }

    public static class Builder {

        private String name;
        private Integer id;
        private Float lowPrice;
        private Float highPrice;
        private Integer productType;
        private Boolean visibility;

        {
            name = DEFAULT_NAME;
            id = DEFAULT_ID;
            lowPrice = DEFAULT_LOW_PRICE;
            highPrice = DEFAULT_HIGH_PRICE;
            productType = DEFAULT_PRODUCT_TYPE;
            visibility = DEFAULT_VISIBILITY;
        }

        public Builder() {

        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setPriceRange(Float lowPrice, Float highPrice) {
            this.lowPrice = lowPrice;
            this.highPrice = highPrice;
            return this;
        }

        public Builder setProductType(Integer productType) {
            this.productType = productType;
            return this;
        }

        public Builder setVisibility(Boolean visible) {
            visibility = visible;
            return this;
        }

        public ProductFilter build() {
            return new ProductFilter(this);
        }
    }
}
