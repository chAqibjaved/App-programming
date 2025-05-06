package com.aqib.mymedreminder.utils;


import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GsonParser {
    public static Gson gson;
    private static GsonParser gsonParser;

    private GsonParser() {
        gson = new Gson();
    }

    public static synchronized GsonParser getInstance() {
        GsonParser gsonParser2;
        synchronized (GsonParser.class) {
            if (gsonParser == null) {
                gsonParser = new GsonParser();
            }
            gsonParser2 = gsonParser;
        }
        return gsonParser2;
    }

    public String GenerateJsonFromMode(Object obj) {
        return gson.toJson(obj);
    }

    public <T> List<T> convertFromJsonArray(JSONArray jSONArray, Class<T> cls) {
        try {
            Gson gson2 = new Gson();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList.add(gson2.fromJson(jSONArray.getJSONObject(i).toString(), cls));
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public <T> T convertFromJsonObject(JSONObject jSONObject, Class<T> cls) {
        try {
            return new Gson().fromJson(jSONObject.toString(), cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> List<T> convertFromJsonArray(String str, Class<T> cls) {
        try {
            Gson gson2 = new Gson();
            ArrayList arrayList = new ArrayList();
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList.add(gson2.fromJson(jSONArray.getJSONObject(i).toString(), cls));
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T convertJsonToObject(String str, Class<T> cls) {
        return gson.fromJson(str, cls);
    }

    public <T> T convertJsonArrayToObject(JSONArray jSONArray, Class<T> cls) {
        try {
            return gson.fromJson(jSONArray.getJSONObject(0).toString(), cls);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
