package com.frestau.restaurants;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailTV,phoneTV,passwordTV,nameTVF,nameTVL,repasswordTV;
    private Button regBtn,loginbtn;
    private ProgressBar progressBarS;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView resNum;

    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        loginbtn=findViewById(R.id.btn_login1);

        mAuth=FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        initializeUI();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerNewUser();

            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                //              setContentView(R.layout.activity_login);
            }
        });
    }

    private void registerNewUser() {
        progressBarS.setVisibility(View.VISIBLE);


        final String email,Pnumber, password, repassword, nameF,nameL;

        email=emailTV.getText().toString();
        Pnumber=phoneTV.getText().toString();
        password=passwordTV.getText().toString();
        nameF=nameTVF.getText().toString();
        nameL=nameTVL.getText().toString();
        repassword=repasswordTV.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email.", Toast.LENGTH_LONG).show();
            progressBarS.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(Pnumber)) {
            Toast.makeText(getApplicationContext(), "Please enter phone number.", Toast.LENGTH_LONG).show();
            progressBarS.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password.", Toast.LENGTH_LONG).show();
            progressBarS.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(nameF)) {
            Toast.makeText(getApplicationContext(), "Please enter your first name.", Toast.LENGTH_LONG).show();
            progressBarS.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(nameL)) {
            Toast.makeText(getApplicationContext(), "Please enter your last name.", Toast.LENGTH_LONG).show();
            progressBarS.setVisibility(View.GONE);
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            progressBarS.setVisibility(View.GONE);
            return;
        }
        if (!password.equals(repassword)) {
            Toast.makeText(getApplicationContext(), "Password does not match.", Toast.LENGTH_LONG).show();
            progressBarS.setVisibility(View.GONE);
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult>task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Sign up successful !", Toast.LENGTH_LONG).show();
                            enteradminpw();
                            progressBarS.setVisibility(View.GONE);
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userId = user.getUid();

                            // write the user data to database
                            DatabaseReference mRef = mDatabase.child("uuusers").child(userId);

                            mRef.child("userfirstname").setValue(nameTVF.getText().toString());
                            mRef.child("userlastname").setValue(nameTVL.getText().toString());
                            mRef.child("useremail").setValue(emailTV.getText().toString());
                            mRef.child("userphone").setValue(phoneTV.getText().toString());
                            mRef.child("userpassword").setValue(passwordTV.getText().toString());
                            mRef.child( "userid" ).setValue( userId );
                            mRef.child("userlatitude").setValue("0.0");
                            mRef.child("userlongitude").setValue("0.0");
                            mRef.child("userlogintime").setValue("00:00");
                            mRef.child("userphotoid").setValue("00");
                            mRef.child("userresnumber").setValue("0");
                            mRef.child("userresnumber_a").setValue("0");
                            mRef.child("userresnumber_b").setValue("0");
                            mRef.child("userresnumber_c").setValue("0");

                            //TODO;firebase database write;

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Sign up failed! Please try again.", Toast.LENGTH_LONG).show();

                            progressBarS.setVisibility(View.GONE);


                        }
                    }
                });

        empty();
    }

    private void initializeUI () {
        emailTV=findViewById(R.id.email);
        phoneTV=findViewById(R.id.phonenumber);
        nameTVF=findViewById(R.id.edtFirstname);
        nameTVL=findViewById(R.id.edtLastname);
        passwordTV=findViewById(R.id.password);
        repasswordTV=findViewById(R.id.input_reEnterPassword);
        regBtn=findViewById(R.id.signupBtn);
        progressBarS=findViewById(R.id.progressBar_sign);
        resNum=findViewById( R.id.resnum );

    }
    public void empty(){

        nameTVF.setVisibility(View.INVISIBLE);
        nameTVL.setVisibility(View.INVISIBLE);
        emailTV.setVisibility(View.INVISIBLE);
        phoneTV.setVisibility(View.INVISIBLE);
        passwordTV.setVisibility(View.INVISIBLE);
        repasswordTV.setVisibility(View.INVISIBLE);
        repasswordTV.setVisibility(View.INVISIBLE);

    }
    public void enteradminpw() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(SignUpActivity.this);
        edittext.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        alert.setTitle("Enter admin key."); // enter admin key

        alert.setView(edittext);

        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        final DatabaseReference mRef = mDatabase.child("uuusers").child(userId);
        final DatabaseReference aaRef = mDatabase.child("adminkey");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                final String enteredAdminKey = edittext.getText().toString();

                aaRef.addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String aaa = dataSnapshot.getValue().toString();
                        if (enteredAdminKey.equals(aaa)) {
                            // you are admin
                            resNum.setText( "4" );

                            mRef.child("userresnumber").setValue(resNum.getText().toString());
                            Toast.makeText(SignUpActivity.this, "Password is correct, You are admin.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();

                        } else {
                            // if you are not admin
                            //Toast.makeText(SignUpActivity.this, "You entered an invalid key. Please try again.", Toast.LENGTH_SHORT).show();
                            resNum.setText( "0" );
                            Toast.makeText(SignUpActivity.this, "You are not admin, sign up to general user.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                // we set it as defaul value 111111

            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                resNum.setText( "0" );
                mRef.child("userresnumber").setValue("0");
                mRef.child("userresnumber_a").setValue("0");
                mRef.child("userresnumber_b").setValue("0");
                mRef.child("userresnumber_c").setValue("0");
                Toast.makeText(SignUpActivity.this, "You are general user.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
                // what ever you want to do with No option.
            }
        });
        alert.show();
    }

    @Override
    public void onBackPressed() {

        if (exit) {
            finish();
        } else {
            Toast.makeText(this, this.getString(R.string.press_back_again_to_exit), Toast.LENGTH_SHORT).show();
            exit = true;

            new Handler().postDelayed( new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3000);
        }

    }
}
