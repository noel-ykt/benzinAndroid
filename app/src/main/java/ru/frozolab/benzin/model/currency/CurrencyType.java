package ru.frozolab.benzin.model.currency;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyType {
    private int id;
    private String name;
    private String type;

    public static final CurrencyType UNKNOWN = new CurrencyType(0, "Unknown", "Unknown");

    public CurrencyType(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static CurrencyType initFromJSON(JSONObject object) {
        CurrencyType result;
        try {
            result = new CurrencyType(object.getInt("id"), object.getString("name"), object.getString("type"));
        } catch (JSONException ex) {
            Log.e("JSONException when parse type", ex.getLocalizedMessage());
            result = CurrencyType.UNKNOWN;
        }
        return result;
    }
}
