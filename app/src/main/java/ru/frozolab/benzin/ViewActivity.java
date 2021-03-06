package ru.frozolab.benzin;

import android.app.Activity;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.frozolab.benzin.adapter.ViewItemAdapter;
import ru.frozolab.benzin.model.Item;
import ru.frozolab.benzin.model.ListItem;


public class ViewActivity extends Activity {

    ListView itemList;
    int typeId;
    public static final String EXTRA_COMPANY_FULL_NAME = "ru.frozolab.benzin.companyFullName";
    List<ListItem> itemsResult = new ArrayList<ListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        typeId = getIntent().getIntExtra(MainActivity.EXTRA_TYPEID, 0);

        ImageView imageBack = (ImageView) findViewById(R.id.back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView imageMap = (ImageView) findViewById(R.id.map);
        imageMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(R.animator.slide_left_in, R.animator.slide_left_out);
            }
        });
        new ProgressTask().execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.slide_right_in, R.animator.slide_right_out);
    }

    private void initData() {
        itemList = (ListView) findViewById(R.id.listView);

        String typeName = "";
        String typeDesc = "";
        for (ListItem item : itemsResult) {
            typeName = item.getType().getName();
            typeDesc = item.getType().getDescription();
        }

        TextView viewTitle = (TextView) findViewById(R.id.viewTitle);
        viewTitle.setText(typeName);

        TextView viewTypeDesc = (TextView) findViewById(R.id.viewTitleHelp);
        viewTypeDesc.setText(typeDesc);

        ViewItemAdapter adapter = new ViewItemAdapter(this, itemsResult);
        itemList.setAdapter(adapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListItem selectedItem = (ListItem) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra(EXTRA_COMPANY_FULL_NAME, selectedItem.getCompanies().get(0).getFullName());
                startActivity(intent);
                overridePendingTransition(R.animator.slide_left_in, R.animator.slide_left_out);
            }
        });
    }

    private class ProgressTask extends AsyncTask<String, String, Void> {
        private ProgressDialog dialog = new ProgressDialog(ViewActivity.this);

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
            itemsResult = Item.getView(typeId);
            Collections.sort(itemsResult, ListItem.Comparators.PRICE);
            return null;
        }
    }
}
