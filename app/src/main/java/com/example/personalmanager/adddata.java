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
import com.squareup.okhttp.internal.DiskLruCache;

import java.util.ArrayList;
import java.util.List;

public class adddata extends AppCompatActivity {
    private Button add1,back;
    private EditText contact;
    private ListView list1;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddata);
        back=findViewById(R.id.button3);
        contact=findViewById(R.id.data1);
        add1=findViewById(R.id.button);
        list1=findViewById(R.id.list1);
        userId=FirebaseAuth.getInstance().getCurrentUser().getUid();

        back.setOnClickListener(v -> {
            startActivity(new Intent(adddata.this,startactivity.class));
            finish();
        });
        add1.setOnClickListener(v -> {
            String txt_1=contact.getText().toString();

            if(txt_1.isEmpty()){
                Toast.makeText(adddata.this, "Empty Data", Toast.LENGTH_SHORT).show();
            }else{
                FirebaseDatabase.getInstance().getReference(userId).child("contacts").child("phone").push().setValue(txt_1);
            }
        });

        ArrayList<String>arrayList=new ArrayList<>();
        ArrayAdapter adapter=new ArrayAdapter<String>(this, R.layout.list_1,  arrayList);
        list1.setAdapter(adapter);
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference(userId).child("contacts").child("phone");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    arrayList.add(dataSnapshot.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}