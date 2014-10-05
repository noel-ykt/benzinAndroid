package ru.frozolab.benzin.model.fuel;

import org.joda.money.Money;

import java.util.Comparator;
import java.util.List;

public class FuelListItem {

    public static class Comparators {

        public static Comparator<FuelListItem> PRICE = new Comparator<FuelListItem>() {
            @Override
            public int compare(FuelListItem o1, FuelListItem o2) {
                return o1.getPrice().getAmount().compareTo(o2.getPrice().getAmount());
            }
        };
    }

    private FuelType fuelType;
    private List<FuelCompany> companies;
    private Money price;

    public FuelListItem(FuelType fuelType, List<FuelCompany> companies, Money price) {
        this.fuelType = fuelType;
        this.companies = companies;
        this.price = price;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public List<FuelCompany> getCompanies() {
        return companies;
    }

    public void setCompanies(List<FuelCompany> companies) {
        this.companies = companies;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public void addCompny(FuelCompany fuelCompany) {
        this.companies.add(fuelCompany);
    }
}
