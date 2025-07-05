package com.example.pgcnexus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignIn extends AppCompatActivity {
    private FirebaseAuth mAuth;  // Firebase Auth instance
    private FirebaseFirestore db;
    private EditText emailEditText, passwordEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();  // Initialize Firebase Auth
        db = FirebaseFirestore.getInstance();

//        createUserWithRole("admin@example.com", "adminpassword", "admin");
//        createUserWithRole("teacher@example.com", "teacherpassword", "teacher");
//        createUserWithRole("student@example.com", "studentpassword", "student");

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);

        // Guest Button
        Button btnGuest = findViewById(R.id.btn_guest);
        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, GuestHomeActivity.class);
                startActivity(intent);
            }
        });

        // Admin Button
        Button btnAdmin = findViewById(R.id.btn_one);
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, AdminHomeActivity.class);
                startActivity(intent);
            }
        });

        // Student Button
        Button btnStudent = findViewById(R.id.btn_two);
        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                createUserWithRole("admin@example.com", "adminpassword", "admin");
                Intent intent = new Intent(SignIn.this, Student1Activity.class);
                startActivity(intent);
            }
        });

        // Teacher Button
        Button btnTeacher = findViewById(R.id.btn_three);
        btnTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, BoxActivity.class);
                startActivity(intent);
            }
        });
        // Sign In Button
        Button btnSignIn = findViewById(R.id.btn_SignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignIn.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                } else {
                    signInWithEmail(email, password);
                }
            }
        });
    }
    private void signInWithEmail(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        // Check if the user is signed in
                        if (user != null) {
                            // Now, fetch role and proceed accordingly
                            db.collection("users")
                                    .document(user.getUid())
                                    .get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                        String role = documentSnapshot.getString("role");
                                        if (role != null) {
                                            // Check the role and navigate accordingly
                                            navigateToRoleBasedActivity(role);
                                        } else {
                                            Log.e("SignIn", "Role not found for user");
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("SignIn", "Failed to get user role: " + e.getMessage());
                                    });
                        }
                    } else {
                        Log.e("SignIn", "Authentication failed: " + task.getException().getMessage());
                        Toast.makeText(SignIn.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void navigateToRoleBasedActivity(String role) {
        Intent intent;
        switch (role) {
            case "admin":
                intent = new Intent(SignIn.this, AdminHomeActivity.class);
                break;
            case "teacher":
                intent = new Intent(SignIn.this, BoxActivity.class);
                break;
            case "student":
                intent = new Intent(SignIn.this, Student1Activity.class);
                break;
            default:
                intent = new Intent(SignIn.this, GuestHomeActivity.class);
                break;
        }
        startActivity(intent);
    }

    private void createUserWithRole(String email, String password, String role) {
        System.out.println("Hello, World!");

        Log.e("CreateUser", "Attempting to create user with email: " + email);
        // Create a new user using Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();

                            // Define the user's role
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("role", role);

                            // Store the user's role in Firestore
                            db.collection("users").document(userId)
                                    .set(userData)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d("CreateUser", "User created with role: " + role);
//                                        Toast.makeText(CreateCredentialsActivity.this, "User created as " + role, Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("CreateUser", "Error setting user role: " + e.getMessage());
//                                        Toast.makeText(conte, "Error setting role", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    } else {
                        // If sign-up fails, show an error message
                        Log.e("CreateUser", "Error: " + task.getException());
//                        Toast.makeText(CreateCredentialsActivity.this, "Error creating user", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

