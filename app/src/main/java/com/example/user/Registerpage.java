package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registerpage extends AppCompatActivity {


    EditText eName,eContact,eUsername, ePassword,eaddress,ecity,estate,pcode,edob,eoccupation,efield;
    Button signUp;
    TextView signIn;
    Spinner spinner;

    ArrayAdapter arrayAdapter;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://user-4d03f-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        eName=findViewById(R.id.name);
        eContact=findViewById(R.id.contact);
        eaddress=findViewById(R.id.address);
        eUsername = findViewById(R.id.email);
        ePassword = findViewById(R.id.password);
        ecity=findViewById(R.id.city);
        estate=findViewById(R.id.state);
        pcode=findViewById(R.id.postalcode);
        edob=findViewById(R.id.dob);
        eoccupation=findViewById(R.id.occupation);
        efield = findViewById(R.id.interest);
        signIn = findViewById(R.id.sign_in);
        signUp =findViewById(R.id.sign_up);


        Spinner spinner = findViewById(R.id.spinner);
        Spinner spinner1 = findViewById(R.id.spinner1);

        String[] options = {"Select Your Occupation","12th", "Diploma", "UG", "PG", "Others"};
        String[] options1 = {"Select Your Interest","Healthcare", "Education", "Animal Welfare", "Environment and Conservation","Social Service","Others"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter1);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedOption = options[position];

                if (selectedOption!=options[0]){
                    eoccupation.setText(selectedOption);
                    Toast.makeText(Registerpage.this,"Occupation is selected",Toast.LENGTH_SHORT).show();
                    if (selectedOption=="others"){
                        eoccupation.setText(null);
                    }
                }
                else {
                     eoccupation.setText(null);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedOption1 = options1[position];

                if (selectedOption1!=options1[0]){
                    efield.setText(selectedOption1);
                    Toast.makeText(Registerpage.this,"area of interest is selected",Toast.LENGTH_SHORT).show();
                    if (selectedOption1=="others"){
                        efield.setText(null);
                    }
                }
                else {
                    efield.setText(null);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });






        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registerpage.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=eName.getText().toString();
                String contact=eContact.getText().toString();
                String address=eaddress.getText().toString();
                String username=eUsername.getText().toString();
                String password=ePassword.getText().toString();
                String city=ecity.getText().toString();
                String state=estate.getText().toString();
                String postal=pcode.getText().toString();
                String dob=edob.getText().toString();
                String occupation=eoccupation.getText().toString();
                String field =efield.getText().toString();


                if (name.isEmpty() || contact.isEmpty() || username.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(Registerpage.this,"Please enter all the details",Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(username)){
                                Toast.makeText(Registerpage.this,"Username already exists",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {

                                databaseReference.child("users").child(username).child("Name").setValue(name);
                                databaseReference.child("users").child(username).child("Contact").setValue(contact);
                                databaseReference.child("users").child(username).child("Address").setValue(address);
                                databaseReference.child("users").child(username).child("Password").setValue(password);
                                databaseReference.child("users").child(username).child("City").setValue(city);
                                databaseReference.child("users").child(username).child("State").setValue(state);
                                databaseReference.child("users").child(username).child("Postal Code").setValue(postal);
                                databaseReference.child("users").child(username).child("Date of birth").setValue(dob);
                                databaseReference.child("users").child(username).child("Occupation").setValue(occupation);
                                databaseReference.child("users").child(username).child("Area of Interest").setValue(field);


                                Toast.makeText(Registerpage.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(Registerpage.this,MainActivity.class);

                                startActivity(intent);
                                finish();





                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Registerpage.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            }
        });

    }
}
