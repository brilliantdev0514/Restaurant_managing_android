package com.frestau.restaurants.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.frestau.restaurants.InventoryActivitya;
import com.frestau.restaurants.R;
import com.frestau.restaurants.model.Itema;

import java.util.List;

public class ItemLista extends ArrayAdapter<Itema> {

    private Activity context;
    //list of users
    List<Itema> itemsa;

    public ItemLista(InventoryActivitya context, List<Itema> itemsa) {
        super(context, R.layout.layout_item_lista, itemsa );
        this.context = context;
        this.itemsa = itemsa;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_item_lista, null, true);
        //initialize
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textviewemail = (TextView) listViewItem.findViewById(R.id.textviewemail);
        TextView textviewnumber = (TextView) listViewItem.findViewById(R.id.textviewnumber);

        //getting user at position
        Itema Itema = itemsa.get(position);
        //set user name
        textViewName.setText( Itema.getitemnumber());
        //set user email
        textviewemail.setText( Itema.getitemname());
        //set user mobilenumber
        textviewnumber.setText( Itema.getitemquality());

        return listViewItem;
    }
}