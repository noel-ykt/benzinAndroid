package ru.frozolab.benzin.model.fuel;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class FuelType {
    private int id;
    private String name;
    private String description;

    public static final FuelType UNKNOWN = new FuelType(0, "Unknown", "Unknown");

    public FuelType(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public static FuelType initFromJSON(JSONObject object) {
        FuelType result;
        try {
            result = new FuelType(object.getInt("id"), object.getString("name"), object.getString("description"));
        } catch (JSONException ex) {
            Log.e("JSONException when parse type", ex.getLocalizedMessage());
            result = FuelType.UNKNOWN;
        }
        return result;
    }
}
