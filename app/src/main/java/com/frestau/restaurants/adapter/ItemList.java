package com.frestau.restaurants.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.frestau.restaurants.InventoryActivity;
import com.frestau.restaurants.R;
import com.frestau.restaurants.model.Item;

import java.util.List;

public class ItemList extends ArrayAdapter<Item> {

    private Activity context;
    //list of users
    List<Item> items;

    public ItemList(InventoryActivity context, List<Item> items) {
        super(context, R.layout.layout_item_list, items );
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_item_list, null, true);
        //initialize
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textviewemail = (TextView) listViewItem.findViewById(R.id.textviewemail);
        TextView textviewnumber = (TextView) listViewItem.findViewById(R.id.textviewnumber);

        //getting user at position
        Item Item = items.get(position);
        //set user name
        textViewName.setText( Item.getitemnumber());
        //set user email
        textviewemail.setText( Item.getitemname());
        //set user mobilenumber
        textviewnumber.setText( Item.getitemquality());

        return listViewItem;
    }
}