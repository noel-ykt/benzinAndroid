package com.tabalab.benzinyakutsk;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.tabalab.benzinyakutsk.adapter.MainItemAdapter;
import com.tabalab.benzinyakutsk.model.Company;
import com.tabalab.benzinyakutsk.model.ListItem;
import com.tabalab.benzinyakutsk.model.Type;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
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
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity {

    ListView itemList;

    private Context context;
    private static String url = "http://151.248.122.171:3000/prices";

    private static final String VTYPE = "Type";
    private static final String VCOLOR = "Color";
    private static final String FUEL = "Fuel";
    private static final String TREAD = "Tread";

    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemList = (ListView) findViewById(R.id.listView);

        List<ListItem> items = initData();
        MainItemAdapter adapter = new MainItemAdapter(this, items);
        itemList.setAdapter(adapter);

        new ProgressTask().execute();
    }

    private List<ListItem> initData() {
        List<ListItem> result = new ArrayList<ListItem>();

        ArrayList<Company> companies = new ArrayList<Company>();
        Company company1 = new Company(1, "SINET");
        Company company2 = new Company(2, "ЯТЭК");
        companies.add(company1);
        companies.add(company2);

        Type type1 = new Type(1, "ДТ", "Дизельное топливо");
        ListItem item1 = new ListItem(type1, companies, "35.40");

        Type type2 = new Type(1, "Р-92", "Регуляр");
        ListItem item2 = new ListItem(type2, companies, "35.60");

        result.add(item1);
        result.add(item2);
        return result;
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
        String result = "";

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
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);


                }
            } catch (JSONException ex) {
                Log.e("JSONException", "Error: " + ex.toString());
            }
        }

        @Override
        protected Void doInBackground(String... strings) {
            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {
                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(param));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                // Read content & Log
                inputStream = httpEntity.getContent();
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
                result = stringBuilder.toString();
            } catch (Exception e) {
                Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
            }
            return null;
        }
    }


}