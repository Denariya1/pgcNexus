package com.example.pgcnexus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PostNoticeAdminActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST_CODE = 1001;

    private EditText etTitle, etBody;
    private Spinner spinnerAudience;
    private Uri selectedFileUri = null; // to store selected file

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_notice_admin);

        etTitle = findViewById(R.id.et_notice_title);
        etBody = findViewById(R.id.et_notice_body);
        spinnerAudience = findViewById(R.id.spinner_audience);
        Button btnAttachFile = findViewById(R.id.btn_attach_file);
        Button btnPost = findViewById(R.id.btn_post_notice);

        // Setup spinner values
        String[] audienceOptions = {"BSIT", "BSCS", "BSSE", "BBA", "All"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, audienceOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAudience.setAdapter(adapter);

        // File attachment button
        btnAttachFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*"); // accept all file types
            startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_FILE_REQUEST_CODE);
        });

        // Post notice button
        btnPost.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String body = etBody.getText().toString().trim();
            String audience = spinnerAudience.getSelectedItem().toString();

            if (title.isEmpty() || body.isEmpty()) {
                Toast.makeText(this, "Please enter both title and body.", Toast.LENGTH_SHORT).show();
                return;
            }

            String message = "Notice posted for " + audience;
            if (selectedFileUri != null) {
                message += "\nAttached file: " + selectedFileUri.getLastPathSegment();
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();

            // Clear fields (optional)
            etTitle.setText("");
            etBody.setText("");
            selectedFileUri = null;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedFileUri = data.getData();
            Toast.makeText(this, "File attached successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
