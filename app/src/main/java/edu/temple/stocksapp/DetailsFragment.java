package edu.temple.stocksapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.support.v4.app.Fragment;


import java.util.ArrayList;

import edu.temple.stocksapp.utilities.Stock;
import edu.temple.stocksapp.utilities.Utility;

public class DetailsFragment extends Fragment {

    TextView companyNameTextView;
    TextView companyStockPrice;
    WebView stockDayGraph;
    Utility utility;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(String param1, String param2) {

        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utility = new Utility(getActivity());
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){

        if (container != null) {
            container.removeAllViews();
        }

        Bundle bundle = this.getArguments();
        int position = 0;
        String symbol = "";

        if(bundle != null) {
            position = bundle.getInt("position");
            symbol = bundle.getString("symbol");
        }

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        companyNameTextView = (TextView) view.findViewById(R.id.companyNameTextView);
        companyStockPrice = (TextView) view.findViewById(R.id.companyStockTextView);
        stockDayGraph = (WebView) view.findViewById(R.id.imageView);

        ArrayList<Stock> dataFromFile = utility.getStockData();

        if(bundle != null) {
            companyNameTextView.setText(utility.getJSONCompanyName(dataFromFile.get(position).getStockJsonString()));
            companyStockPrice.setText(utility.getJSONLastPrice(dataFromFile.get(position).getStockJsonString()));
        }
        Log.d("PAAAAAAAAAAAXXXXXXXXX", utility.getStockSymbols().get(position));
        if(!dataFromFile.isEmpty()) {
            stockDayGraph.getSettings().setJavaScriptEnabled(true);
            String symbolText = utility.getStockSymbols().get(position);
            stockDayGraph.loadUrl("https://macc.io/lab/cis3515/?symbol=" + symbolText + "&width=400&height=200");
        }
        //showStockChart(symbol);

        return view;
    }

    @Override
    public void onDetach () {
        super.onDetach();
    }

    public void showStockChart(String symbol) {

//            Picasso.with(getContext())
//                    .load("https://chart.yahoo.com/z?t=1d&s=" + symbol)
//                    .centerInside()
//                    .resize(1200, 1200)
//                    .into(stockDayGraph);
    }

    public void setDualPaneView(int position, String symbol) {

        ArrayList<Stock> dataFromFile = utility.getStockData();

        companyNameTextView.setText(utility.getJSONCompanyName(dataFromFile.get(position).getStockJsonString()));
        showStockChart(symbol);
        companyStockPrice.setText(utility.getJSONLastPrice(dataFromFile.get(position).getStockJsonString()));
    }
}



