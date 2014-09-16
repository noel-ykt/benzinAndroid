package ru.frozolab.benzin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.List;

import ru.frozolab.benzin.R;
import ru.frozolab.benzin.model.Company;
import ru.frozolab.benzin.model.MainListItem;

public class MainItemAdapter extends BaseAdapter {
    private List<MainListItem> list = new ArrayList<MainListItem>();
    private LayoutInflater layoutInflater;

    public MainItemAdapter(Context context, List<MainListItem> list) {
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
        MainListItem item = getListItem(position);

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
        companyNames.setText(Joiner.on(", ").join(companyNamesArr));

        return view;
    }

    private MainListItem getListItem(int position) {
        return (MainListItem) getItem(position);
    }
}
