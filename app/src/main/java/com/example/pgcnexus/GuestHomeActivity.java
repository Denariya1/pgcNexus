package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ViewFlipper;
import androidx.appcompat.app.AppCompatActivity;

public class GuestHomeActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_home);

        viewFlipper = findViewById(R.id.viewFlipper);

        // Set flipping interval to 1.5 seconds
        viewFlipper.setFlipInterval(1500);
        viewFlipper.startFlipping();
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
            intent = new Intent(this, SignIn.class); // Use SignIn instead of SignInActivity
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}

