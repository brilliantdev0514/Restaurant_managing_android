package com.frestau.restaurants.ui.inventory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.frestau.restaurants.InventoryActivity;
import com.frestau.restaurants.InventoryActivitya;
import com.frestau.restaurants.InventoryActivityb;
import com.frestau.restaurants.MainActivity;
import com.frestau.restaurants.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class InventoryFragment extends Fragment {

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_inventory, container, false);

        final Button resbtn = (Button) root.findViewById(R.id.resBtn);
        final Button resbtna = (Button) root.findViewById(R.id.resBtna);
        final Button resbtnb = (Button) root.findViewById(R.id.resBtnb);
        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final String userId = user.getUid();

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("uuusers").child(userId);




        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap hashUser = (HashMap) dataSnapshot.getValue();
                if (hashUser != null) {
                    final String bbb = hashUser.get("userresnumber").toString();
                    final String res_a = hashUser.get("userresnumber_a").toString();
                    final String res_b = hashUser.get("userresnumber_b").toString();
                    final String res_c = hashUser.get("userresnumber_c").toString();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            if (bbb.equals( "4" )){
                                resbtn.setEnabled( true );
                                resbtna.setEnabled( true );
                                resbtnb.setEnabled( true );

                            } else {
                                resbtn.setEnabled( false );
                                resbtna.setEnabled( false );
                                resbtnb.setEnabled( false );
                                if (res_a.equals( "1" )){
                                    resbtn.setEnabled( true );
                                }
                                if (res_b.equals( "2" )){
                                    resbtna.setEnabled( true );
                                }
                                if (res_c.equals( "3" )){
                                    resbtnb.setEnabled( true );
                                }
                            }
                        }

                    });
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText( getActivity(), databaseError.getMessage() , Toast.LENGTH_SHORT).show();

            }
        });

        resbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), InventoryActivity.class));
            }
        } );
        resbtna.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), InventoryActivitya.class));
            }
        } );
        resbtnb.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), InventoryActivityb.class));
            }
        } );

        return root;
    }
}