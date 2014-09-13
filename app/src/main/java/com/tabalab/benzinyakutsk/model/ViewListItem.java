package com.tabalab.benzinyakutsk.model;

import java.util.ArrayList;

public class ViewListItem {
    private Type type;
    private Company company;
    private String price;

    public ViewListItem(Type type, Company company, String price) {
        this.type = type;
        this.company = company;
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public Company getCompany() {
        return company;
    }

    public String getPrice() {
        return price;
    }
}
