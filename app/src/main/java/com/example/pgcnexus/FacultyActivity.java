package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class FacultyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);
    }

    // Top menu click handler
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
            intent = new Intent(this, SignIn.class); // Add SignIn page intent
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    // Faculty button click handler
    public void onFacultyClick(View view) {
        int id = view.getId();
        Intent intent = null;

        if (id == R.id.btn_it) {
            intent = new Intent(this, BsItActivity.class);
        } else if (id == R.id.btn_cs) {
            intent = new Intent(this, BsCsActivity.class);
        } else if (id == R.id.btn_chem) {
            intent = new Intent(this, BsChemActivity.class);
        } else if (id == R.id.btn_bba) {
            intent = new Intent(this, BbaActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}

