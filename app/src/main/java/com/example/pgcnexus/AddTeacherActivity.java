package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddTeacherActivity extends AppCompatActivity {

    private static final String TAG = "AddTeacherActivity";
    private EditText fullNameEditText, emailEditText, departmentEditText,
            phoneEditText, qualificationEditText, subjectsEditText;
    private Button saveButton;
    private ImageView backArrow; // This is correctly declared and initialized
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Test Firebase connection
        testFirebaseConnection();

        // Initialize Views
        initializeViews();
        setupClickListeners(); // This method already sets up the back arrow click listener
    }

    private void initializeViews() {
        fullNameEditText = findViewById(R.id.fullNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        departmentEditText = findViewById(R.id.departmentEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        qualificationEditText = findViewById(R.id.qualificationEditText);
        subjectsEditText = findViewById(R.id.subjectsEditText);
        saveButton = findViewById(R.id.saveButton);
        backArrow = findViewById(R.id.backArrow); // Correctly initialized here
    }

    private void setupClickListeners() {
        backArrow.setOnClickListener(v -> {
            // Your existing back arrow functionality
            Intent intent = new Intent(AddTeacherActivity.this, RegisterteacherAdminActivity.class);
            startActivity(intent);
            finish(); // Finishes the current activity, removing it from the back stack
        });

        saveButton.setOnClickListener(v -> saveTeacher());
    }

    private void saveTeacher() {
        String fullName = fullNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String department = departmentEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String qualification = qualificationEditText.getText().toString().trim();
        String subjects = subjectsEditText.getText().toString().trim();

        // Validate input
        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(department) || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(qualification) || TextUtils.isEmpty(subjects)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show loading message
        Toast.makeText(this, "Saving teacher...", Toast.LENGTH_SHORT).show();

        // Create Teacher object
        Teacher teacher = new Teacher(fullName, email, department, phone, qualification, subjects);

        // Save to Firebase
        Log.d(TAG, "Attempting to save teacher: " + teacher.getFullName());

        db.collection("teachers")
                .add(teacher)
                .addOnCompleteListener(new OnCompleteListener<com.google.firebase.firestore.DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.firestore.DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Teacher added successfully with ID: " + task.getResult().getId());
                            // Show success message
                            Toast.makeText(AddTeacherActivity.this, "Teacher added successfully!", Toast.LENGTH_LONG).show();

                            // Clear the form fields
                            clearFormFields();

                            // Navigate back to teacher list
                            Intent intent = new Intent(AddTeacherActivity.this, RegisterteacherAdminActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Exception exception = task.getException();
                            Log.e(TAG, "Error adding teacher", exception);

                            String errorMessage = "Error adding teacher. ";
                            if (exception != null) {
                                errorMessage += "Reason: " + exception.getMessage();
                                Log.e(TAG, "Exception details: " + exception.toString());

                                // Provide specific error messages based on exception type
                                if (exception.getMessage().contains("permission")) {
                                    errorMessage = "Permission denied. Please check Firebase security rules.";
                                } else if (exception.getMessage().contains("network")) {
                                    errorMessage = "Network error. Please check your internet connection.";
                                } else if (exception.getMessage().contains("quota")) {
                                    errorMessage = "Firebase quota exceeded. Please try again later.";
                                }
                            }

                            Toast.makeText(AddTeacherActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void clearFormFields() {
        fullNameEditText.setText("");
        emailEditText.setText("");
        departmentEditText.setText("");
        phoneEditText.setText("");
        qualificationEditText.setText("");
        subjectsEditText.setText("");
    }

    private void testFirebaseConnection() {
        // Test if Firebase is properly connected
        db.collection("test")
                .document("connection")
                .set(new java.util.HashMap<String, Object>() {{
                    put("timestamp", new java.util.Date());
                    put("test", "connection");
                }})
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Firebase connection test successful");
                        // Clean up test document
                        db.collection("test").document("connection").delete();
                    } else {
                        Log.e(TAG, "Firebase connection test failed", task.getException());
                        Toast.makeText(AddTeacherActivity.this,
                                "Firebase connection failed. Please check your internet connection and Firebase setup.",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}