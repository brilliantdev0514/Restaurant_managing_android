package com.frestau.restaurants;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.frestau.restaurants.adapter.ItemList;
import com.frestau.restaurants.model.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InventoryActivity extends AppCompatActivity {



    private EditText editTxtNumber, editTxtName, editTxtQuality;
    ProgressDialog pd;
    Button buttonAddUser;
    ListView listViewUsers;
    List<Item> items;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);



        // method for find ids of views
        databaseReference = FirebaseDatabase.getInstance().getReference("items");

        editTxtNumber = (EditText)findViewById(R.id.editTextNumber);
        editTxtName = (EditText) findViewById(R.id.editTextName);
        editTxtQuality = (EditText) findViewById(R.id.editTextQuality);
        listViewUsers = (ListView) findViewById(R.id.listViewItems);
        buttonAddUser = (Button) findViewById(R.id.buttonAddUser);
        //list for store objects of user
        items = new ArrayList<>();
        pd = new ProgressDialog(InventoryActivity.this);
        pd.setMessage("Loading...");
        pd.show();

        // to maintian click listner of views
        initListner();

    }

    private void initListner() {
        //adding an onclicklistener to button
        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addUser()
                //the method is defined below
                //this method is actually performing the write operation
                addItem();
            }
        });

        // list item click listener
        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Item Item = items.get(i);
                CallUpdateAndDeleteDialog( Item.getitemid(), Item.getitemnumber(), Item.getitemname(), Item.getitemquality());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous Item list
                items.clear();

                //getting all nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting Item from firebase console
                    Item Item = postSnapshot.getValue( Item.class);
                    //adding Item to the list
                    items.add( Item );
                }
                Collections.sort(items, new Comparator<Item>() {

                    @Override
                    public int compare(Item o1, Item o2) {
                        // TODO Auto-generated method stub
                        return o1.getitemnumber().compareTo(o2.getitemnumber());
                    }
                });
                //creating Userlist adapter
                ItemList UserAdapter = new ItemList(InventoryActivity.this, items );
                //attaching adapter to the listview
                listViewUsers.setAdapter(UserAdapter);
                pd.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void CallUpdateAndDeleteDialog(final String itemid, String inumber, final String iname, String iquality) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);
        //Access Dialog views
        final EditText updateTextinumber = (EditText) dialogView.findViewById(R.id.updateitemnumber);
        final EditText updateTextiname = (EditText) dialogView.findViewById(R.id.updateitemname);
        final EditText updateTextiquality = (EditText) dialogView.findViewById(R.id.updateitemquality);
        updateTextinumber.setText(inumber);
        updateTextiname.setText(iname);
        updateTextiquality.setText(iquality);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateUser);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteUser);
        //username for set dialog title
        dialogBuilder.setTitle(inumber);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        // Click listener for Update data
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inumber = updateTextinumber.getText().toString().trim();
                String iname = updateTextiname.getText().toString().trim();
                String iquality = updateTextiquality.getText().toString().trim();
                //checking if the value is provided or not Here, you can Add More Validation as you required

                if (!TextUtils.isEmpty(inumber)) {
                    if (!TextUtils.isEmpty(iname)) {
                        if (!TextUtils.isEmpty(iquality)) {
                            //Method for update data
                            updateItem(itemid, inumber, iname, iquality);
                            b.dismiss();
                        }
                    }
                }

            }
        });

        // Click listener for Delete data
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Method for delete data
                deleteItem(itemid);
                b.dismiss();
            }
        });
    }

    private boolean updateItem(String id, String inumber, String iname, String iquality) {
        //getting the specified Item reference
        DatabaseReference UpdateReference = FirebaseDatabase.getInstance().getReference("items").child(id);
        Item Item = new Item(id, inumber, iname, iquality);
        //update  Item  to firebase
        UpdateReference.setValue( Item );
        Toast.makeText(getApplicationContext(), "Item Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteItem(String id) {
        //getting the specified Item reference
        DatabaseReference DeleteReference = FirebaseDatabase.getInstance().getReference("items").child(id);
        //removing Item
        DeleteReference.removeValue();
        Toast.makeText(getApplicationContext(), "Item Deleted", Toast.LENGTH_LONG).show();
        return true;
    }


    private void addItem() {


        //getting the values to save
        String inumber = editTxtNumber.getText().toString().trim();
        String iname = editTxtName.getText().toString().trim();
        String iquality = editTxtQuality.getText().toString().trim();


        //checking if the value is provided or not Here, you can Add More Validation as you required

        if (!TextUtils.isEmpty(inumber)) {
            if (!TextUtils.isEmpty(iname)) {
                if (!TextUtils.isEmpty(iquality)) {

                    //it will create a unique id and we will use it as the Primary Key for our Item
                    String id = databaseReference.push().getKey();
                    //creating an Item Object
                    Item Item = new Item(id, inumber, iname, iquality);
                    //Saving the Item
                    databaseReference.child(id).setValue( Item );

                    editTxtNumber.setText("");
                    editTxtQuality.setText("");
                    editTxtName.setText("");
                    Toast.makeText(this, "Item added", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Please enter a Quality", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Please enter a Name", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Please enter a Number", Toast.LENGTH_LONG).show();
        }
    }

}