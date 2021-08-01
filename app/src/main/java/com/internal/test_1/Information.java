package com.internal.test_1;

public class Information {
    private String email;
    private String unit;

    public Information() {
    }

    public Information(String email, String unit) {
        this.email = email;
        this.unit = unit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
