package com.frestau.restaurants.ui.users;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.frestau.restaurants.R;
import com.frestau.restaurants.adapter.UserList;
import com.frestau.restaurants.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class UsersFragment extends Fragment {
    ProgressDialog pd;
    List<User> uuusers;
    ListView listViewUsers;
    DatabaseReference rootRef;
    private TextView resNummm,textview1,textview2,textview3;
    android.app.AlertDialog.Builder alertdialogbuilder;

    String[] AlertDialogItems = new String[]{" First Restaurant "," Second Restaurant "," Third Restaurant "};
    String[] Resnumber = new String[]{"0","1","2"};
    List<String> ItemsIntoList;

    boolean[] Selectedtruefalse = new boolean[]{
            false,
            false,
            false,
            false
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_users, container, false);


        rootRef = FirebaseDatabase.getInstance().getReference("uuusers");
        listViewUsers = (ListView) root.findViewById(R.id.loginusers);
        resNummm = root.findViewById( R.id.resnummm );
        textview1 = (TextView)root.findViewById(R.id.textView1);
        textview2 = (TextView)root.findViewById(R.id.textView2);
        textview3 = (TextView)root.findViewById(R.id.textView3);


        uuusers = new ArrayList<>();

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.show();

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

                    if (bbb.equals( "4" )){


                        // grant permission for restaurant if I am an admin...

                        // if myRes = 4

                        listViewUsers.setClickable(true);
                        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
                                //Do some
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                builder1.setMessage("Manager Permission");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Allow",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                User Users = uuusers.get( position );
                                                CreateAlertDialogWithRadioButtonGroup(Users.getuserid(),Users.getuseremail(),Users.getuserfirstname(),Users.getuserlastname(),Users.getuserphone(),Users.getuserpassword(),Users.getuserlatitude(),Users.getuserlongitude(),Users.getuserlogintime(),Users.getuserphotoid(),Users.getuserresnumber(),Users.getuserresnumber_a(),Users.getuserresnumber_b(),Users.getuserresnumber_c()) ;

                                            }
                                        });
                                builder1.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                        builder1.setTitle("Are you sure you want to delete "+uuusers.get( position ).getuserfirstname()+" "+uuusers.get( position ).getuserlastname()+"?");
                                        builder1.setCancelable(true);

                                        builder1.setPositiveButton(
                                                "Yes",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {

                                                        dialog.cancel();
                                                        User User = uuusers.get( position );
                                                        CallDeleteDialog(User.getuserid());

                                                    }
                                                });

                                        builder1.setNegativeButton(
                                                "No",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });

                                        AlertDialog alert11 = builder1.create();
                                        alert11.setIcon(android.R.drawable.ic_dialog_alert);
                                        alert11.show();
                                    }
                                });

                                builder1.setNegativeButton(
                                        "Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                                return ;
                            }});

                    }
                    else
                        {
                        listViewUsers.setClickable(false);
                        }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText( getActivity(), databaseError.getMessage() , Toast.LENGTH_SHORT).show();

            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        rootRef.addValueEventListener(mValueEventListener);
    }

    ValueEventListener mValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            //clearing the previous user list
            uuusers.clear();
            //getting all nodes
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                //getting user from firebase console
                User User = postSnapshot.getValue( User.class);
//                    User useridd = postSnapshot.getValue(User.getuserid());
                //adding user to the list
                uuusers.add( User );
//                    userList.add( useridd );
            }
            pd.dismiss();
            //creating Userlist adapter
            UserList UserAdapter = new UserList(getActivity(), uuusers );
            //attaching adapter to the listview
            listViewUsers.setAdapter(UserAdapter);

        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public void onPause() {
        super.onPause();
        rootRef.removeEventListener( mValueEventListener );
    }

    private void CallDeleteDialog(final String userid){
        deleteItem(userid);
    }

    private boolean deleteItem(String id) {
        DatabaseReference DeleteReference = FirebaseDatabase.getInstance().getReference("uuusers").child(id);
        //removing Item
        DeleteReference.removeValue();
        Toast.makeText(getActivity(), "User Deleted", Toast.LENGTH_LONG).show();
        return true;
    }

    //TODO; alert dialog with multiselect Items.

    public void CreateAlertDialogWithRadioButtonGroup(final String userid, final String useremail, final String userfirstname, final String userlastname, final String userphone, final String userpassword, final String userlatitude, final String userlongitude, final String userlogintime, final String userphotoid, final String userresnumber, final String userresnumber_a, final String userresnumber_b, final String userresnumber_c){
        resNummm.setText("");

        alertdialogbuilder = new android.app.AlertDialog.Builder(getActivity());

        ItemsIntoList = Arrays.asList(Resnumber);

        alertdialogbuilder.setMultiChoiceItems(AlertDialogItems, Selectedtruefalse, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if (!isChecked){
                    if (!ItemsIntoList.contains( position )){
                        //ItemsIntoList.add( position );
                        switch (position){
                            case 0:
                                textview1.setText( "0" );
                                break;
                            case 1:
                                textview2.setText( "0" );
                                break;
                            case 2:
                                textview3.setText( "0" );
                                break;
                        }

                    }
                }
            }
        });

        alertdialogbuilder.setCancelable(false);

        alertdialogbuilder.setTitle("Select Restaurants Here");

        alertdialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int a = 0;
                while(a < Selectedtruefalse.length)
                {
                    boolean value = Selectedtruefalse[a];

                    if(value){
                        resNummm.setText(ItemsIntoList.get(a));

                            String item = (String) resNummm.getText();
                            switch (item){
                                case "0":
                                    //Toast.makeText( getActivity(),"1",Toast.LENGTH_SHORT ).show();
                                    textview1.setText( "1" );
                                    break;
                                case "1":
                                    //Toast.makeText( getActivity(),"1",Toast.LENGTH_SHORT ).show();
                                    textview2.setText( "2" );
                                    break;
                                case "2":
                                    //Toast.makeText( getActivity(),"1",Toast.LENGTH_SHORT ).show();
                                    textview3.setText( "3" );
                                    break;
                            }
                    }

                    a++;
                }
                //String userresnumber = "0";
                String userresnumber_a = textview1.getText().toString().trim();
                String userresnumber_b = textview2.getText().toString().trim();
                String userresnumber_c = textview3.getText().toString().trim();
                updateresnumber(userid,useremail,  userfirstname,  userlastname,  userphone,  userpassword, userlatitude, userlongitude, userlogintime, userphotoid, userresnumber, userresnumber_a, userresnumber_b, userresnumber_c);

            }


        });

        alertdialogbuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        android.app.AlertDialog dialog = alertdialogbuilder.create();

        dialog.show();

        ////////////////////////////
    }
    private boolean updateresnumber(String id,String useremail, String userfirstname, String userlastname, String userphone, String userpassword,String userlatitude,String userlongitude,String userlogintime,String userphotoid,String userresnumber, String userresnumber_a, String userresnumber_b, String userresnumber_c) {
        DatabaseReference UpdateReference = FirebaseDatabase.getInstance().getReference("uuusers").child(id);
        //removing Item
        User Item = new User(id, useremail,  userfirstname,  userlastname,  userphone,  userpassword, userlatitude, userlongitude, userlogintime, userphotoid, userresnumber,  userresnumber_a,  userresnumber_b,  userresnumber_c);
        //update  Item  to firebase
        UpdateReference.setValue( Item );
        return true;
    }
}


