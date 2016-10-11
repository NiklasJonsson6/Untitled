package com.untitledapps.meetasweedt;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.google.android.gms.maps.model.internal.IPolylineDelegate;


/**
 * Created by Ajla on 2016-10-11.
 */

public class MultiSpinner extends Spinner implements OnMultiChoiceClickListener {
///NOTE: class is costumized for string arrays only, not string lists

    ArrayAdapter<String> adapter;
    String[] itemlist = null;
    boolean[] selected = null;


    public void onClick(DialogInterface dialog, int items, boolean Checked) {
        if (selected != null) {
            if (items < selected.length) {
                selected[items] = Checked;

                adapter.clear();
                adapter.add(getSelectedItemStr());
            }
        } else {
            throw new IllegalArgumentException(
                    "'Items' is out of bound.");
        }
    }


    ///constructors
    public MultiSpinner(Context stuff) {
        super(stuff);
        adapter = new ArrayAdapter<String>(stuff, android.R.layout.simple_spinner_item);
        super.setAdapter(adapter);
    }

    public MultiSpinner(Context stuff, AttributeSet aset) {
        super(stuff, aset);
        adapter = new ArrayAdapter<String>(stuff, android.R.layout.simple_spinner_item);
        super.setAdapter(adapter);
    }




    //set adapter items using String[] to spinner
    public void setItem(String[] items) {
        itemlist = items;
        int length = itemlist.length;
        selected = new boolean[length];

        adapter.clear();
        adapter.add(itemlist[0]);
        Arrays.fill(selected, false);
    }


    //set selected items on this adapter using String[] to this spinner
    public void setSelection(String[] sel){
        for(int i = 0; i<sel.length; i++){
            for(int j=0; j<itemlist.length; j++){
                if(itemlist[j].equals(sel[i])){
                    selected[j] = true;
                }
            }
        }
    }


    //get selected items as a string
    public List<String> getSelectedStrings(){
        List<String> sel = new LinkedList<String>();

        for(int i = 0; i < itemlist.length; i++){
            if(selected[i])
                sel.add(itemlist[i]);
        }

        return sel;
    }



    //get selected indexes as a list of integers
    public List<Integer> getSelectedIndicies(){
        List<Integer> sel = new LinkedList<Integer>();

        for (int i = 0; i < itemlist.length; i++){
            if(selected[i])
                sel.add(i);
        }

        return sel;
    }



    public String getSelectedItemStr() {
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
    public boolean performClick(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMultiChoiceItems(itemlist, selected, this);
        builder.show();

        return true;
    }

    @Override
    public void setAdapter(SpinnerAdapter spadapter) {
        throw new RuntimeException(
                "setAdapter is not supported by MultiSelectSpinner.");
    }



}
