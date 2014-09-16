package ru.frozolab.benzin.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Company {
    private int id;
    private String name;

    public static final Company UNKNOWN = new Company(0, "Unknown");

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
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

    public static Company initFromJSON(JSONObject object) {
        Company result;
        try {
            result = new Company(object.getInt("id"), object.getString("name"));
        } catch (JSONException ex) {
            Log.e("JSONException when parse company", ex.getLocalizedMessage());
            result = Company.UNKNOWN;
        }
        return result;
    }
}
