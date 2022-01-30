package com.jwd.fShop.domain;

public class ProductBunch {
    private final int productId;
    private final int quantity;


    public ProductBunch(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        ProductBunch productBunch = (ProductBunch) obj;
        return productId == productBunch.productId && quantity == productBunch.quantity;
    }
}
