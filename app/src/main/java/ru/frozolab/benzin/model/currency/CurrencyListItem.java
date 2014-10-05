package ru.frozolab.benzin.model.currency;

import org.joda.money.Money;

import java.util.Comparator;
import java.util.List;

public class CurrencyListItem {

    public static class Comparators {

        public static Comparator<CurrencyListItem> PRICE = new Comparator<CurrencyListItem>() {
            @Override
            public int compare(CurrencyListItem o1, CurrencyListItem o2) {
                return o1.getPriceSale().getAmount().compareTo(o2.getPriceSale().getAmount());
            }
        };
    }

    private CurrencyType currencyType;
    private List<CurrencyCompany> companies;
    private Money priceBuy;
    private Money priceSale;

    public CurrencyListItem(CurrencyType currencyType, List<CurrencyCompany> companies, Money priceBuy, Money priceSale) {
        this.currencyType = currencyType;
        this.companies = companies;
        this.priceBuy = priceBuy;
        this.priceSale = priceSale;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public List<CurrencyCompany> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CurrencyCompany> companies) {
        this.companies = companies;
    }

    public Money getPriceBuy() {
        return priceBuy;
    }

    public void setPriceBuy(Money priceBuy) {
        this.priceBuy = priceBuy;
    }

    public Money getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(Money priceSale) {
        this.priceSale = priceSale;
    }

    public void addCompany(CurrencyCompany currencyCompany) {
        this.companies.add(currencyCompany);
    }
}
