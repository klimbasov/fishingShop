package com.jwd.fShop.domain;

import java.util.Objects;

public class Product {
    private final String name;
    private final Integer quantity;
    private final Float price;
    private final Integer productType;
    private final Boolean visible;

    Product(Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.quantity = builder.quantity;
        this.productType = builder.productType;
        this.visible = builder.visible;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getProductType() {
        return productType;
    }

    public Boolean getVisible() {
        return visible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) ||
                Objects.equals(quantity, product.quantity) ||
                Objects.equals(productType, product.productType) ||
                Objects.equals(visible, product.visible);
    }

    @Override
    public int hashCode() {//todo refactor
        return Objects.hash(name, quantity, price, productType, visible);
    }

    public static class Builder {
        private String name;
        private Integer quantity;
        private Float price;
        private Integer productType;
        private Boolean visible;


        public Builder() {
            this.name = null;
            this.quantity = null;
            this.price = null;
            this.productType = null;
        }

        public Builder(Product product) {
            this.name = product.name;
            this.quantity = product.quantity;
            this.price = product.price;
            this.productType = product.productType;
            this.visible = product.visible;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setPrice(float price) {
            this.price = price;
            return this;
        }

        public Builder setProductType(Integer productType) {
            this.productType = productType;
            return this;
        }

        public Builder setVisible(Boolean visible) {
            this.visible = visible;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
