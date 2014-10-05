package ru.frozolab.benzin.adapter.fuel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.frozolab.benzin.R;
import ru.frozolab.benzin.model.fuel.FuelListItem;

public class FuelViewItemAdapter extends BaseAdapter {
    private List<FuelListItem> list = new ArrayList<FuelListItem>();
    private LayoutInflater layoutInflater;

    public FuelViewItemAdapter(Context context, List<FuelListItem> list) {
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
            view = layoutInflater.inflate(R.layout.activity_view_fuel_item, parent, false);
        }
        FuelListItem item = getListItem(position);

        TextView companyName = (TextView) view.findViewById(R.id.companyName);
        companyName.setText(item.getCompanies().get(0).getName());

        TextView price = (TextView) view.findViewById(R.id.price);
        price.setText(item.getPrice().getAmount().toString());

        return view;
    }

    private FuelListItem getListItem(int position) {
        return (FuelListItem) getItem(position);
    }
}
