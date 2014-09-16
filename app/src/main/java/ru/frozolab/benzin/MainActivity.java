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

import java.util.ArrayList;
import java.util.List;

import ru.frozolab.benzin.adapter.MainItemAdapter;
import ru.frozolab.benzin.model.MainListItem;


public class MainActivity extends Activity {

    ListView itemList;
    ImageView aboutImg;

    public static final String EXTRA_TYPEID = "com.tabalab.benzinyakutsk.typeid";

    List<MainListItem> itemsResult = new ArrayList<MainListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        MainItemAdapter adapter = new MainItemAdapter(this, itemsResult);
        itemList.setAdapter(adapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainListItem selectedItem = (MainListItem) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), ViewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra(EXTRA_TYPEID, selectedItem.getType().getId());
                startActivity(intent);
                overridePendingTransition(R.animator.slide_left_in, R.animator.slide_left_out);
            }
        });
    }

    private class ProgressTask extends AsyncTask<String, String, Void> {
        private ProgressDialog dialog = new ProgressDialog(MainActivity.this);

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
            itemsResult = MainListItem.getAll();
            return null;
        }
    }
}