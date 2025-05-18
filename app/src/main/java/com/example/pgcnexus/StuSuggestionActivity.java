package com.example.pgcnexus;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StuSuggestionActivity extends AppCompatActivity {

    private static final int FILE_PICKER_REQUEST_CODE = 1;

    private RadioGroup typeGroup;
    private RadioGroup anonymousGroup;
    private Spinner categorySpinner;
    private EditText subjectEditText;
    private EditText descriptionEditText;
    private Button attachButton;
    private TextView selectedFileTextView;
    private Button submitButton;

    private Uri selectedFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_suggestion);

        // Initialize views
        initializeViews();

        // Setup category spinner
        setupCategorySpinner();

        // Set up button click listeners
        setupButtonListeners();
    }

    private void initializeViews() {
        typeGroup = findViewById(R.id.typeGroup);
        anonymousGroup = findViewById(R.id.anonymousGroup);
        categorySpinner = findViewById(R.id.categorySpinner);
        subjectEditText = findViewById(R.id.subjectEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        attachButton = findViewById(R.id.attachButton);
        selectedFileTextView = findViewById(R.id.selectedFileTextView);
        submitButton = findViewById(R.id.submitButton);
    }

    private void setupCategorySpinner() {
        String[] categories = new String[]{
                "Select Category",
                "Academic",
                "Administration",
                "Facilities",
                "Library",
                "Cafeteria",
                "Transportation",
                "Other"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    private void setupButtonListeners() {
        attachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }

    private void openFilePicker() {
        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, "Select a file"), FILE_PICKER_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(this, "Error opening file picker: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getData() != null) {
                selectedFileUri = data.getData();
                String fileName = selectedFileUri.getLastPathSegment();
                if (fileName != null) {
                    selectedFileTextView.setText("Selected file: " + fileName);
                    selectedFileTextView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void submitForm() {
        if (!validateForm()) {
            return;
        }

        try {
            String type = ((RadioButton)findViewById(typeGroup.getCheckedRadioButtonId())).getText().toString();
            boolean isAnonymous = ((RadioButton)findViewById(anonymousGroup.getCheckedRadioButtonId())).getText().toString().equals("Yes");
            String category = categorySpinner.getSelectedItem().toString();
            String subject = subjectEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();

            String message = "Type: " + type + "\n" +
                    "Anonymous: " + (isAnonymous ? "Yes" : "No") + "\n" +
                    "Category: " + category + "\n" +
                    "Subject: " + subject + "\n" +
                    "Description: " + description + "\n" +
                    "Attachment: " + (selectedFileUri != null ? selectedFileUri.toString() : "None");

            Toast.makeText(this, "Suggestion submitted successfully!\n\n" + message, Toast.LENGTH_LONG).show();

            resetForm();
        } catch (Exception e) {
            Toast.makeText(this, "Error submitting form: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateForm() {
        if (categorySpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (subjectEditText.getText().toString().trim().isEmpty()) {
            subjectEditText.setError("Subject is required");
            return false;
        }

        if (descriptionEditText.getText().toString().trim().isEmpty()) {
            descriptionEditText.setError("Description is required");
            return false;
        }

        return true;
    }

    private void resetForm() {
        typeGroup.check(R.id.radioSuggestion);
        anonymousGroup.check(R.id.radioAnonymousNo);
        categorySpinner.setSelection(0);
        subjectEditText.setText("");
        descriptionEditText.setText("");
        selectedFileTextView.setVisibility(View.GONE);
        selectedFileUri = null;
    }
}