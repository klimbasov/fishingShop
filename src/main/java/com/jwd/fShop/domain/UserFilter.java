package com.jwd.fShop.domain;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

public class UserFilter {
    private static final int DEFAULT_ID = -1;
    private static final String DEFAULT_USER_SUBNAME = null;
    private static final String DEFAULT_HASH_PASS = null;
    private static final Date DEFAULT_LOW_DATE = null;
    private static final Date DEFAULT_HIGH_DATE = null;
    private static final Time DEFAULT_LOW_TIME = null;
    private static final Time DEFAULT_HIGH_TIME = null;
    private static final int DEFAULT_ROLE = -1;
    private static final boolean DEFAULT_FULL_NAME = false;

    private final int id;
    private final String userSubname;
    private final String subHashPass;
    private final Date lowDate;
    private final Date highDate;
    private final Time lowTime;
    private final Time highTime;
    private final int role;
    private final boolean fullName;

    UserFilter(final Builder builder) {
        this.id = builder.id;
        this.userSubname = builder.userSubname;
        this.subHashPass = builder.subHashPass;
        this.lowDate = builder.lowDate;
        this.highDate = builder.highDate;
        this.lowTime = builder.lowTime;
        this.highTime = builder.highTime;
        this.role = builder.role;
        this.fullName = builder.fullName;
    }

    public int getId() {
        return id;
    }

    public String getUserSubname() {
        return userSubname;
    }

    public String getSubHashPass() {
        return subHashPass;
    }

    public Date getLowDate() {
        return lowDate;
    }

    public Date getHighDate() {
        return highDate;
    }

    public Time getLowTime() {
        return lowTime;
    }

    public Time getHighTime() {
        return highTime;
    }

    public int getRole() {
        return role;
    }

    public boolean isId() {
        return id != DEFAULT_ID;
    }

    public boolean isUserSubname() {
        return !Objects.equals(userSubname, DEFAULT_USER_SUBNAME);
    }

    public boolean isSubHashPass() {
        return !Objects.equals(subHashPass, DEFAULT_HASH_PASS);
    }

    public boolean isLowDate() {
        return !Objects.equals(lowDate, DEFAULT_LOW_DATE);
    }

    public boolean isHighDate() {
        return !Objects.equals(highDate, DEFAULT_HIGH_DATE);
    }

    public boolean isLowTime() {
        return !Objects.equals(lowTime, DEFAULT_LOW_TIME);
    }

    public boolean isHighTime() {
        return !Objects.equals(highTime, DEFAULT_HIGH_TIME
        );
    }

    public boolean isRole() {
        return role != DEFAULT_ROLE;
    }

    public boolean isFullName() {
        return fullName;
    }

    public static class Builder {

        private int id;
        private String userSubname;
        private String subHashPass;
        private Date lowDate;
        private Date highDate;
        private Time lowTime;
        private Time highTime;
        private int role;
        private boolean fullName;

        public Builder() {
            id = DEFAULT_ID;
            userSubname = DEFAULT_USER_SUBNAME;
            subHashPass = DEFAULT_HASH_PASS;
            lowDate = DEFAULT_LOW_DATE;
            highDate = DEFAULT_HIGH_DATE;
            lowTime = DEFAULT_LOW_TIME;
            highTime = DEFAULT_HIGH_TIME;
            role = DEFAULT_ROLE;
        }

        public UserFilter build() {
            return new UserFilter(this);
        }

        public Builder setUserSubname(String userSubname) {
            this.userSubname = userSubname;
            return this;
        }

        public Builder setSubHashPass(String subHashPass) {
            this.subHashPass = subHashPass;
            return this;
        }

        public Builder setLowDate(Date lowDate) {
            this.lowDate = lowDate;
            return this;
        }

        public Builder setHighDate(Date highDate) {
            this.highDate = highDate;
            return this;
        }

        public Builder setLowTime(Time lowTime) {
            this.lowTime = lowTime;
            return this;
        }

        public Builder setHighTime(Time highTime) {
            this.highTime = highTime;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setRole(int role) {
            this.role = role;
            return this;
        }

        public Builder fullName(boolean fullName) {
            this.fullName = fullName;
            return this;
        }
    }
}
