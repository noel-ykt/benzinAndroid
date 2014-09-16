package ru.frozolab.benzin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.frozolab.benzin.R;
import ru.frozolab.benzin.model.ViewListItem;

public class ViewItemAdapter extends BaseAdapter {
    private List<ViewListItem> list = new ArrayList<ViewListItem>();
    private LayoutInflater layoutInflater;

    public ViewItemAdapter(Context context, List<ViewListItem> list) {
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
            view = layoutInflater.inflate(R.layout.activity_view_item, parent, false);
        }
        ViewListItem item = getListItem(position);

        TextView companyName = (TextView) view.findViewById(R.id.companyName);
        companyName.setText(item.getCompany().getName());

        TextView price = (TextView) view.findViewById(R.id.price);
        price.setText(item.getPrice());

        return view;
    }

    private ViewListItem getListItem(int position) {
        return (ViewListItem) getItem(position);
    }
}
