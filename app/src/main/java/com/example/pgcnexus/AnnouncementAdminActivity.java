package com.example.pgcnexus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class AnnouncementAdminActivity extends AppCompatActivity {

    private Spinner spinnerPrograms;
    private EditText etTitle, etMessage;
    private Uri attachedFileUri = null;

    // File picker launcher
    private final ActivityResultLauncher<Intent> filePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    attachedFileUri = result.getData().getData();
                    if (attachedFileUri != null) {
                        Toast.makeText(this, "File attached: " + attachedFileUri.getLastPathSegment(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcement_admin);

        // Initialize views
        spinnerPrograms = findViewById(R.id.spinner_programs);
        etTitle = findViewById(R.id.et_announcement_title);
        etMessage = findViewById(R.id.et_announcement_message);
        Button btnAttachFile = findViewById(R.id.btn_attach_file);
        Button btnSubmit = findViewById(R.id.btn_submit_announcement);

        // Set up spinner with program options
        String[] programs = {"BSIT", "BSCS", "BSSE", "BBA", "All"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, programs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrograms.setAdapter(adapter);

        // Handle file attachment
        btnAttachFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*"); // Accept any file type. Use "application/pdf" for only PDFs.
            filePickerLauncher.launch(Intent.createChooser(intent, "Select File"));
        });

        // Handle submission
        btnSubmit.setOnClickListener(v -> {
            String selectedProgram = spinnerPrograms.getSelectedItem().toString();
            String title = etTitle.getText().toString().trim();
            String message = etMessage.getText().toString().trim();

            if (title.isEmpty() || message.isEmpty()) {
                Toast.makeText(this, "Please enter both title and message.", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder summary = new StringBuilder();
            summary.append("Program: ").append(selectedProgram).append("\n")
                    .append("Title: ").append(title).append("\n")
                    .append("Message: ").append(message);

            if (attachedFileUri != null) {
                summary.append("\nFile: ").append(attachedFileUri.getLastPathSegment());
            }

            // Show announcement preview (replace this with Firebase or backend logic)
            Toast.makeText(this, summary.toString(), Toast.LENGTH_LONG).show();

            // Optional: Clear fields
            etTitle.setText("");
            etMessage.setText("");
            attachedFileUri = null;
        });
    }
}
