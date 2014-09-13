package com.tabalab.benzinyakutsk.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Type {
    private int id;
    private String name;
    private String description;

    public static final Type UNKNOWN = new Type(0, "Unknown", "Unknown");

    public Type(int id, String name, String description) {
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

    public static Type initFromJSON(JSONObject object) {
        Type result;
        try {
            result = new Type(object.getInt("id"), object.getString("name"), object.getString("description"));
        } catch (JSONException ex) {
            Log.e("JSONException when parse type", ex.getLocalizedMessage());
            result = Type.UNKNOWN;
        }
        return result;
    }
}
