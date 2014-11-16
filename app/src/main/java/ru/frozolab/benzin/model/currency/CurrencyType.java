package ru.frozolab.benzin.model.currency;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyType {

    public enum TYPE {
        USD("usd", "Доллар"),
        EUR("eu", "Евро"),
        CNY("cny_10", "10 юаней"),
        GOLD("gold", "Золото"),
        SILVER("silver", "Серебро");

        private String codeName;
        private String name;

        TYPE(String codeName, String name) {
            this.codeName = codeName;
            this.name = name;
        }

        public String getCodeName() {
            return codeName;
        }

        public void setCodeName(String codeName) {
            this.codeName = codeName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        /**
         * Default - USD
         * @param codeName
         * @return
         */
        public static TYPE getByCodeName(String codeName) {
            TYPE result = TYPE.USD;
            for (TYPE type : TYPE.values()) {
                if (type.getCodeName().equalsIgnoreCase(codeName)) {
                    result = type;
                    break;
                }
            }
            return result;
        }
    }

    private int id;
    private String name;
    private TYPE type;
    private String label;
    private int sort;

    public static final CurrencyType UNKNOWN = new CurrencyType(0, "Unknown", TYPE.USD, "Unknown", 0);

    public CurrencyType(int id, String name, TYPE type, String label, int sort) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.label = label;
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

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public static CurrencyType initFromJSON(JSONObject object) {
        CurrencyType result;
        try {
            String typeCodeName = object.getString("type");
            TYPE type = TYPE.getByCodeName(typeCodeName);
            result = new CurrencyType(object.getInt("id"), object.getString("name"), type, object.getString("label"), object.getInt("sort"));
        } catch (JSONException ex) {
            Log.e("JSONException when parse type", ex.getLocalizedMessage());
            result = CurrencyType.UNKNOWN;
        }
        return result;
    }
}
