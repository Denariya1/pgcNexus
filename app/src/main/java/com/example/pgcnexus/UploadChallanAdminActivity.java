package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle; // Removed Uri import as it's no longer needed
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

// Removed ActivityResultLauncher and ActivityResultContracts imports

import androidx.appcompat.app.AppCompatActivity;

public class UploadChallanAdminActivity extends AppCompatActivity {

    private Spinner spinnerPrograms;
    private EditText etRollNo;
    private EditText etMonth, etAmount, etDueDate;
    private Switch switchPaid;
    // Removed: private Uri selectedFileUri = null;
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_challan_admin); // Match the layout filename

        // Initialize Views
        spinnerPrograms = findViewById(R.id.spinner_programs);
        etRollNo = findViewById(R.id.et_rollno);
        etMonth = findViewById(R.id.et_month);
        etAmount = findViewById(R.id.et_amount);
        etDueDate = findViewById(R.id.et_due_date);
        switchPaid = findViewById(R.id.switch_paid);
        Button btnSubmitChallan = findViewById(R.id.btn_submit_challan);
        backArrow = findViewById(R.id.backArrow);

        // Set up Program Spinner
        String[] programs = {"BSIT", "BBA", "BSCS", "BSSE"};
        ArrayAdapter<String> programAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, programs);
        programAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrograms.setAdapter(programAdapter);

        // Submit Challan Button Click
        btnSubmitChallan.setOnClickListener(v -> {
            String program = spinnerPrograms.getSelectedItem().toString();
            String rollNo = etRollNo.getText().toString().trim();
            String month = etMonth.getText().toString().trim();
            String amount = etAmount.getText().toString().trim();
            String dueDate = etDueDate.getText().toString().trim();
            boolean isPaid = switchPaid.isChecked();

            // Basic validation
            if (rollNo.isEmpty() || month.isEmpty() || amount.isEmpty() || dueDate.isEmpty()) {
                Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Show summary
            StringBuilder summary = new StringBuilder();
            summary.append("Program: ").append(program)
                    .append("\nRoll No.: ").append(rollNo)
                    .append("\nMonth/Semester: ").append(month)
                    .append("\nAmount: ").append(amount)
                    .append("\nDue Date: ").append(dueDate)
                    .append("\nPaid: ").append(isPaid ? "Yes" : "No");


            Toast.makeText(this, summary.toString(), Toast.LENGTH_LONG).show();

            // Optional: Clear fields
            etRollNo.setText("");
            etMonth.setText("");
            etAmount.setText("");
            etDueDate.setText("");
            switchPaid.setChecked(false);
            // Removed: selectedFileUri = null;
        });

        // Back arrow navigation
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadChallanAdminActivity.this, ManageStudentActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}