package com.tabalab.benzinyakutsk;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.tabalab.benzinyakutsk.adapter.PriceAdapter;
import com.tabalab.benzinyakutsk.model.Price;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    ListView itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemList = (ListView) findViewById(R.id.listView);

        List<Price> items = initData();
        PriceAdapter adapter = new PriceAdapter(this, items);
        itemList.setAdapter(adapter);
    }

    private List<Price> initData() {
        List<Price> result = new ArrayList<Price>();

        return result;
    }
}
