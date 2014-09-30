package ru.frozolab.benzin.model;

import org.joda.money.Money;

import java.util.List;

public class ListItem {
    private Type type;
    private List<Company> companies;
    private Money price;

    public ListItem(Type type, List<Company> companies, Money price) {
        this.type = type;
        this.companies = companies;
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public void addCompny(Company company) {
        this.companies.add(company);
    }
}
