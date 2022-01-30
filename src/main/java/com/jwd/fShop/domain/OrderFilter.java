package com.jwd.fShop.domain;

import java.sql.Date;
import java.sql.Time;

public class OrderFilter {
    private final Date orderingDate;
    private final Time orderingTime;

    public OrderFilter(final Date orderingDate, final Time orderingTime) {
        this.orderingDate = orderingDate;
        this.orderingTime = orderingTime;
    }


    public Date getOrderingDate() {
        return orderingDate;
    }

    public Time getOrderingTime() {
        return orderingTime;
    }
}
