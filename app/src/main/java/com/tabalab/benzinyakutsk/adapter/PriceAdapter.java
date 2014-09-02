package com.tabalab.benzinyakutsk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tabalab.benzinyakutsk.R;
import com.tabalab.benzinyakutsk.model.Price;

import java.util.ArrayList;
import java.util.List;

public class PriceAdapter extends BaseAdapter {
    private List<Price> list = new ArrayList<Price>();
    private LayoutInflater layoutInflater;

    public PriceAdapter(Context context, List<Price> list) {
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
            view = layoutInflater.inflate(R.layout.activity_price, parent, false);
        }
        Price price = getPrice(position);

        TextView textView = (TextView) view.findViewById(R.id.priceView);
        textView.setText(price.getType().getName());

        return view;
    }

    private Price getPrice(int position) {
        return (Price) getItem(position);
    }
}
