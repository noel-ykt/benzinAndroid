package com.tabalab.benzinyakutsk.model;

import android.util.Log;

import com.tabalab.benzinyakutsk.util.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewListItem {
    private static final String URL = "http://151.248.122.171:3000/prices/type";
    private static final String TAG_TYPE = "type";
    private static final String TAG_COMPANY = "company";
    private static final String TAG_PRICE = "price";

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

    public static List<ViewListItem> loadItems(Integer typeId) {
        List<ViewListItem> itemsResult = new ArrayList<ViewListItem>();
        JSONArray jsonArray = JSONParser.getJSONFromUrl(URL + typeId);

        for (int i = 0; i < jsonArray.length(); i++) {
            ArrayList<Company> companies = new ArrayList<Company>();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Company
                JSONObject jsonCompany = jsonObject.getJSONObject(TAG_COMPANY);
                Company company = Company.initFromJSON(jsonCompany);
                companies.add(company);
                //Type
                JSONObject jsonType = jsonObject.getJSONObject(TAG_TYPE);
                Type type = Type.initFromJSON(jsonType);
                //Price
                String price = jsonObject.getString(TAG_PRICE);

                ViewListItem item = new ViewListItem(type, company, price);
                itemsResult.add(item);
            } catch (Exception ex) {
                Log.e("Parse Json Object", ex.getLocalizedMessage());
            }
        }
        return itemsResult;
    }
}
