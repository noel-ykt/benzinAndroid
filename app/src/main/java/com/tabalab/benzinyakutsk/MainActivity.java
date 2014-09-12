package com.tabalab.benzinyakutsk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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

    private Context context;
    private static String url = "http://151.248.122.171:3000/prices";

    private static final String VTYPE = "Type";
    private static final String VCOLOR = "Color";
    private static final String FUEL = "Fuel";
    private static final String TREAD = "Tread";

    List<ListItem> itemsResult = new ArrayList<ListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ProgressTask().execute();
    }

    private void initData(String jsonString) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
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
                    itemsResult.add(item);
                } catch (Exception ex) {
                    Log.d("Parse Json Object", ex.getLocalizedMessage());
                }
            }
        } catch (JSONException ex) {
            Log.e("JSONException", "Error: " + ex.toString());
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
        InputStream inputStream;
        String responseResult = "[]";

        protected void onPreExecute() {
            this.dialog.setMessage("Progress start");
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
            initData(responseResult);
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                HttpClient httpClient = new DefaultHttpClient();

                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                StatusLine statusLine = httpResponse.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    HttpEntity httpEntity = httpResponse.getEntity();
                    inputStream = httpEntity.getContent();
                }
            } catch (UnsupportedEncodingException e1) {
                Log.e("UnsupportedEncodingException", e1.toString());
                e1.printStackTrace();
            } catch (ClientProtocolException e2) {
                Log.e("ClientProtocolException", e2.toString());
                e2.printStackTrace();
            } catch (IllegalStateException e3) {
                Log.e("IllegalStateException", e3.toString());
                e3.printStackTrace();
            } catch (IOException e4) {
                Log.e("IOException", e4.toString());
                e4.printStackTrace();
            }

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + '\n');
                }
                inputStream.close();
                responseResult = stringBuilder.toString();
            } catch (Exception e) {
                Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
            }
            return null;
        }
    }


}