package com.example.pgcnexus;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class TeacherProfileActivity extends AppCompatActivity {

    private TextView tabProfile, tabAcademic;
    private LinearLayout layoutProfile, layoutAcademic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        // Initialize views
        tabProfile = findViewById(R.id.tabProfile);
        tabAcademic = findViewById(R.id.tabAcademic);
        layoutProfile = findViewById(R.id.layoutProfile);
        layoutAcademic = findViewById(R.id.layoutAcademic);

        // Set click listeners for tabs
        tabProfile.setOnClickListener(v -> {
            setActiveTab(tabProfile);
            showProfileContent();
        });

        tabAcademic.setOnClickListener(v -> {
            setActiveTab(tabAcademic);
            showAcademicContent();
        });

        // Set initial active tab and content
        setActiveTab(tabProfile);
        showProfileContent();
    }

    private void setActiveTab(TextView activeTab) {
        // Reset both tabs to default state
        tabProfile.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        tabAcademic.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        tabProfile.setTextColor(ContextCompat.getColor(this, android.R.color.black));
        tabAcademic.setTextColor(ContextCompat.getColor(this, android.R.color.black));

        // Highlight the active tab
        activeTab.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
        activeTab.setTextColor(ContextCompat.getColor(this, android.R.color.white));
    }

    private void showProfileContent() {
        layoutProfile.setVisibility(View.VISIBLE);
        layoutAcademic.setVisibility(View.GONE);
    }

    private void showAcademicContent() {
        layoutProfile.setVisibility(View.GONE);
        layoutAcademic.setVisibility(View.VISIBLE);
    }
}
