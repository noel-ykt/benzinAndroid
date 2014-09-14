package com.tabalab.benzinyakutsk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.tabalab.benzinyakutsk.adapter.ViewItemAdapter;
import com.tabalab.benzinyakutsk.model.Company;
import com.tabalab.benzinyakutsk.model.Type;
import com.tabalab.benzinyakutsk.model.ViewListItem;
import com.tabalab.benzinyakutsk.util.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ViewActivity extends Activity {

    ListView itemList;

    int typeId;

    private static final String URL = "http://151.248.122.171:3000/prices/type";

    private static final String TAG_TYPE = "type";
    private static final String TAG_COMPANY = "company";
    private static final String TAG_PRICE = "price";

    List<ViewListItem> itemsResult = new ArrayList<ViewListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        typeId = getIntent().getIntExtra(MainActivity.EXTRA_TYPEID, 0);
        new ProgressTask().execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.slide_right_in, R.animator.slide_right_out);
    }

    private void initData(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            ArrayList<Company> companies = new ArrayList<Company>();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Company
                JSONObject jsonCompany = jsonObject.getJSONObject(TAG_COMPANY);
                Company company = Company.initFromJSON(jsonCompany);
                companies.add(company);
                //Type
                JSONObject jsonType = jsonObject.getJSONObject(TAG_TYPE);
                Type type = Type.initFromJSON(jsonType);
                //Price
                String price = jsonObject.getString(TAG_PRICE);

                ViewListItem item = new ViewListItem(type, company, price);
                itemsResult.add(item);
            } catch (Exception ex) {
                Log.e("Parse Json Object", ex.getLocalizedMessage());
            }
        }

        itemList = (ListView) findViewById(R.id.listView);

        ViewItemAdapter adapter = new ViewItemAdapter(this, itemsResult);
        itemList.setAdapter(adapter);
    }

    private class ProgressTask extends AsyncTask<String, String, Void> {
        private ProgressDialog dialog = new ProgressDialog(ViewActivity.this);
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
            this.dialog.dismiss();
            initData(responseJSONArray);
        }

        @Override
        protected Void doInBackground(String... strings) {
            responseJSONArray = JSONParser.getJSONFromUrl(URL + typeId);
            return null;
        }
    }
}
