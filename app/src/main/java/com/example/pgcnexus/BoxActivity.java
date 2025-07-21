package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem; // Import for menu item handling
import android.view.View;
import android.widget.ImageButton; // Import for ImageButton (for settingsIcon)
import android.widget.ImageView; // For profileIcon
import android.widget.Toast; // Import for Toast
import androidx.appcompat.widget.PopupMenu; // Import for PopupMenu
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Import Firebase Authentication
import com.google.firebase.auth.FirebaseAuth;

public class BoxActivity extends AppCompatActivity {

    CardView card1, card2, card3, card4, card5, card6;
    ImageView profileIcon; //  ImageView as declared in XML for profile_button
    ImageButton settingsIcon; //  ImageButton as declared in XML for settings

    private FirebaseAuth mAuth; // Declare FirebaseAuth instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_box);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

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

        // Initialize icons
        profileIcon = findViewById(R.id.profileIcon); // profileIcon (ImageView)
        settingsIcon = findViewById(R.id.settingsIcon); // settingsIcon (ImageButton)

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

        // Set click listener for profile icon (for profile page)
        if (profileIcon != null) {
            profileIcon.setOnClickListener(v -> {
                Intent profileIntent = new Intent(BoxActivity.this, TeacherProfileActivity.class);
                startActivity(profileIntent);
            });
        }

        // Setup the settings button with the PopupMenu for logout
        setupSettingsButton();
    }

    // Method to setup the settings button's click listener for logout
    private void setupSettingsButton() {
        if (settingsIcon != null) { // Add a null check just in case
            settingsIcon.setOnClickListener(v -> {
                PopupMenu popup = new PopupMenu(this, v);
                popup.getMenuInflater().inflate(R.menu.settings_menu, popup.getMenu()); // Ensure menu name is correct

                popup.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.action_logout) { // Ensure item ID is correct
                        performLogout();
                        return true;
                    }
                    return false;
                });
                popup.show();
            });
        } else {
            Toast.makeText(this, "Settings icon not found for logout functionality.", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to handle the logout logic
    private void performLogout() {
        // 1. Sign out from Firebase Authentication
        if (mAuth != null) {
            mAuth.signOut();
            Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
        } else {
            // This case should ideally not happen if Firebase is set up correctly
            Toast.makeText(this, "Error: Firebase Auth not initialized for logout.", Toast.LENGTH_SHORT).show();
        }

        // 2. Clear any local session data (e.g., SharedPreferences) if you have any
        // Example: SharedPreferences preferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        // SharedPreferences.Editor editor = preferences.edit();
        // editor.clear();
        // editor.apply();

        // 3. Navigate back to the SignInActivity (or your main login page)
        // IMPORTANT: Replace 'SignIn.class' with the actual class name of your sign-in/login activity
        Intent intent = new Intent(this, SignIn.class);
        // These flags ensure the user cannot navigate back to BoxActivity after logout
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Finish the current activity (BoxActivity)
    }
}