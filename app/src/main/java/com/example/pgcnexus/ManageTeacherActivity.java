package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ManageTeacherActivity extends AppCompatActivity {

    CardView cardRegisterTeachers, cardViewTeachers, cardAssignSubjects, cardSendAnnouncements;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_teacher);

        // Initialize views
        cardRegisterTeachers = findViewById(R.id.monday_card);
        cardViewTeachers = findViewById(R.id.tuesday_card);
        cardAssignSubjects = findViewById(R.id.wednesday_card);
        backButton = findViewById(R.id.backButton);

        // Set click listeners for cards
        cardRegisterTeachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageTeacherActivity.this, RegisterteacherAdminActivity.class);
                startActivity(intent);
            }
        });

        cardViewTeachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageTeacherActivity.this, ViewAllteachersAdminActivity.class);
                startActivity(intent);
            }
        });

        cardAssignSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageTeacherActivity.this, AssignSubToteacherAdminActivity.class);
                startActivity(intent);
            }
        });


        // Back button functionality
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Go back to previous activity
            }
        });
    }
    }
