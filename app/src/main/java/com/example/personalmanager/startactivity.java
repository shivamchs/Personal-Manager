package com.example.personalmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.sql.ResultSet;
import java.util.ResourceBundle;

public class startactivity extends AppCompatActivity {
    EditText name,email,phone,address,dob;
    private Button logout,adddata,adddata2,adddata3;
    FirebaseAuth auth;
    FirebaseFirestore store;
    String  userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startactivity);
        phone=findViewById(R.id.phone2);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address1);
        email=findViewById(R.id.email2);
        dob=findViewById(R.id.dob1);
        logout=findViewById(R.id.logout);
        adddata=findViewById(R.id.data);
        adddata2=findViewById(R.id.data3);
        adddata3=findViewById(R.id.data4);


        auth=FirebaseAuth.getInstance();
        store=FirebaseFirestore.getInstance();

        userId=auth.getCurrentUser().getUid();
        DocumentReference documentReference=store.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                phone.setText(documentSnapshot.getString("phone"));
                name.setText(documentSnapshot.getString("name"));
                email.setText(documentSnapshot.getString("email"));
                address.setText(documentSnapshot.getString("address"));
                dob.setText(documentSnapshot.getString("dob"));

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(startactivity.this,MainActivity.class));
                finish();
            }
        });
        adddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(startactivity.this,adddata.class));
                finish();
            }
        });
        adddata2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(startactivity.this,adddata2.class));
                finish();
            }
        });
        adddata3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(startactivity.this,adddata3.class));
                finish();
            }
        });
    }
}