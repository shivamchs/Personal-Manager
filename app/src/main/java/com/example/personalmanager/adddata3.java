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

public class adddata3 extends AppCompatActivity {
    private EditText adress;
    private ListView list3;
    private Button add3,back;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddata3);

        adress=findViewById(R.id.data3);
        list3=findViewById(R.id.list3);
        add3=findViewById(R.id.button4);
        back=findViewById(R.id.back3);
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();

        add3.setOnClickListener(v -> {
           String txt_3=adress.getText().toString();
           if(txt_3.isEmpty()){
               Toast.makeText(adddata3.this, "Empty Data", Toast.LENGTH_SHORT).show();
           }
           else{
               FirebaseDatabase.getInstance().getReference(userId).child("adress").child("place").push().setValue(txt_3);
           }
        });
        back.setOnClickListener(v -> {
            startActivity(new Intent(adddata3.this,startactivity.class));
            finish();
        });
        ArrayList<String>arrayList3=new ArrayList<>();
        ArrayAdapter adapter3=new ArrayAdapter<String>(this,R.layout.list_3,arrayList3);
        DatabaseReference reference3=FirebaseDatabase.getInstance().getReference(userId).child("adress").child("place").push();
        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList3.clear();
                for(DataSnapshot dataSnapshot3:snapshot.getChildren()){
                    arrayList3.add(dataSnapshot3.getValue().toString());
                }
                adapter3.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}