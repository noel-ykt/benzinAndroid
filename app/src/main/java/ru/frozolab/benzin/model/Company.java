package ru.frozolab.benzin.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Company {
    private int id;
    private String name;
    private String fullName;

    public static final Company UNKNOWN = new Company(0, "Unknown", "Unknown");

    public Company(int id, String name, String fullName) {
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

    public static Company initFromJSON(JSONObject object) {
        Company result;
        try {
            result = new Company(object.getInt("id"), object.getString("name"), object.getString("full_name"));
        } catch (JSONException ex) {
            Log.e("JSONException when parse company", ex.getLocalizedMessage());
            result = Company.UNKNOWN;
        }
        return result;
    }
}
