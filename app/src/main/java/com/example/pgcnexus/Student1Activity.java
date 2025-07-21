package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast; // Import the Toast class
import androidx.appcompat.widget.PopupMenu;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// If you are using Firebase Authentication for logout, uncomment the following line:
import com.google.firebase.auth.FirebaseAuth;

public class Student1Activity extends AppCompatActivity {

    // Declare all card views
    private CardView cardProfile, cardCourses, cardFeeChallan, cardLeaveStatus,
            cardLectureSchedule, cardSuggestionBox;
    private ImageButton settingsButton;

    // If you are using Firebase Authentication, declare a FirebaseAuth instance:
    // private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student1);

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);
        setupWindowInsets();

        // Initialize all views
        initializeViews();

        // Set click listeners for all cards
        setupCardClickListeners();

        // Setup settings button with popup menu
        setupSettingsButton();

        // If using Firebase Authentication, initialize it here:
        // mAuth = FirebaseAuth.getInstance();
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeViews() {
        // Initialize card views
        cardProfile = findViewById(R.id.card1);
        cardCourses = findViewById(R.id.card2);
        cardFeeChallan = findViewById(R.id.card3);
        cardLeaveStatus = findViewById(R.id.card4);
        cardLectureSchedule = findViewById(R.id.card5);
        cardSuggestionBox = findViewById(R.id.card6);

        // Initialize settings button
        settingsButton = findViewById(R.id.settingsButton);
    }

    private void setupCardClickListeners() {
        cardProfile.setOnClickListener(v ->
                startActivity(new Intent(this, StudentProfileActivity.class)));

        cardCourses.setOnClickListener(v ->
                startActivity(new Intent(this, RegisteredCoursesActivity.class)));

        cardFeeChallan.setOnClickListener(v ->
                startActivity(new Intent(this, StuFeechallanActivity.class)));

        cardLeaveStatus.setOnClickListener(v ->
                startActivity(new Intent(this, StuCoursesActivity.class)));

        cardLectureSchedule.setOnClickListener(v ->
                startActivity(new Intent(this, StuLecSchActivity.class)));

        cardSuggestionBox.setOnClickListener(v ->
                startActivity(new Intent(this, StuSuggestionActivity.class)));
    }

    private void setupSettingsButton() {
        settingsButton.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, v);
            popup.getMenuInflater().inflate(R.menu.settings_menu, popup.getMenu()); // Ensure R.menu.settings_menu exists

            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_logout) { // Ensure R.id.action_logout exists in settings_menu.xml
                    performLogout();
                    return true;
                }
                return false;
            });
            popup.show();
        });
    }

    private void performLogout() {
        // --- Add any logout logic here ---

        // If using Firebase Authentication, uncomment the following lines:
        // if (mAuth != null) {
        //     mAuth.signOut();
        // }

        // Display the "Logout successfully!" message
        Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();

        // Navigate to login screen and clear back stack
        Intent intent = new Intent(this, SignIn.class); // Ensure 'SignIn.class' is the correct name for your login activity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Finish the current activity (Student1Activity)
    }
}