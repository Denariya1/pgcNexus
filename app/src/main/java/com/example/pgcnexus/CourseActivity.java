package com.example.pgcnexus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CourseActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST_CODE = 1;

    EditText editTextSubject;
    Button buttonChooseFile, buttonSend;
    TextView textSelectedFile;
    Spinner spinnerClass;
    Uri selectedFileUri = null;
    String selectedClass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        editTextSubject = findViewById(R.id.editText_subject);
        buttonChooseFile = findViewById(R.id.button_choose_file);
        buttonSend = findViewById(R.id.button_send);
        textSelectedFile = findViewById(R.id.text_selected_file);
        spinnerClass = findViewById(R.id.spinner_class);

        // Setup spinner with class options
        setupClassSpinner();

        buttonChooseFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
        });

        buttonSend.setOnClickListener(v -> {
            String subject = editTextSubject.getText().toString().trim();

            // Validate all fields
            if (selectedClass.isEmpty()) {
                Toast.makeText(this, "Please select a class", Toast.LENGTH_SHORT).show();
                return;
            }
            if (subject.isEmpty()) {
                Toast.makeText(this, "Please enter a subject", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedFileUri == null) {
                Toast.makeText(this, "Please choose a file", Toast.LENGTH_SHORT).show();
                return;
            }

            // All validations passed
            String message = "Course material ready for " + selectedClass + "\n" +
                    "Subject: " + subject + "\n" +
                    "File: " + textSelectedFile.getText().toString();
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        });
    }

    private void setupClassSpinner() {
        // Class options
        String[] classes = {
                "Select Class",
                "BSIT 21",
                "BSIT 22",
                "BSCS 22",
                "BSCHE 21",
                "BCSHEM 21"
        };

        // Create adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                classes
        ) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item (hint)
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                // Gray out the hint
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClass.setAdapter(adapter);

        // Spinner selection listener
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedClass = classes[position];
                } else {
                    selectedClass = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedClass = "";
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedFileUri = data.getData();
            if (selectedFileUri != null) {
                String fileName = getFileName(selectedFileUri);
                textSelectedFile.setText(fileName != null ? fileName : "File selected");
            }
        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index >= 0) {
                        result = cursor.getString(index);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
}
