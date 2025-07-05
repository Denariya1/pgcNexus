package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.cardview.widget.CardView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home);

        // Handle window insets properly
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dashboardBox), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Manage Students Card
        CardView studentCard = findViewById(R.id.studentCard);
        if (studentCard != null) {
            studentCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(AdminHomeActivity.this, ManageStudentActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(AdminHomeActivity.this, "Error opening student management", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Student card not found", Toast.LENGTH_SHORT).show();
        }

        // Manage Teachers Card
        CardView teacherCard = findViewById(R.id.teacherCard);
        if (teacherCard != null) {
            teacherCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(AdminHomeActivity.this, ManageTeacherActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(AdminHomeActivity.this, "Error opening teacher management", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Teacher card not found", Toast.LENGTH_SHORT).show();
        }
    }
}
