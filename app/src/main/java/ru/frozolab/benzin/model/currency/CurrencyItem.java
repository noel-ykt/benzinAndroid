package ru.frozolab.benzin.model.currency;

import android.util.Log;

import com.google.common.collect.Lists;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import ru.frozolab.benzin.util.Cache;
import ru.frozolab.benzin.util.JSONParser;
import ru.frozolab.benzin.util.Money;

public class CurrencyItem {
    public static final String URL = "http://currency.api.frozolab.ru/v2";
    private static final String TAG_TYPE = "type";
    private static final String TAG_COMPANY = "bank";
    private static final String TAG_PRICE_BUY = "buy";
    private static final String TAG_PRICE_SALE = "sale";
    private static final String TAG_BEST_SALE = "bestSale";
    private static final String TAG_BEST_BUY = "bestBuy";

    private CurrencyType currencyType;
    private CurrencyCompany currencyCompany;
    private Money priceBuy;
    private Money priceSale;
    private boolean bestBuy;
    private boolean bestSale;

    public CurrencyItem(CurrencyType currencyType,
                        CurrencyCompany currencyCompany,
                        Money priceBuy,
                        Money priceSale,
                        boolean bestBuy,
                        boolean bestSale) {
        this.currencyType = currencyType;
        this.currencyCompany = currencyCompany;
        this.priceBuy = priceBuy;
        this.priceSale = priceSale;
        this.bestBuy = bestBuy;
        this.bestSale = bestSale;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public CurrencyCompany getCurrencyCompany() {
        return currencyCompany;
    }

    public Money getPriceBuy() {
        return priceBuy;
    }

    public Money getPriceSale() {
        return priceSale;
    }

    public boolean isBestBuy() {
        return bestBuy;
    }

    public boolean isBestSale() {
        return bestSale;
    }

    public static List<CurrencyListItem> getMain() {
        return Cache.getCurrencyMainItems();
    }

    public static List<CurrencyListItem> getView(int typeId) {
        return Cache.getCurrencyViewItems(typeId);
    }

    public static List<CurrencyItem> loadItems() {
        List<CurrencyItem> itemsResult = Lists.newArrayList();

        JSONArray jsonArray = JSONParser.getJSONFromUrl(URL);

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Type
                JSONObject jsonType = jsonObject.getJSONObject(TAG_TYPE);
                CurrencyType currencyType = CurrencyType.initFromJSON(jsonType);
                //Company
                JSONObject jsonCompany = jsonObject.getJSONObject(TAG_COMPANY);
                CurrencyCompany currencyCompany = CurrencyCompany.initFromJSON(jsonCompany);
                //Price
                Money priceBuy = Money.parse(currencyType.getName(), jsonObject.getString(TAG_PRICE_BUY));
                Money priceSale = Money.parse(currencyType.getName(), jsonObject.getString(TAG_PRICE_SALE));

                boolean bestSale = jsonObject.getBoolean(TAG_BEST_SALE);
                boolean bestBuy = jsonObject.getBoolean(TAG_BEST_BUY);

                CurrencyItem currencyItem = new CurrencyItem(
                        currencyType,
                        currencyCompany,
                        priceBuy,
                        priceSale,
                        bestBuy,
                        bestSale);
                itemsResult.add(currencyItem);
            } catch (Exception ex) {
                Log.e("Parse Json Object", ex.getLocalizedMessage());
            }
        }
        return itemsResult;
    }

    public static List<CurrencyListItem> getPreparedViewItems(int typeId) {
        List<CurrencyListItem> result = Lists.newArrayList();
        for (CurrencyItem currencyItem : Cache.getCurrencyItems()) {
            if (currencyItem.getCurrencyType().getId() == typeId) {
                upsertViewItem(result, currencyItem);
            }
        }
        return result;
    }

    public static List<CurrencyListItem> getPreparedMainItems() {
        List<CurrencyListItem> result = Lists.newArrayList();
        for (CurrencyItem currencyItem : Cache.getCurrencyItems()) {
            upsertMainItem(result, currencyItem);
        }
        return result;
    }

    private static void upsertViewItem(List<CurrencyListItem> array, CurrencyItem currencyItem) {
        List<CurrencyCompany> companies = Lists.newArrayList();
        companies.add(currencyItem.getCurrencyCompany());
        CurrencyListItem currencyListItem = new CurrencyListItem(
                currencyItem.getCurrencyType(),
                companies,
                currencyItem.getPriceBuy(),
                currencyItem.getPriceSale(),
                currencyItem.isBestBuy(),
                currencyItem.isBestSale());
        array.add(currencyListItem);
    }

    private static void upsertMainItem(List<CurrencyListItem> array, CurrencyItem currencyItem) {
        boolean isExist = false;
        for (CurrencyListItem currencyListItem : array) {
            if (currencyListItem.getCurrencyType().getId() == currencyItem.getCurrencyType().getId()) {
                isExist = true;
                if (currencyListItem.getPriceSale().isGreaterThan(currencyItem.getPriceSale())) {
                    List<CurrencyCompany> newCompanies = Lists.newArrayList();
                    newCompanies.add(currencyItem.getCurrencyCompany());
                    currencyListItem.setCompanies(newCompanies);
                    currencyListItem.setPriceSale(currencyItem.getPriceSale());
                    currencyListItem.setPriceBuy(currencyItem.getPriceBuy());
                } else if (currencyListItem.getPriceSale().isEqual(currencyItem.getPriceSale())) {
                    currencyListItem.addCompany(currencyItem.getCurrencyCompany());
                }
            }
        }

        if (!isExist) {
            List<CurrencyCompany> companies = Lists.newArrayList();
            companies.add(currencyItem.getCurrencyCompany());
            CurrencyListItem currencyListItem = new CurrencyListItem(
                    currencyItem.getCurrencyType(),
                    companies,
                    currencyItem.getPriceBuy(),
                    currencyItem.getPriceSale(),
                    currencyItem.isBestBuy(),
                    currencyItem.isBestSale());
            array.add(currencyListItem);
        }
    }
}
