package com.untitledapps.meetasweedt;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.*;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;


/**
 * Created by Ajla on 2016-10-11.
 */

public class MultiSpinner extends Spinner implements OnMultiChoiceClickListener, OnCancelListener {


    ArrayAdapter<String> adapter;
    String[] itemlist = null;
    boolean[] selected = null;


    public void onClick(DialogInterface dialog, int items, boolean Checked) {
        if (selected != null){
            if(items < selected.length) {
                selected[items] = Checked;

                adapter.clear();
                adapter.add(buildSelectedItemStr());
            }
        } else {
            throw new IllegalArgumentException(
                    "'Items' is out of bound.");
        }
    }


    ///contructors
    public MultiSpinner(Context stuff){
        super(stuff);
        adapter = new ArrayAdapter<String>(stuff, android.R.layout.simple_spinner_item);
        super.setAdapter(adapter);
    }

    public MultiSpinner(Context stuff, AttributeSet aset){
        super(stuff, aset);
        adapter = new ArrayAdapter<String>(stuff, android.R.layout.simple_spinner_item);
        super.setAdapter(adapter);
    }


    ///NOTE: class is costumized for string arrays only, not string lists
    public void setItem(String[] items){
        itemlist = items;
        int length = itemlist.length;
        selected = new boolean[length];

        adapter.clear();
        adapter.add(itemlist[0]);
        Arrays.fill(selected, false);
    }


    

    private String buildSelectedItemStr() {
        boolean foundOne = false;
        StringBuilder strngbf = new StringBuilder();

        for (int i = 0; i < itemlist.length; ++i) {
            if (selected[i] && foundOne) {
                strngbf.append("; ");
            }
            if (selected[i]) {
                foundOne = true;
            }
            strngbf.append(itemlist[i]);
        }
        return strngbf.toString();
    }


    @Override
    public void onCancel(DialogInterface dialog) {

    }








}
