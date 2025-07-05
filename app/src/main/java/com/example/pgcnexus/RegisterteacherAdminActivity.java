package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterteacherAdminActivity extends AppCompatActivity {

    private static final String TAG = "TeacherRegistration";
    private ImageView arrow1, arrow2, arrow3, backArrow;
    private LinearLayout details1, details2, details3;
    private Button addTeacherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerteacher_admin);

        try {
            // Initialize Views
            arrow1 = findViewById(R.id.arrow1);
            arrow2 = findViewById(R.id.arrow2);
            arrow3 = findViewById(R.id.arrow3);
            backArrow = findViewById(R.id.backArrow); // Make sure your XML has this ID

            details1 = findViewById(R.id.details1);
            details2 = findViewById(R.id.details2);
            details3 = findViewById(R.id.details3);

            addTeacherButton = findViewById(R.id.addTeacherButton);

            // Set click listeners for expanding/collapsing
            arrow1.setOnClickListener(v -> toggleVisibility(details1));
            arrow2.setOnClickListener(v -> toggleVisibility(details2));
            arrow3.setOnClickListener(v -> toggleVisibility(details3));

            // Back arrow functionality
            backArrow.setOnClickListener(v -> {
                Intent intent = new Intent(RegisterteacherAdminActivity.this, ManageTeacherActivity.class);
                startActivity(intent);
                finish();
            });

            // Add Teacher button functionality (placeholder — currently reloads same screen)
            addTeacherButton.setOnClickListener(v -> {
                Toast.makeText(RegisterteacherAdminActivity.this, "Add Teacher button clicked", Toast.LENGTH_SHORT).show();
                // You can implement actual functionality here later
            });

        } catch (Exception e) {
            Log.e(TAG, "Error initializing activity", e);
            Toast.makeText(this, "Error loading registration form", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void toggleVisibility(LinearLayout layout) {
        layout.setVisibility(layout.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
}
