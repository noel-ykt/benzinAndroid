package ru.frozolab.benzin.model.fuel;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class FuelCompany {
    private int id;
    private String name;
    private String fullName;

    public static final FuelCompany UNKNOWN = new FuelCompany(0, "Unknown", "Unknown");

    public FuelCompany(int id, String name, String fullName) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public static FuelCompany initFromJSON(JSONObject object) {
        FuelCompany result;
        try {
            result = new FuelCompany(object.getInt("id"), object.getString("name"), object.getString("full_name"));
        } catch (JSONException ex) {
            Log.e("JSONException when parse company", ex.getLocalizedMessage());
            result = FuelCompany.UNKNOWN;
        }
        return result;
    }
}
