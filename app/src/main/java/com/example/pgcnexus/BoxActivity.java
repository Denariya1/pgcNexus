package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BoxActivity extends AppCompatActivity {

    CardView card1, card2, card3, card4, card5, card6;
    ImageView profileIcon; // Add this line

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_box);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize all card views
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);
        card6 = findViewById(R.id.card6);

        // Initialize profile icon
        profileIcon = findViewById(R.id.profileIcon); // Make sure this ID matches your XML

        // Set click listeners for cards
        card1.setOnClickListener(v -> {
            Intent intent = new Intent(BoxActivity.this, AttendanceActivity.class);
            startActivity(intent);
        });
        card2.setOnClickListener(v -> {
            Intent intent = new Intent(BoxActivity.this, CourseActivity.class);
            startActivity(intent);
        });
        card3.setOnClickListener(v -> {
            Intent intent = new Intent(BoxActivity.this, EmailActivity.class);
            startActivity(intent);
        });
        card4.setOnClickListener(v -> {
            Intent intent = new Intent(BoxActivity.this, ScheduleActivity.class);
            startActivity(intent);
        });
        card5.setOnClickListener(v -> {
            Intent intent = new Intent(BoxActivity.this, MarksActivity.class);
            startActivity(intent);
        });
        card6.setOnClickListener(v -> {
            Intent intent = new Intent(BoxActivity.this, AnnouncementActivity.class);
            startActivity(intent);
        });

        // Set click listener for profile icon
        profileIcon.setOnClickListener(v -> {
            Intent profileIntent = new Intent(BoxActivity.this, TeacherProfileActivity.class);
            startActivity(profileIntent);
        });
    }
}
