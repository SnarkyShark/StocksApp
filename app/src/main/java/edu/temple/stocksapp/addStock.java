package edu.temple.stocksapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class addStock extends Activity  {

    Button addButton, cancelButton;
    EditText stockEditText;
    ProgressDialog pd;
    AddNewStockInterface mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window);

        addButton = findViewById(R.id.addButton);
        cancelButton = findViewById(R.id.cancelButton);
        stockEditText = findViewById(R.id.stockSymbolEditText);

        // set dimensions for popup window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * .8), (int) (height * .4));

        // exit popup if user cancels
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mListener.onDialogNegativeClick(addStock.this);
                finish();
            }
        });

        // adds stock to portfolio_file
        // TODO: runs into issue where it returns "bad" too soon, then doesn't return correct the first time
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mListener.onDialogPositiveClick(addStock.this);
            }
        });
    }

    public String getCompanyInput(){
        return stockEditText.getText().toString();
    }

    public interface AddNewStockInterface {

        void onDialogPositiveClick(android.support.v4.app.DialogFragment dialogFragment);
        void onDialogNegativeClick(android.support.v4.app.DialogFragment dialogFragment);
    }
}

