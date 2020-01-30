package com.frestau.restaurants.adapter;



import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.frestau.restaurants.R;
import com.frestau.restaurants.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserList extends ArrayAdapter<User> {

    private Activity context;
    //list of users
    List<User> uuusers;

    public UserList(Activity context, List<User> uuusers) {
        super(context, R.layout.layout_item_users, uuusers);
        this.context = context;
        this.uuusers = uuusers;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItemu = inflater.inflate(R.layout.layout_item_users, null, true);
        //initialize
        TextView textViewfirstName = (TextView) listViewItemu.findViewById(R.id.txvuserfName);
        TextView textViewlastName = (TextView) listViewItemu.findViewById(R.id.txvuserlName);
        TextView textviewemail = (TextView) listViewItemu.findViewById(R.id.txvuserEmail);
        TextView textviewnumber = (TextView) listViewItemu.findViewById(R.id.txvuserPhone);
        TextView textviewlatitude = (TextView) listViewItemu.findViewById(R.id.txvuserlatitude);
        TextView textviewlongitude = (TextView) listViewItemu.findViewById(R.id.txvuserlongitude);
        TextView textviewlogintime = (TextView) listViewItemu.findViewById(R.id.txvuserlogintime);
        TextView textViewresnumberB = (TextView) listViewItemu.findViewById( R.id.resnumberB );
        TextView textViewresnumber1 = (TextView) listViewItemu.findViewById( R.id.resnumber1 );
        TextView textViewresnumber2 = (TextView) listViewItemu.findViewById( R.id.resnumber2 );
        TextView textViewresnumber3 = (TextView) listViewItemu.findViewById( R.id.resnumber3 );
        CircleImageView userphoto = (CircleImageView) listViewItemu.findViewById( R.id.imvuserPhoto );


        //getting user at position
        User User = uuusers.get(position);
        //set user name
        textViewfirstName.setText(User.getuserfirstname());
        textViewlastName.setText(User.getuserlastname());
        //set user email
        textviewemail.setText(User.getuseremail());
        //set user mobilenumber
        textviewnumber.setText(User.getuserphone());

        textviewlatitude.setText( User.getuserlatitude() );
        textviewlongitude.setText( User.getuserlongitude() );
        textviewlogintime.setText( User.getuserlogintime() );
        textViewresnumber1.setText( User.getuserresnumber_a() );
        textViewresnumber2.setText( User.getuserresnumber_b() );
        textViewresnumber3.setText( User.getuserresnumber_c() );

        if(User.getuserresnumber().equals( "4" )){
            textViewresnumberB.setText( "( Admin )" );
        }
        else {
            textViewresnumberB.setText( "" );
        }
        if(User.getuserphotoid().equals( "00" )){
            Picasso.get().load(R.drawable.avatar).into(userphoto);
        }
        else {
            Picasso.get().load(User.getuserphotoid()).into(userphoto);
        }

        return listViewItemu;
    }
}