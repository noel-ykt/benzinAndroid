package com.tabalab.benzinyakutsk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tabalab.benzinyakutsk.R;
import com.tabalab.benzinyakutsk.model.Company;
import com.tabalab.benzinyakutsk.model.ListItem;

import java.util.ArrayList;
import java.util.List;

public class PriceAdapter extends BaseAdapter {
    private List<ListItem> list = new ArrayList<ListItem>();
    private LayoutInflater layoutInflater;

    public PriceAdapter(Context context, List<ListItem> list) {
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.activity_main_item, parent, false);
        }
        ListItem item = getListItem(position);

        TextView typeName = (TextView) view.findViewById(R.id.typeName);
        typeName.setText(item.getType().getName());

        TextView typeDesc = (TextView) view.findViewById(R.id.typeDesc);
        typeDesc.setText(item.getType().getDescription());

        TextView price = (TextView) view.findViewById(R.id.price);
        price.setText(item.getPrice());

        TextView companyNames = (TextView) view.findViewById(R.id.companyName);
        ArrayList<String> companyNamesArr = new ArrayList<String>();
        for (Company company : item.getCompanies()) {
            companyNamesArr.add(company.getName());
        }
        companyNames.setText(companyNamesArr.toString());

        return view;
    }

    private ListItem getListItem(int position) {
        return (ListItem) getItem(position);
    }
}
