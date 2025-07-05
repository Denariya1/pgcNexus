package com.example.pgcnexus;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AnnouncementActivity extends AppCompatActivity {

    private Button btnAddAnnouncement;
    private Spinner spinnerClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_announcement);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        setupSpinner();
        setupListeners();
    }

    private void initializeViews() {
        btnAddAnnouncement = findViewById(R.id.button_send);
        spinnerClass = findViewById(R.id.spinner_class);
    }

    private void setupSpinner() {
        String[] classOptions = {"BSIT21B", "BSIT23", "BSCS21", "BSSE20"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClass.setAdapter(adapter);
    }

    private void setupListeners() {
        btnAddAnnouncement.setOnClickListener(v -> {
            String selectedClass = spinnerClass.getSelectedItem().toString();
            Toast.makeText(this, "Announcement added for " + selectedClass, Toast.LENGTH_SHORT).show();
        });
    }
}
