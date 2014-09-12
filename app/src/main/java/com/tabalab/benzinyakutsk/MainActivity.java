package com.tabalab.benzinyakutsk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.tabalab.benzinyakutsk.adapter.MainItemAdapter;
import com.tabalab.benzinyakutsk.model.Company;
import com.tabalab.benzinyakutsk.model.ListItem;
import com.tabalab.benzinyakutsk.model.Type;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    ListView itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemList = (ListView) findViewById(R.id.listView);

        List<ListItem> items = initData();
        MainItemAdapter adapter = new MainItemAdapter(this, items);
        itemList.setAdapter(adapter);
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

    @Override
    protected void onPause() {
        super.onPause();
    }
}