package ru.frozolab.benzin.adapter.currency;

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
import ru.frozolab.benzin.model.currency.CurrencyCompany;
import ru.frozolab.benzin.model.currency.CurrencyListItem;

public class CurrencyMainItemAdapter extends BaseAdapter {
    private List<CurrencyListItem> list = new ArrayList<CurrencyListItem>();
    private LayoutInflater layoutInflater;

    public CurrencyMainItemAdapter(Context context, List<CurrencyListItem> list) {
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
            view = layoutInflater.inflate(R.layout.activity_main_currency_item, parent, false);
        }
        CurrencyListItem item = getListItem(position);

        TextView typeName = (TextView) view.findViewById(R.id.typeName);
        typeName.setText(item.getCurrencyType().getName());

        TextView typeDesc = (TextView) view.findViewById(R.id.typeDesc);
        typeDesc.setText(item.getCurrencyType().getType());

        TextView price = (TextView) view.findViewById(R.id.price);
        price.setText(item.getPriceSale().getAmount().toString());

        TextView companyNames = (TextView) view.findViewById(R.id.companyName);
        ArrayList<String> companyNamesArr = new ArrayList<String>();
        for (CurrencyCompany currencyCompany : item.getCompanies()) {
            companyNamesArr.add(currencyCompany.getName());
        }
        companyNames.setText(Joiner.on(", ").join(companyNamesArr));

        return view;
    }

    private CurrencyListItem getListItem(int position) {
        return (CurrencyListItem) getItem(position);
    }
}
