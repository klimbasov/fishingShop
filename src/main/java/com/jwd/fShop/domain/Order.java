package com.jwd.fShop.domain;

import java.sql.Date;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Order {
    private static final List<ProductBunch> DEFAULT_PRODUCT_BUNCH_LIST = null;
    private static final Integer DEFAULT_USER_ID = null;
    private static final Date DEFAULT_ORDERING_DATE = null;
    private static final Time DEFAULT_ORDERING_TIME = null;

    private final List<ProductBunch> productBunchList;
    private final Integer userId;
    private final Date orderingDate;
    private final Time orderingTime;

    public Order(final List<ProductBunch> quantityBunchList, final int userId, final Date orderingDate, final Time orderingTime) {
        this.productBunchList = quantityBunchList;
        this.userId = userId;
        this.orderingDate = orderingDate;
        this.orderingTime = orderingTime;
    }

    Order(final Builder builder) {
        this.userId = builder.userId;
        this.productBunchList = builder.productBunchList;
        this.orderingDate = builder.orderingDate;
        this.orderingTime = builder.orderingTime;
    }

    public int getUserId(){
        return userId;
    }

    public Date getOrderingDate() {
        return orderingDate;
    }

    public Time getOrderingTime() {
        return orderingTime;
    }

    public List<ProductBunch> getProductBunchList() {
        return new LinkedList<>(productBunchList);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        Order order = (Order) obj;
        return Objects.equals(userId, order.userId) &&
                Objects.equals(orderingDate, order.orderingDate) &&
                Objects.equals(orderingTime, order.orderingTime) &&
                (Objects.isNull(productBunchList) ? Objects.isNull(order.productBunchList) : productBunchList.equals(order.productBunchList));
    }


    public static class Builder {
        private List<ProductBunch> productBunchList;
        private Integer userId;
        private Date orderingDate;
        private Time orderingTime;

        public Builder() {
            productBunchList = DEFAULT_PRODUCT_BUNCH_LIST;
            userId = DEFAULT_USER_ID;
            orderingDate = DEFAULT_ORDERING_DATE;
            orderingTime = DEFAULT_ORDERING_TIME;
        }

        public Builder setUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Builder setOrderingDate(Date orderingDate) {
            this.orderingDate = Date.valueOf(orderingDate.toString());
            return this;
        }

        public Builder setOrderingTime(Time orderingTime) {
            this.orderingTime = orderingTime;
            return this;
        }

        public Builder setProductBunchList(List<ProductBunch> productBunchList) {
            this.productBunchList = productBunchList;
            return this;
        }

        public Order build() {
            return new Order(this);

        }
    }
}
