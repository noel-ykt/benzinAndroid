package com.tabalab.benzinyakutsk.model;

public class Price {
    private Company company;
    private Type type;
    private String price;

    public Price(Company company, Type type, String price) {
        this.company = company;
        this.type = type;
        this.price = price;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
