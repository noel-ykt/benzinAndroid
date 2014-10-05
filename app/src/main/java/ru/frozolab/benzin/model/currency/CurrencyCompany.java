package ru.frozolab.benzin.model.currency;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyCompany {
    private String id;
    private String name;

    public static final CurrencyCompany UNKNOWN = new CurrencyCompany("Unknown", "Unknown");

    public CurrencyCompany(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static CurrencyCompany initFromJSON(JSONObject object) {
        CurrencyCompany result;
        try {
            result = new CurrencyCompany(object.getString("id"), object.getString("name"));
        } catch (JSONException ex) {
            Log.e("JSONException when parse company", ex.getLocalizedMessage());
            result = CurrencyCompany.UNKNOWN;
        }
        return result;
    }
}
