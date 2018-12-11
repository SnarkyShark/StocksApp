package edu.temple.stocksapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

public class AddNewStockDialog extends DialogFragment {

    AddNewStockInterface mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.add_stock_dialog, null));
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mListener.onDialogPositiveClick(AddNewStockDialog.this);

                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mListener.onDialogNegativeClick(AddNewStockDialog.this);

                    }
                });
        return builder.show();
    }

    public interface AddNewStockInterface {

        void onDialogPositiveClick(DialogFragment dialogFragment);
        void onDialogNegativeClick(DialogFragment dialogFragment);
    }

    /*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (AddNewStockInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AddNewStockInterface");
        }
    } */
}