package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UploadChallanAdminActivity extends AppCompatActivity {

    private Spinner spinnerPrograms;
    private EditText etRollNo;
    private EditText etMonth, etAmount, etDueDate, etIssueDate, etPaidDate; // Added etIssueDate, etPaidDate
    private Switch switchPaid;
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
        etIssueDate = findViewById(R.id.et_issueDate); // Initialize etIssueDate
        etPaidDate = findViewById(R.id.et_paid_date);   // Initialize etPaidDate
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
            String issueDate = etIssueDate.getText().toString().trim(); // Get issue date
            String dueDate = etDueDate.getText().toString().trim();
            String paidDate = etPaidDate.getText().toString().trim();   // Get paid date
            boolean isPaid = switchPaid.isChecked();

            // Basic validation - now includes rollNo, issueDate, paidDate (if not paid)
            if (rollNo.isEmpty() || month.isEmpty() || amount.isEmpty() || issueDate.isEmpty() || dueDate.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            // If "Mark as Paid" is checked, "Paid Date" should not be empty
            if (isPaid && paidDate.isEmpty()) {
                Toast.makeText(this, "Please enter Paid Date if marking as Paid.", Toast.LENGTH_SHORT).show();
                return;
            }
            // If "Mark as Paid" is NOT checked, "Paid Date" should be empty
            if (!isPaid && !paidDate.isEmpty()) {
                Toast.makeText(this, "Paid Date should be empty if not marked as Paid.", Toast.LENGTH_SHORT).show();
                return;
            }


            // Show summary
            StringBuilder summary = new StringBuilder();
            summary.append("Program: ").append(program)
                    .append("\nRoll No.: ").append(rollNo)
                    .append("\nMonth/Semester: ").append(month)
                    .append("\nAmount: ").append(amount)
                    .append("\nIssue Date: ").append(issueDate) // Add issue date to summary
                    .append("\nDue Date: ").append(dueDate)
                    .append("\nPaid Date: ").append(paidDate.isEmpty() ? "N/A" : paidDate) // Add paid date to summary
                    .append("\nPaid: ").append(isPaid ? "Yes" : "No");


            Toast.makeText(this, summary.toString(), Toast.LENGTH_LONG).show();

            // Optional: Clear fields
            etRollNo.setText("");
            etMonth.setText("");
            etAmount.setText("");
            etIssueDate.setText(""); // Clear issue date field
            etDueDate.setText("");
            etPaidDate.setText("");   // Clear paid date field
            switchPaid.setChecked(false);
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