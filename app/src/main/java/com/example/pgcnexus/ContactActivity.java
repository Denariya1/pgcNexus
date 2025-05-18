package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {

    private EditText etName, etEmail, etMessage;
    private Button btnSendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // Initialize UI elements
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etMessage = findViewById(R.id.et_message);
        btnSendMessage = findViewById(R.id.btn_send_message);

        // Menu navigation
        TextView home = findViewById(R.id.home);
        TextView faculties = findViewById(R.id.faculties);
        TextView about = findViewById(R.id.about);
        TextView contactUs = findViewById(R.id.contact_us);
        TextView signIn = findViewById(R.id.sign_in); // Added sign-in option

        home.setOnClickListener(v -> startActivity(new Intent(ContactActivity.this, GuestHomeActivity.class)));
        faculties.setOnClickListener(v -> startActivity(new Intent(ContactActivity.this, FacultyActivity.class)));
        about.setOnClickListener(v -> startActivity(new Intent(ContactActivity.this, AboutActivity.class)));
        contactUs.setOnClickListener(v -> {
            // Stay on ContactActivity
        });
        signIn.setOnClickListener(v -> startActivity(new Intent(ContactActivity.this, SignIn.class)));

        // Handle Send Message button click
        btnSendMessage.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String message = etMessage.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                Toast.makeText(ContactActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(ContactActivity.this, "Invalid email format. Please use format like abc@gmail.com", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ContactActivity.this, "Message sent successfully!", Toast.LENGTH_SHORT).show();
            }
        });
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
            intent = new Intent(this, SignIn.class); // Added SignIn
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}

