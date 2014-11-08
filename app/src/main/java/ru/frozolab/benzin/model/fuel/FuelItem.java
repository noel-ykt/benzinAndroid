package ru.frozolab.benzin.model.fuel;

import android.util.Log;

import com.google.common.collect.Lists;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import ru.frozolab.benzin.util.Cache;
import ru.frozolab.benzin.util.JSONParser;
import ru.frozolab.benzin.util.Money;

public class FuelItem {
    public static final String URL = "http://fuel.api.frozolab.ru/v2";
    private static final String TAG_TYPE = "type";
    private static final String TAG_COMPANY = "company";
    private static final String TAG_PRICE = "price";

    private FuelType fuelType;
    private FuelCompany fuelCompany;
    private Money price;

    public FuelItem(FuelType fuelType, FuelCompany fuelCompany, Money price) {
        this.fuelType = fuelType;
        this.fuelCompany = fuelCompany;
        this.price = price;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public FuelCompany getFuelCompany() {
        return fuelCompany;
    }

    public Money getPrice() {
        return price;
    }

    public static List<FuelListItem> getMain() {
        return Cache.getFuelMainItems();
    }

    public static List<FuelListItem> getView(int typeId) {
        return Cache.getFuelViewItems(typeId);
    }

    public static List<FuelItem> loadItems() {
        List<FuelItem> itemsResult = Lists.newArrayList();

        JSONArray jsonArray = JSONParser.getJSONFromUrl(URL);

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Type
                JSONObject jsonType = jsonObject.getJSONObject(TAG_TYPE);
                FuelType fuelType = FuelType.initFromJSON(jsonType);
                //Company
                JSONObject jsonCompany = jsonObject.getJSONObject(TAG_COMPANY);
                FuelCompany fuelCompany = FuelCompany.initFromJSON(jsonCompany);
                //Price
                Money price = Money.parse(Money.RUB, jsonObject.getString(TAG_PRICE));

                FuelItem fuelItem = new FuelItem(fuelType, fuelCompany, price);
                itemsResult.add(fuelItem);
            } catch (Exception ex) {
                Log.e("Parse Json Object", ex.getLocalizedMessage());
            }
        }
        return itemsResult;
    }

    public static List<FuelListItem> getPreparedViewItems(int typeId) {
        List<FuelListItem> result = Lists.newArrayList();
        for (FuelItem fuelItem : Cache.getFuelItems()) {
            if (fuelItem.getFuelType().getId() == typeId) {
                upsertViewItem(result, fuelItem);
            }
        }
        return result;
    }

    public static List<FuelListItem> getPreparedMainItems() {
        List<FuelListItem> result = Lists.newArrayList();
        for (FuelItem fuelItem : Cache.getFuelItems()) {
            upsertMainItem(result, fuelItem);
        }
        return result;
    }

    private static void upsertViewItem(List<FuelListItem> array, FuelItem fuelItem) {
        List<FuelCompany> companies = Lists.newArrayList();
        companies.add(fuelItem.getFuelCompany());
        FuelListItem fuelListItem = new FuelListItem(fuelItem.getFuelType(), companies, fuelItem.getPrice());
        array.add(fuelListItem);
    }

    private static void upsertMainItem(List<FuelListItem> array, FuelItem fuelItem) {
        boolean isExist = false;
        for (FuelListItem fuelListItem : array) {
            if (fuelListItem.getFuelType().getId() == fuelItem.getFuelType().getId()) {
                isExist = true;
                if (fuelListItem.getPrice().isGreaterThan(fuelItem.getPrice())) {
                    List<FuelCompany> newCompanies = Lists.newArrayList();
                    newCompanies.add(fuelItem.getFuelCompany());
                    fuelListItem.setCompanies(newCompanies);
                    fuelListItem.setPrice(fuelItem.getPrice());
                } else if (fuelListItem.getPrice().isEqual(fuelItem.getPrice())) {
                    fuelListItem.addCompny(fuelItem.getFuelCompany());
                }
            }
        }

        if (!isExist) {
            List<FuelCompany> companies = Lists.newArrayList();
            companies.add(fuelItem.getFuelCompany());
            FuelListItem fuelListItem = new FuelListItem(fuelItem.getFuelType(), companies, fuelItem.getPrice());
            array.add(fuelListItem);
        }
    }
}
