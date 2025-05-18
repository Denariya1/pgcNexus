package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BoxActivity extends AppCompatActivity {

    private CardView card1, card2, card3, card4, card5, card6;

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

        // Initialize cards
        card1 = findViewById(R.id.card1); // Attendance
        card2 = findViewById(R.id.card2); // Courses
        card3 = findViewById(R.id.card3); // Announcements
        card4 = findViewById(R.id.card4); // Schedule
        card5 = findViewById(R.id.card5); // Marks
        card6 = findViewById(R.id.card6); // Email

        // Set click listeners
        card1.setOnClickListener(v -> startActivity(new Intent(BoxActivity.this, AttendanceActivity.class)));
        card2.setOnClickListener(v -> startActivity(new Intent(BoxActivity.this, CourseActivity.class)));
        card3.setOnClickListener(v -> startActivity(new Intent(BoxActivity.this, AnnouncementActivity.class)));
        card4.setOnClickListener(v -> startActivity(new Intent(BoxActivity.this, ScheduleActivity.class)));
        card5.setOnClickListener(v -> startActivity(new Intent(BoxActivity.this, MarksActivity.class)));
        card6.setOnClickListener(v -> startActivity(new Intent(BoxActivity.this, EmailActivity.class)));
    }
}

