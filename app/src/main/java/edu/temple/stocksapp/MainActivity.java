package edu.temple.stocksapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.temple.stocksapp.utilities.GetStockJSONAsync;
import edu.temple.stocksapp.utilities.Stock;
import edu.temple.stocksapp.utilities.StockUpdateService;
import edu.temple.stocksapp.utilities.Utility;

public class MainActivity extends AppCompatActivity
        implements addStock.AddNewStockInterface,
        PortfolioFragment.PortfolioInterface {


    FloatingActionButton launchAddButton;
    PortfolioFragment portfolioFragment;
    DetailsFragment detailsFragment;
    boolean twoPanes;
    String newStockJson = null;
    Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        launchAddButton = findViewById(R.id.floatingActionButton);
        utility = new Utility(this);
        twoPanes = (findViewById(R.id.stocks_details_fragment) != null);
        portfolioFragment = new PortfolioFragment();
        detailsFragment = new DetailsFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.stocks_nav_fragment, portfolioFragment)
                .commit();

        launchAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewStockDialog newStockDialog = new AddNewStockDialog();
                newStockDialog.show(getSupportFragmentManager(), "AddNewStockDialog");
            }
        });

        Intent stockServiceIntent = new Intent(this, StockUpdateService.class);
        startService(stockServiceIntent);

        if(twoPanes) {
            getSupportFragmentManager().beginTransaction()
            .add(R.id.stocks_details_fragment, detailsFragment)
            .commit();
        }
    }

    @Override
    public void onDialogPositiveClick(android.support.v4.app.DialogFragment dialogFragment) {

        EditText stockEditText = (EditText) dialogFragment.getDialog().findViewById(R.id.stock_editText);
        String userInputStock = stockEditText.getText().toString();
        getNewStockJson(userInputStock.toUpperCase());
    }

    @Override
    public void onDialogNegativeClick(android.support.v4.app.DialogFragment dialogFragment) {
    }

    @Override
    public void addStockToList(Stock stock) {
        portfolioFragment.addStock(stock);
    }

    @Override
    public void stockItemSelected(int position, String symbol) {

        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("symbol", symbol);

        if(!twoPanes) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.stocks_nav_fragment, detailsFragment)
                    .addToBackStack(null).commit();
            detailsFragment.setArguments(bundle);

            getSupportFragmentManager().executePendingTransactions();
        }
        detailsDualPane(position, symbol);
    }

    public void getNewStockJson(final String stockSymbol) {

        new GetStockJSONAsync() {

            @Override
            protected void onPostExecute(String jsonString) {

                Utility utility = new Utility(getApplicationContext());
                newStockJson = jsonString;

                if(utility.isValidSymbol(jsonString) == true) {
                    Stock stock = new Stock(stockSymbol, newStockJson);
                    addStockToList(stock);
                }else{
                    Toast.makeText(getApplicationContext(), getResources().
                            getString(R.string.invalid_symbol), Toast.LENGTH_SHORT).show();
                }
                newStockJson = null;
            }
        }.execute(stockSymbol);
    }

    public void detailsDualPane(int position, String symbol) {
        detailsFragment.setDualPaneView(position, symbol);
    }

}
