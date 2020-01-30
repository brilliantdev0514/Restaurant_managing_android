package com.frestau.restaurants.ui.send;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.frestau.restaurants.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class SendFragment extends Fragment {

    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;

    private FirebaseAuth mAuth;



    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate( R.layout.fragment_send, container, false );

        usersList = (ListView) root.findViewById( R.id.usersList );
        noUsersText = (TextView) root.findViewById( R.id.noUsersText );


        mAuth= FirebaseAuth.getInstance();

        final FirebaseUser user = mAuth.getCurrentUser();
        final String userId = user.getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference mRef = mDatabase.child("uuusers").child(userId);

        pd = new ProgressDialog( getActivity() );
        pd.setMessage( "Loading..." );
        pd.show();

        final Intent i = new Intent( Intent.ACTION_SEND );

        pd.dismiss();

        ValueEventListener eventListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap hashUser = (HashMap) dataSnapshot.getValue();
                if (hashUser != null) {

                    String firstname = hashUser.get("userfirstname").toString();
                    String lastname = hashUser.get("userlastname").toString();

                    i.setType( "message/rfc822" );
                    i.putExtra( Intent.EXTRA_EMAIL, new String[]{"fordevelop0401@gmail.com"} );
                    i.putExtra( Intent.EXTRA_SUBJECT, "Report from " + firstname +" "+ lastname );// user first and last name
//            i.putExtra(Intent.EXTRA_TEXT   , "kkkkkkkk@mail.ru");

                    }
                try {
                    startActivity( Intent.createChooser( i, "Send mail..." ) );
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText( getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT ).show();
                }
                //Toast.makeText( getActivity(), "Your email has been sended.", Toast.LENGTH_SHORT ).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        mRef.addListenerForSingleValueEvent(eventListener);

        return root;
    }
}

