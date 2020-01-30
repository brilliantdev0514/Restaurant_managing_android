package com.frestau.restaurants.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.frestau.restaurants.InventoryActivityb;
import com.frestau.restaurants.R;
import com.frestau.restaurants.model.Itemb;

import java.util.List;

public class ItemListb extends ArrayAdapter<Itemb> {

    private Activity context;
    //list of users
    List<Itemb> itemsb;

    public ItemListb(InventoryActivityb context, List<Itemb> itemsb) {
        super(context, R.layout.layout_item_listb, itemsb );
        this.context = context;
        this.itemsb = itemsb;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_item_listb, null, true);
        //initialize
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textviewemail = (TextView) listViewItem.findViewById(R.id.textviewemail);
        TextView textviewnumber = (TextView) listViewItem.findViewById(R.id.textviewnumber);

        //getting user at position
        Itemb Itemb = itemsb.get(position);
        //set user name
        textViewName.setText( Itemb.getitemnumber());
        //set user email
        textviewemail.setText( Itemb.getitemname());
        //set user mobilenumber
        textviewnumber.setText( Itemb.getitemquality());

        return listViewItem;
    }
}