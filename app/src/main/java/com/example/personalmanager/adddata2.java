package com.example.personalmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adddata2 extends AppCompatActivity {
    private Button add2,back1;
    private EditText password;
    private ListView list2;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddata2);
        back1=findViewById(R.id.btn4);
        password=findViewById(R.id.data2);
        add2=findViewById(R.id.button2);
        list2=findViewById(R.id.list2);
        userId=FirebaseAuth.getInstance().getCurrentUser().getUid();

        back1.setOnClickListener(v -> {
            startActivity(new Intent(adddata2.this,startactivity.class));
            finish();
        });
        add2.setOnClickListener(v -> {
            String txt_1=password.getText().toString();

            if(txt_1.isEmpty()){
                Toast.makeText(adddata2.this, "Empty Data", Toast.LENGTH_SHORT).show();
            }else{
                FirebaseDatabase.getInstance().getReference(userId).child("password").child("charcter").push().setValue(txt_1);
            }
        });

        ArrayList<String> arrayList1=new ArrayList<>();
        ArrayAdapter adapter1=new ArrayAdapter<String>(this, R.layout.list_2,  arrayList1);
        list2.setAdapter(adapter1);
        DatabaseReference reference1=FirebaseDatabase.getInstance().getReference(userId).child("password").child("character");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList1.clear();
                for(DataSnapshot dataSnapshot1:snapshot.getChildren()){
                    arrayList1.add(dataSnapshot1.getValue().toString());
                }
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}