package ru.frozolab.benzin.adapter.currency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.frozolab.benzin.R;
import ru.frozolab.benzin.model.currency.CurrencyListItem;

public class CurrencyViewItemAdapter extends BaseAdapter {
    private List<CurrencyListItem> list = new ArrayList<CurrencyListItem>();
    private LayoutInflater layoutInflater;

    public CurrencyViewItemAdapter(Context context, List<CurrencyListItem> list) {
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
            view = layoutInflater.inflate(R.layout.activity_view_currency_item, parent, false);
        }
        CurrencyListItem item = getListItem(position);

        TextView companyName = (TextView) view.findViewById(R.id.companyName);
        companyName.setText(item.getCompanies().get(0).getName());

        TextView priceBuy = (TextView) view.findViewById(R.id.priceBuy);
        priceBuy.setText(item.getPriceBuy().getAmount().toString());

        if (item.isBestBuy()) {
            priceBuy.setBackgroundColor(parent.getResources().getColor(R.color.yellow));
            priceBuy.setTextColor(parent.getResources().getColor(R.color.black));
        } else {
            priceBuy.setBackgroundColor(parent.getResources().getColor(R.color.black));
            priceBuy.setTextColor(parent.getResources().getColor(R.color.yellow));
        }

        TextView priceSale = (TextView) view.findViewById(R.id.priceSale);
        priceSale.setText(item.getPriceSale().getAmount().toString());

        if (item.isBestSale()) {
            priceSale.setBackgroundColor(parent.getResources().getColor(R.color.yellow));
            priceSale.setTextColor(parent.getResources().getColor(R.color.black));
        } else {
            priceSale.setBackgroundColor(parent.getResources().getColor(R.color.black));
            priceSale.setTextColor(parent.getResources().getColor(R.color.yellow));
        }

        return view;
    }

    private CurrencyListItem getListItem(int position) {
        return (CurrencyListItem) getItem(position);
    }
}
