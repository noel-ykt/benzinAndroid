package ru.frozolab.benzin;

import android.app.ActivityGroup;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.frozolab.benzin.adapter.fuel.FuelMainItemAdapter;
import ru.frozolab.benzin.model.fuel.FuelItem;
import ru.frozolab.benzin.model.fuel.FuelListItem;


public class FuelMainActivity extends ActivityGroup {

    ListView itemList;
    ImageView aboutImg;
    TabHost tabHost;


    public static final String EXTRA_TYPEID = "ru.frozolab.benzin.typeid";

    List<FuelListItem> itemsResult = new ArrayList<FuelListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fuel);
        new ProgressTask().execute();

        aboutImg = (ImageView) findViewById(R.id.about);
        aboutImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(R.animator.slide_left_in, R.animator.slide_left_out);
            }
        });
    }

    private void initData() {
        itemList = (ListView) findViewById(R.id.listMain);

        FuelMainItemAdapter adapter = new FuelMainItemAdapter(this, itemsResult);
        itemList.setAdapter(adapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FuelListItem selectedItem = (FuelListItem) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), ViewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra(EXTRA_TYPEID, selectedItem.getFuelType().getId());

                startActivity(intent);
                overridePendingTransition(R.animator.slide_left_in, R.animator.slide_left_out);
            }
        });
    }

    private class ProgressTask extends AsyncTask<String, String, Void> {
        private ProgressDialog dialog = new ProgressDialog(FuelMainActivity.this);

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
            initData();
        }

        @Override
        protected Void doInBackground(String... strings) {
            itemsResult = FuelItem.getMain();
            Collections.sort(itemsResult, FuelListItem.Comparators.PRICE);
            return null;
        }
    }
}