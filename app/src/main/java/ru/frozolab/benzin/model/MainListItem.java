package ru.frozolab.benzin.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.frozolab.benzin.util.Cache;
import ru.frozolab.benzin.util.JSONParser;

public class MainListItem {
    public static final String URL = "http://151.248.122.171:3000/prices";
    private static final String TAG_TYPE = "type";
    private static final String TAG_COMPANIES = "companies";
    private static final String TAG_PRICE = "price";

    private Type type;
    private ArrayList<Company> companies;
    private String price;

    public MainListItem(Type type, ArrayList<Company> companies, String price) {
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

    public static List<MainListItem> getAll() {
        return Cache.getMainItems();
    }

    public static List<MainListItem> loadItems() {
        List<MainListItem> itemsResult = new ArrayList<MainListItem>();

        JSONArray jsonArray = JSONParser.getJSONFromUrl(URL);

        for (int i = 0; i < jsonArray.length(); i++) {
            ArrayList<Company> companies = new ArrayList<Company>();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Companies
                JSONArray jsonCompanies = jsonObject.getJSONArray(TAG_COMPANIES);
                for (int j = 0; j < jsonCompanies.length(); j++) {
                    JSONObject jsonCompany = jsonCompanies.getJSONObject(j);
                    Company company = Company.initFromJSON(jsonCompany);
                    companies.add(company);
                }
                //Type
                JSONObject jsonType = jsonObject.getJSONObject(TAG_TYPE);
                Type type = Type.initFromJSON(jsonType);
                //Price
                String price = jsonObject.getString(TAG_PRICE);

                MainListItem item = new MainListItem(type, companies, price);
                itemsResult.add(item);
            } catch (Exception ex) {
                Log.e("Parse Json Object", ex.getLocalizedMessage());
            }
        }
        return itemsResult;
    }
}
