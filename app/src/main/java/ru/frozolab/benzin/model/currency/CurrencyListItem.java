package ru.frozolab.benzin.model.currency;

import java.util.Comparator;
import java.util.List;

import ru.frozolab.benzin.util.Money;

public class CurrencyListItem {

    public static class Comparators {

        public static Comparator<CurrencyListItem> SORT = new Comparator<CurrencyListItem>() {
            @Override
            public int compare(CurrencyListItem o1, CurrencyListItem o2) {
                return o1.getCurrencyType().getSort() - o2.getCurrencyType().getSort();
            }
        };
    }

    private CurrencyType currencyType;
    private List<CurrencyCompany> companies;
    private Money priceBuy;
    private Money priceSale;
    private boolean bestBuy;
    private boolean bestSale;

    public CurrencyListItem(CurrencyType currencyType,
                            List<CurrencyCompany> companies,
                            Money priceBuy,
                            Money priceSale,
                            boolean bestBuy,
                            boolean bestSale) {
        this.currencyType = currencyType;
        this.companies = companies;
        this.priceBuy = priceBuy;
        this.priceSale = priceSale;
        this.bestBuy = bestBuy;
        this.bestSale = bestSale;
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

    public boolean isBestBuy() {
        return bestBuy;
    }

    public void setBestBuy(boolean bestBuy) {
        this.bestBuy = bestBuy;
    }

    public boolean isBestSale() {
        return bestSale;
    }

    public void setBestSale(boolean bestSale) {
        this.bestSale = bestSale;
    }
}
