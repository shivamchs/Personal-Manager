package com.example.personalmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    private EditText email;
    private EditText Name;
    private EditText DOB;
    private EditText phone;
    private EditText address;
    private EditText password;
    private Button register;

    private FirebaseAuth auth;
    private FirebaseFirestore store;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Name=findViewById(R.id.name);
        DOB=findViewById(R.id.dob);
        phone=findViewById(R.id.phone);
        address=findViewById(R.id.adress);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        register=findViewById(R.id.reg);
        auth=FirebaseAuth.getInstance();
        store=FirebaseFirestore.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email=email.getText().toString();
                String txt_password=password.getText().toString();
                String txt_name=Name.getText().toString();
                String txt_phone=phone.getText().toString();
                String txt_dob=DOB.getText().toString();
                String txt_address=address.getText().toString();

                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(register.this,"Empty Credentials",Toast.LENGTH_SHORT).show();
                }
                else if(txt_password.length()<6){
                    Toast.makeText(register.this,"password too short",Toast.LENGTH_SHORT).show();
                }
                else{
                    reguser(txt_email,txt_password,txt_name,txt_dob,txt_phone,txt_address);
                }
            }
        });
    }
    private void reguser(String email, String password,String name,String dob,String phone,String address){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((task) -> {

            if (task.isSuccessful()) {
                Toast.makeText(register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                userID = auth.getCurrentUser().getUid();
                DocumentReference documentReference = store.collection("users").document(userID);
                Map<String, Object> user = new HashMap<>();
                user.put("name",name);
                user.put("email", email);
                user.put("phone", phone);
                user.put("dob", dob);
                user.put("address", address);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","OnSuccess: user Profile is created for "+userID);
                    }
                });
                startActivity(new Intent(register.this, startactivity.class));
                finish();
            } else {
                Toast.makeText(register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}