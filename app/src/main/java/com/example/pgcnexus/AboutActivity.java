package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
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
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}