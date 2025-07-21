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

public class ManageStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_student);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dashboardBox), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Back arrow navigation
        ImageView backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageStudentActivity.this, AdminHomeActivity.class);
                startActivity(intent);
                finish(); // Optional: removes this activity from the stack
            }
        });

        // Get references to remaining CardViews
        CardView card1 = findViewById(R.id.card1); // Edit Students
        CardView card2 = findViewById(R.id.card2); // Attendance Report
        CardView card3 = findViewById(R.id.card3); // Manage Courses
        CardView card4 = findViewById(R.id.card4); // Upload Challan
        CardView card7 = findViewById(R.id.card7); // Faculties
        CardView card8 = findViewById(R.id.card8); // Marksheet Report

        // Set click listeners for each card
        card1.setOnClickListener(v -> {
            Intent intent = new Intent(ManageStudentActivity.this, EditstudentAdminActivity.class);
            startActivity(intent);
        });

        card2.setOnClickListener(v -> {
            Intent intent = new Intent(ManageStudentActivity.this, AttendanceReportAdminActivity.class);
            startActivity(intent);
        });

        card3.setOnClickListener(v -> {
            Intent intent = new Intent(ManageStudentActivity.this, ManageCoursesAdminActivity.class);
            startActivity(intent);
        });

        card4.setOnClickListener(v -> {
            Intent intent = new Intent(ManageStudentActivity.this, UploadChallanAdminActivity.class);
            startActivity(intent);
        });

        card7.setOnClickListener(v -> {
            Intent intent = new Intent(ManageStudentActivity.this, FaculityAdminActivity.class);
            startActivity(intent);
        });

        card8.setOnClickListener(v -> {
            Intent intent = new Intent(ManageStudentActivity.this, AddStudentAdminActivity.class);
            startActivity(intent);
        });
    }
}