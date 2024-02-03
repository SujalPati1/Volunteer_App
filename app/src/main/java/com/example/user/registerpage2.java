package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class registerpage2 extends AppCompatActivity {

    EditText orgname,lc,orgaddress,ocontact,otype,ouser,opass,copass;
    Button signUp;
    TextView signIn;
    Spinner spinner;

    ArrayAdapter arrayAdapter;

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://user-4d03f-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage2);

        orgname=findViewById(R.id.name);
        lc=findViewById(R.id.license);
        orgaddress=findViewById(R.id.address);
        ocontact=findViewById(R.id.contact);
        otype=findViewById(R.id.interest);
        ouser=findViewById(R.id.email);
        opass=findViewById(R.id.password);
        copass=findViewById(R.id.c_password);
        signIn = findViewById(R.id.sign_in);
        signUp =findViewById(R.id.sign_up);

        Spinner spinner = findViewById(R.id.spinner);

        String[] options = {"Select Type Of Organisation","Healthcare", "Education", "Animal Welfare", "Environment and Conservation","Social Service","Others"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = options[position];

                if (selectedOption!=options[0]){
                    otype.setText(selectedOption);
                    Toast.makeText(registerpage2.this, "type is selected", Toast.LENGTH_SHORT).show();
                    if (selectedOption=="others"){
                        otype.setText(null);
                    }
                }
                else {
                    otype.setText(null);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registerpage2.this,orglogin.class);
                startActivity(intent);
                finish();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=orgname.getText().toString();
                String license=lc.getText().toString();
                String address=orgaddress.getText().toString();
                String contact=ocontact.getText().toString();
                String type=otype.getText().toString();
                String user=ouser.getText().toString();
                String password=opass.getText().toString();
                String cpassword=copass.getText().toString();

                if(name.isEmpty() || contact.isEmpty() || user.isEmpty() || password.isEmpty()){
                    Toast.makeText(registerpage2.this, "Please Enter all the details", Toast.LENGTH_SHORT).show();
                }
                else if (password.equals(cpassword)){
                    databaseReference.child("Org").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(user)){
                                Toast.makeText(registerpage2.this, "User already exits", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                databaseReference.child("Org").child(user).child("Name").setValue(contact);
                                databaseReference.child("Org").child(user).child("License").setValue(license);
                                databaseReference.child("Org").child(user).child("Address").setValue(address);
                                databaseReference.child("Org").child(user).child("Type of org").child(type);
                                databaseReference.child("Org").child(user).child("Password").setValue(password);

                                Toast.makeText(registerpage2.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(registerpage2.this,orglogin.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(registerpage2.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(registerpage2.this, "Password not match", Toast.LENGTH_SHORT).show();
                }



            }
        });




    }
}