package com.tabalab.benzinyakutsk.model;

import java.util.ArrayList;

public class ListItem {
    private Type type;
    private ArrayList<Company> companies;
    private String price;

    public ListItem(Type type, ArrayList<Company> companies, String price) {
        this.type = type;
        this.companies = companies;
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public String getPrice() {
        return price;
    }
}
