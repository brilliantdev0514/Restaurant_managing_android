package com.frestau.restaurants;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class FragmentheaderActivity extends AppCompatActivity {


  //  private TextView mPlayer;
    private TextView titleView;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_main);

   //     mPlayer =  findViewById(R.id.textemail);
    //    titleView = findViewById(R.id.titleview);


        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView titleView =  findViewById(R.id.titleview);
        titleView.setText("Your Text Here");


     //   titleView.setText("mmmmmm");

        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final String userId = user.getUid();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
      //  final DatabaseReference mRef = mDatabase.child("users").child(userId);



//        currentUser = auth.getCurrentUser();
//        String uid = currentUser.getUid();

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("uuusers").child(userId);




        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap hashUser = (HashMap) dataSnapshot.getValue();
                if (hashUser != null) {
//                    String firstname = hashUser.get("First Name").toString();
//                    String lastname = hashUser.get("Last Name").toString();


                   // titleView.setText(firstname + " " + lastname);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FragmentheaderActivity.this, databaseError.getMessage() , Toast.LENGTH_SHORT).show();

            }
        });



    }

}
