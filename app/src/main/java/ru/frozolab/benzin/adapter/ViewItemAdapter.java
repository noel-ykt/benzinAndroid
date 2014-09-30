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
import ru.frozolab.benzin.model.ListItem;

public class ViewItemAdapter extends BaseAdapter {
    private List<ListItem> list = new ArrayList<ListItem>();
    private LayoutInflater layoutInflater;

    public ViewItemAdapter(Context context, List<ListItem> list) {
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
        ListItem item = getListItem(position);

        TextView companyName = (TextView) view.findViewById(R.id.companyName);
        companyName.setText(item.getCompanies().get(0).getName());

        TextView price = (TextView) view.findViewById(R.id.price);
        price.setText(item.getPrice().getAmount().toString());

        return view;
    }

    private ListItem getListItem(int position) {
        return (ListItem) getItem(position);
    }
}
