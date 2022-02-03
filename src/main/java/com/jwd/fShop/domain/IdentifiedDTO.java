package com.jwd.fShop.domain;

public class IdentifiedDTO<T> {
    private final int id;
    private final T DTO;

    public IdentifiedDTO(int id, T dto) {
        this.id = id;
        this.DTO = dto;
    }

    public int getId() {
        return id;
    }

    public T getDTO() {
        return DTO;
    }
}
