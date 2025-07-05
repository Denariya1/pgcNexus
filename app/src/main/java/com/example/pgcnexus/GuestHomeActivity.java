package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

public class GuestHomeActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_home);

        // Initialize ViewFlipper
        viewFlipper = findViewById(R.id.viewFlipper);

        // Configure auto-sliding if needed programmatically
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(1800); // Change image every 3 seconds

        // Start the animation
        viewFlipper.startFlipping();

        // Chatbot Button - Changed from Button to ImageButton
        ImageButton chatbotButton = findViewById(R.id.chatbot_button);
        chatbotButton.setOnClickListener(v -> openChatbot());
    }

    // Method to open Chatbot Activity
    public void openChatbot() {
        Intent intent = new Intent(this, ChatbotActivity.class);
        startActivity(intent);
    }

    public void onMenuClick(View view) {
        int id = view.getId();
        Intent intent = null;

        if (id == R.id.home) {
            intent = new Intent(this, GuestHomeActivity.class);
        } else if (id == R.id.faculties) {
            intent = new Intent(this, FacultyActivity.class);
        } else if (id == R.id.about) {
            intent = new Intent(this, AboutActivity.class);
        } else if (id == R.id.contact_us) {
            intent = new Intent(this, ContactActivity.class);
        } else if (id == R.id.sign_in) {
            intent = new Intent(this, SignIn.class);
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}