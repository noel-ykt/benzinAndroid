package ru.frozolab.benzin.util;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

public class JSONParser {
    static JSONArray result = new JSONArray();

    public static JSONArray getJSONFromUrl(String url) {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                builder.append(EntityUtils.toString(entity));
            } else {
                Log.e("==>", "Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.getLocalizedMessage();
        } catch (IOException e) {
            e.getLocalizedMessage();
        } catch (Exception e) {
            Log.e("JSON url error", e.getLocalizedMessage());
            e.printStackTrace();
        }

        try {
            result = new JSONArray(builder.toString());
        } catch (JSONException ex) {
            Log.e("JSON Parser", "Error parsing data " + ex.toString());
        }

        return result;
    }
}
