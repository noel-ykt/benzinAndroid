package com.tabalab.benzinyakutsk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.tabalab.benzinyakutsk.adapter.MainItemAdapter;
import com.tabalab.benzinyakutsk.model.Company;
import com.tabalab.benzinyakutsk.model.ListItem;
import com.tabalab.benzinyakutsk.model.Type;
import com.tabalab.benzinyakutsk.util.JSONParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    ListView itemList;

    private static String url = "http://151.248.122.171:3000/prices";

    private static final String TYPE = "type";
    private static final String COMPANIES = "companies";
    private static final String PRICE = "price";

    List<ListItem> itemsResult = new ArrayList<ListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ProgressTask().execute();
    }

    private void initData(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            ArrayList<Company> companies = new ArrayList<Company>();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Companies
                JSONArray jsonCompanies = jsonObject.getJSONArray(COMPANIES);
                for (int j = 0; j < jsonCompanies.length(); j++) {
                    JSONObject jsonCompany = jsonCompanies.getJSONObject(j);
                    Company company = Company.getFromJSON(jsonCompany);
                    companies.add(company);
                }
                //Type
                JSONObject jsonType = jsonObject.getJSONObject(TYPE);
                Type type = Type.getFromJSON(jsonType);
                //Price
                String price = jsonObject.getString(PRICE);

                ListItem item = new ListItem(type, companies, price);
                itemsResult.add(item);
            } catch (Exception ex) {
                Log.e("Parse Json Object", ex.getLocalizedMessage());
            }
        }

        itemList = (ListView) findViewById(R.id.listView);

        MainItemAdapter adapter = new MainItemAdapter(this, itemsResult);
        itemList.setAdapter(adapter);
    }

    public void goToView(View v) {
        Intent intent = new Intent(this, ViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("typeId", 42);
        startActivity(intent);
        overridePendingTransition(R.animator.slide_left_in, R.animator.slide_left_out);
    }

    private class ProgressTask extends AsyncTask<String, String, Void> {
        private ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        JSONArray responseJSONArray;

        protected void onPreExecute() {
            this.dialog.setMessage(getString(R.string.loading));
            this.dialog.show();
            this.dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    ProgressTask.this.cancel(true);
                }
            });
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            this.dialog.hide();
            initData(responseJSONArray);
        }

        @Override
        protected Void doInBackground(String... strings) {
            responseJSONArray = JSONParser.getJSONFromUrl(url);
            return null;
        }
    }


}