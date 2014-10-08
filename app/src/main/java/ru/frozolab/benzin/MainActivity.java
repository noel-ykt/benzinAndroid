package ru.frozolab.benzin;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;


public class MainActivity extends TabActivity {
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHost = getTabHost();
        this.setNewTab(this, tabHost, "tab1", "Валюта", new Intent(this, CurrencyMainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        this.setNewTab(this, tabHost, "tab2", "Топливо", new Intent(this, FuelMainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        tabHost.getTabWidget().setBackgroundColor(getResources().getColor(R.color.yellow));
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(R.id.textView); //Unselected Tabs
            tv.setTextColor(getResources().getColor(R.color.yellow));
            if (tabHost.getTabWidget().getChildAt(i).isSelected()) {
                tv.setTextColor(getResources().getColor(R.color.black));
            }
        }
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                    TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(R.id.textView); //Unselected Tabs
                    tv.setTextColor(getResources().getColor(R.color.yellow));
                }
                TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(R.id.textView); //for Selected Tab
                tv.setTextColor(getResources().getColor(R.color.black));
            }
        });
    }

    private void setNewTab(Context context, TabHost tabHost, String tag, String title, Intent intent ){
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(tag);
        tabSpec.setIndicator(getTabIndicator(tabHost.getContext(), title)); // new function to inject our own tab layout
        tabSpec.setContent(intent);
        tabHost.addTab(tabSpec);
    }

    private View getTabIndicator(Context context, String title) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(title);
        return view;
    }
}