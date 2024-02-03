package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class orglogin extends AppCompatActivity {

    TextInputEditText Username, editTextPassword;
    Button signIn;
    TextView signUp;

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://user-4d03f-default-rtdb.firebaseio.com/");


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(orglogin.this, Start.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orglogin);


        Username = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        signIn = findViewById(R.id.sign_in);
        signUp = findViewById(R.id.sign_up);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(orglogin.this, registerpage2.class);
                startActivity(intent);
                finish();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eUsername=Username.getText().toString();
                String ePassword=editTextPassword.getText().toString();
                if (eUsername.isEmpty()){
                    Toast.makeText(orglogin.this, "Enter username", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("Org").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(eUsername)){
                                String Password=snapshot.child(eUsername).child("Password").getValue(String.class);
                                if (Password.equals(ePassword)){
                                  //  String Name=snapshot.child(eUsername).child("Name").getValue(String.class);
                                    Intent intent=new Intent(orglogin.this, Homepage.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(orglogin.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });


    }
}