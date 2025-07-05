package com.example.pgcnexus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class UploadChallanAdminActivity extends AppCompatActivity {

    private Spinner spinnerPrograms;
    private EditText etMonth, etAmount, etDueDate;
    private Switch switchPaid;
    private Uri selectedFileUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_challan_admin); // Match the layout filename

        // Initialize Views
        spinnerPrograms = findViewById(R.id.spinner_programs);
        etMonth = findViewById(R.id.et_month);
        etAmount = findViewById(R.id.et_amount);
        etDueDate = findViewById(R.id.et_due_date);
        switchPaid = findViewById(R.id.switch_paid);
        Button btnUploadFile = findViewById(R.id.btn_upload_file);
        Button btnSubmitChallan = findViewById(R.id.btn_submit_challan);

        // Set up Program Spinner
        String[] programs = {"BSIT", "BBA", "BSCS", "BSSE"};
        ArrayAdapter<String> programAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, programs);
        programAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrograms.setAdapter(programAdapter);

        // File Picker
        ActivityResultLauncher<Intent> filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedFileUri = result.getData().getData();
                        Toast.makeText(this, "File selected: " + selectedFileUri.getLastPathSegment(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Upload File Button Click
        btnUploadFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*"); // Use "application/pdf" or "image/*" if needed
            filePickerLauncher.launch(Intent.createChooser(intent, "Select Challan File"));
        });

        // Submit Challan Button Click
        btnSubmitChallan.setOnClickListener(v -> {
            String program = spinnerPrograms.getSelectedItem().toString();
            String month = etMonth.getText().toString().trim();
            String amount = etAmount.getText().toString().trim();
            String dueDate = etDueDate.getText().toString().trim();
            boolean isPaid = switchPaid.isChecked();

            // Basic validation
            if (month.isEmpty() || amount.isEmpty() || dueDate.isEmpty()) {
                Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Show summary
            StringBuilder summary = new StringBuilder();
            summary.append("Program: ").append(program)
                    .append("\nMonth/Semester: ").append(month)
                    .append("\nAmount: ").append(amount)
                    .append("\nDue Date: ").append(dueDate)
                    .append("\nPaid: ").append(isPaid ? "Yes" : "No");

            if (selectedFileUri != null) {
                summary.append("\nFile: ").append(selectedFileUri.getLastPathSegment());
            }

            Toast.makeText(this, summary.toString(), Toast.LENGTH_LONG).show();

            // Optional: Clear fields
            etMonth.setText("");
            etAmount.setText("");
            etDueDate.setText("");
            switchPaid.setChecked(false);
            selectedFileUri = null;
        });
    }
}
