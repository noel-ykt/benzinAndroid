package ru.frozolab.benzin.model.fuel;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class FuelType {
    private int id;
    private String name;
    private String description;
    private int sort;

    public static final FuelType UNKNOWN = new FuelType(0, "Unknown", "Unknown", 0);

    public FuelType(int id, String name, String description, int sort) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sort = sort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public static FuelType initFromJSON(JSONObject object) {
        FuelType result;
        try {
            result = new FuelType(object.getInt("id"), object.getString("name"), object.getString("description"), object.getInt("sort"));
        } catch (JSONException ex) {
            Log.e("JSONException when parse type", ex.getLocalizedMessage());
            result = FuelType.UNKNOWN;
        }
        return result;
    }
}
