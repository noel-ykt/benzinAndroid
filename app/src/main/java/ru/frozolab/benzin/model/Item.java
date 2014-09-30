package ru.frozolab.benzin.model;

import android.util.Log;

import com.google.common.collect.Lists;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import ru.frozolab.benzin.util.Cache;
import ru.frozolab.benzin.util.JSONParser;

public class Item {
    public static final String URL = "http://fuel.api.frozolab.ru/v2";
    private static final String TAG_TYPE = "type";
    private static final String TAG_COMPANY = "company";
    private static final String TAG_PRICE = "price";

    private Type type;
    private Company company;
    private Money price;

    public Item(Type type, Company company, Money price) {
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

    public Money getPrice() {
        return price;
    }

    public static List<ListItem> getMain() {
        return Cache.getMainItems();
    }

    public static List<ListItem> getView(int typeId) {
        return Cache.getViewItems(typeId);
    }

    public static List<Item> loadItems() {
        List<Item> itemsResult = Lists.newArrayList();

        JSONArray jsonArray = JSONParser.getJSONFromUrl(URL);

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Type
                JSONObject jsonType = jsonObject.getJSONObject(TAG_TYPE);
                Type type = Type.initFromJSON(jsonType);
                //Company
                JSONObject jsonCompany = jsonObject.getJSONObject(TAG_COMPANY);
                Company company = Company.initFromJSON(jsonCompany);
                //Price
                Money price = Money.parse(CurrencyUnit.USD.getCode() + " " + jsonObject.getString(TAG_PRICE));

                Item item = new Item(type, company, price);
                itemsResult.add(item);
            } catch (Exception ex) {
                Log.e("Parse Json Object", ex.getLocalizedMessage());
            }
        }
        return itemsResult;
    }

    public static List<ListItem> getPreparedViewItems(int typeId) {
        List<ListItem> result = Lists.newArrayList();
        for (Item item : Cache.getItems()) {
            if (item.getType().getId() == typeId) {
                upsertViewItem(result, item);
            }
        }
        return result;
    }

    public static List<ListItem> getPreparedMainItems() {
        List<ListItem> result = Lists.newArrayList();
        for (Item item : Cache.getItems()) {
            upsertMainItem(result, item);
        }
        return result;
    }

    private static void upsertViewItem(List<ListItem> array, Item item) {
        List<Company> companies = Lists.newArrayList();
        companies.add(item.getCompany());
        ListItem listItem = new ListItem(item.getType(), companies, item.getPrice());
        array.add(listItem);
    }

    private static void upsertMainItem(List<ListItem> array, Item item) {
        boolean isExist = false;
        for (ListItem listItem : array) {
            if (listItem.getType().getId() == item.getType().getId()) {
                isExist = true;
                if (listItem.getPrice().isGreaterThan(item.getPrice())) {
                    List<Company> newCompanies = Lists.newArrayList();
                    newCompanies.add(item.getCompany());
                    listItem.setCompanies(newCompanies);
                    listItem.setPrice(item.getPrice());
                } else if (listItem.getPrice().isEqual(item.getPrice())) {
                    listItem.addCompny(item.getCompany());
                }
            }
        }

        if (!isExist) {
            List<Company> companies = Lists.newArrayList();
            companies.add(item.getCompany());
            ListItem listItem = new ListItem(item.getType(), companies, item.getPrice());
            array.add(listItem);
        }
    }
}
