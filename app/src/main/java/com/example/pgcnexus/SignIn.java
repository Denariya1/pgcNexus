package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        // Guest Button
        Button btnGuest = findViewById(R.id.btn_guest);
        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, GuestHomeActivity.class);
                startActivity(intent);
            }
        });

        // Admin Button
        Button btnAdmin = findViewById(R.id.btn_one);
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, AdminHomeActivity.class);
                startActivity(intent);
            }
        });

        // Student Button
        Button btnStudent = findViewById(R.id.btn_two);
        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, Student1Activity.class);
                startActivity(intent);
            }
        });

        // Teacher Button
        Button btnTeacher = findViewById(R.id.btn_three);
        btnTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, BoxActivity.class);
                startActivity(intent);
            }
        });
    }
}

