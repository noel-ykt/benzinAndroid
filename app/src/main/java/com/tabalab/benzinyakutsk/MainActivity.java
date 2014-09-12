package com.tabalab.benzinyakutsk;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.tabalab.benzinyakutsk.adapter.MainItemAdapter;
import com.tabalab.benzinyakutsk.model.Company;
import com.tabalab.benzinyakutsk.model.ListItem;
import com.tabalab.benzinyakutsk.model.Type;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    ListView itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        itemList = (ListView) findViewById(R.id.listView);
//
//        List<ListItem> items = initData();
//        PriceAdapter adapter = new PriceAdapter(this, items);
//        itemList.setAdapter(adapter);

        new HttpAsyncTask().execute("http://151.248.122.171:3000/prices");
    }

    public void goToView(View v) {
        Intent intent = new Intent(this, ViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("typeId", 42);
        startActivity(intent);
        overridePendingTransition(R.animator.slide_left_in, R.anim.abc_fade_out);
    }

    private List<ListItem> initData(String jsonString) {
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (Exception ex) {
            Log.d("Parse Json Array", ex.getLocalizedMessage());
        }
        List<ListItem> result = new ArrayList<ListItem>();

        for (int i = 0; i < jsonArray.length(); i++) {
            ArrayList<Company> companies = new ArrayList<Company>();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                //Companies
                JSONArray jsonCompanies = jsonObject.getJSONArray("companies");
                for (int j = 0; j < jsonCompanies.length(); j++) {
                    JSONObject jsonCompany = jsonCompanies.getJSONObject(j);
                    Company company = new Company(jsonCompany.getInt("id"), jsonCompany.getString("name"));
                    companies.add(company);
                }
                //Type
                JSONObject jsonType = jsonObject.getJSONObject("type");
                Type type = new Type(jsonType.getInt("id"), jsonType.getString("name"), jsonType.getString("description"));
                //Price
                String price = jsonObject.getString("price");

                ListItem item = new ListItem(type, companies, price);
                result.add(item);
            } catch (Exception ex) {
                Log.d("Parse Json Object", ex.getLocalizedMessage());
            }
        }
        return result;
    }

    private static String GET(String url) {
        InputStream inputStream;
        String result = "[]";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
            }
            Log.d("GET result", result);
        } catch (Exception ex) {
            Log.d("InputStream", ex.getLocalizedMessage());
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        inputStream.close();
        return result.toString();
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return GET(strings[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Recieved!", Toast.LENGTH_SHORT).show();
            itemList = (ListView) findViewById(R.id.listView);

            List<ListItem> items = initData(result);
            MainItemAdapter adapter = new MainItemAdapter(getApplicationContext(), items);
            itemList.setAdapter(adapter);
        }
    }
}
