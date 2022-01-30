package com.jwd.fShop.domain;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

public class User {
    private static final Integer DEFAULT_ID = null;
    private static final String DEFAULT_NAME = null;
    private static final String DEFAULT_HASHED_PASSWORD = null;
    private static final Date DEFAULT_DATE = null;
    private static final Time DEFAULT_TIME = null;
    private static final Integer DEFAULT_ROLE = null;

    private final Integer id;
    private final String name;
    private final String hashedPassword;
    private final Date registrationDate;
    private final Time registrationTime;
    private final Integer role;

    User(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.hashedPassword = builder.hashedPassword;
        this.registrationDate = builder.registrationDate;
        this.registrationTime = builder.registrationTime;
        this.role = builder.role;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public Time getRegistrationTime() {
        return registrationTime;
    }

    public Integer getRole() {
        return role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        User user = (User) obj;

        return Objects.equals(this.id, user.id)
                && Objects.equals(this.name, user.name)
                && Objects.equals(this.hashedPassword, user.hashedPassword)
                && Objects.equals(this.role, user.role)
                && Objects.equals(this.registrationDate, user.registrationDate)
                && Objects.equals(this.registrationTime, user.registrationTime);
    }

    public static class Builder {
        private Integer id;
        private String name;
        private String hashedPassword;
        private Date registrationDate;
        private Time registrationTime;
        private Integer role;

        public Builder(final User user) {
            id = user.id;
            name = user.name;
            hashedPassword = user.hashedPassword;
            registrationDate = user.registrationDate;
            registrationTime = user.registrationTime;
            role = user.role;
        }

        public Builder() {
            id = DEFAULT_ID;
            name = DEFAULT_NAME;
            hashedPassword = DEFAULT_HASHED_PASSWORD;
            registrationDate = DEFAULT_DATE;
            registrationTime = DEFAULT_TIME;
            role = DEFAULT_ROLE;
        }


        public User build() {
            return new User(this);
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setHashedPassword(String hashedPassword) {
            this.hashedPassword = hashedPassword;
            return this;
        }

        public Builder setRegistrationDate(Date registrationDate) {
            this.registrationDate = Date.valueOf(registrationDate.toString());
            return this;
        }

        public Builder setRegistrationTime(Time registrationTime) {
            this.registrationTime = Time.valueOf(registrationTime.toString());
            return this;
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setRole(int role) {
            this.role = role;
            return this;
        }
    }
}