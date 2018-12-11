package edu.temple.stocksapp.utilities;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.temple.stocksapp.R;

public class StockListAdapter extends ArrayAdapter<Stock> implements ListAdapter {

    Context context;
    ArrayList<Stock> stocks;
    Stock stock;
    Utility utility;

    public static class ViewHolder {
        TextView symbolText;
        TextView highText;
        Button deleteButton;
    }

    public StockListAdapter(Context context, ArrayList<Stock> stocks) {
        super(context, 0, stocks);
        utility = new Utility(getContext());
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Stock stock = getItem(position);

        ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.stock_list_item, parent, false);

            viewHolder.symbolText = (TextView) convertView.findViewById(R.id.stock_symbol_text);
            viewHolder.deleteButton = (Button) convertView.findViewById(R.id.delete_button);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.symbolText.setText(stock.getStockSymbol());
        viewHolder.symbolText.setTextSize(30);
        viewHolder.symbolText.setTextColor(Color.parseColor("black"));
        //convertView.setBackgroundColor(Color.parseColor("white"));

        String json = stock.getStockJsonString();
        String priceStr = utility.getJSONChange(json);
        Double price = Double.valueOf(priceStr);

        if(price < 0) {
            convertView.setBackgroundColor(Color.RED);
        } else {
            convertView.setBackgroundColor(Color.GREEN);
        }

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utility.deleteStock(stock.getStockSymbol());
            }
        });

        return convertView;
    }
}
