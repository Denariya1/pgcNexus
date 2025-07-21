package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem; // Import for menu item handling
import android.view.View;
import android.widget.ImageButton; // Import for ImageButton
import android.widget.PopupMenu; // Import for PopupMenu
import android.widget.Toast;
import androidx.cardview.widget.CardView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Import Firebase Auth for actual sign out
import com.google.firebase.auth.FirebaseAuth;

public class AdminHomeActivity extends AppCompatActivity {

    private ImageButton profileIcon;
    private ImageButton settingsIcon;
    private FirebaseAuth mAuth; // Declare FirebaseAuth instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Handle window insets properly
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dashboardBox), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize ImageButtons
        profileIcon = findViewById(R.id.profileIcon);
        settingsIcon = findViewById(R.id.settingsIcon);

        // Set OnClickListener for the profile icon to open AdminProfileActivity
        if (profileIcon != null) {
            profileIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        // Assuming AdminProfileActivity is the name of your admin profile screen
                        Intent profileIntent = new Intent(AdminHomeActivity.this, AdminProfileActivity.class);
                        startActivity(profileIntent);
                    } catch (Exception e) {
                        Toast.makeText(AdminHomeActivity.this, "Error opening admin profile.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Profile icon not found.", Toast.LENGTH_SHORT).show();
        }


        // Setup the settings button with the PopupMenu for logout
        setupSettingsButton();

        // --- Existing CardView Click Listeners ---
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

        // Guest Card (as previously discussed, ensuring it's initialized and has a listener)
        CardView guestCard = findViewById(R.id.guestCard);
        if (guestCard != null) {
            guestCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        // Replace with your actual Guest Management Activity
                        // Intent intent = new Intent(AdminHomeActivity.this, ManageGuestActivity.class);
                        // startActivity(intent);
                        Toast.makeText(AdminHomeActivity.this, "Manage Guests clicked!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(AdminHomeActivity.this, "Error opening guest management", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Guest card not found", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to setup the settings button's click listener for logout
    private void setupSettingsButton() {
        if (settingsIcon != null) {
            settingsIcon.setOnClickListener(v -> {
                PopupMenu popup = new PopupMenu(this, v);
                popup.getMenuInflater().inflate(R.menu.settings_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.action_logout) {
                        performLogout();
                        return true;
                    }
                    return false;
                });
                popup.show();
            });
        } else {
            Toast.makeText(this, "Settings icon not initialized for popup menu setup.", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to handle the logout logic
    private void performLogout() {
        // 1. Sign out from Firebase Authentication
        if (mAuth != null) {
            mAuth.signOut();
            Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error: Firebase Auth not initialized.", Toast.LENGTH_SHORT).show();
        }

        // 2. Clear any local session data (e.g., SharedPreferences) if you have any
        // Example: SharedPreferences preferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        // SharedPreferences.Editor editor = preferences.edit();
        // editor.clear();
        // editor.apply();

        // 3. Navigate to login screen and clear back stack
        // IMPORTANT: Replace 'SignIn.class' with the actual class name of your sign-in/login activity
        Intent intent = new Intent(this, SignIn.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Finish the current activity (AdminHomeActivity)
    }
}