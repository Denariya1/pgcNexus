package com.example.pgcnexus;

import android.content.Intent;
import android.graphics.Typeface; // Import for Typeface
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity; // Import for Gravity
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
// No longer need android.graphics.Color if using 0xAARRGGBB format directly

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditstudentAdminActivity extends AppCompatActivity {

    private Spinner spinnerPrograms;
    private ImageView backArrow;
    private EditText searchBar;
    private LinearLayout studentListLayout;

    // Sample student data (replace with real data from database/API)
    private List<String> allStudents = new ArrayList<>(Arrays.asList(
            "Anush Khalid", "Tooba Tahir", "Ali Javed", "Ayesha Tahir",
            "Sarmad Javed", "Zainab Khan", "Faisal Mahmood", "Hira Zafar",
            "Imran Ali", "Javeria Khan"
    ));
    private List<String> filteredStudents = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.editstudent_admin);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.manageStudentsLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Views
        backArrow = findViewById(R.id.backArrow);
        spinnerPrograms = findViewById(R.id.spinner_programs);
        searchBar = findViewById(R.id.searchBar);
        studentListLayout = findViewById(R.id.studentListLayout);

        // --- Back Arrow Functionality ---
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditstudentAdminActivity.this, ManageStudentActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // --- Program Spinner Setup ---
        String[] programs = {"BSIT", "BBA", "BS Chem", "BSCS"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                programs
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrograms.setAdapter(adapter);

        // --- Initial Load of Students ---
        filteredStudents.addAll(allStudents);
        displayStudents(filteredStudents);


        // --- Search Bar Functionality ---
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterStudents(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed for this implementation
            }
        });
    }

    /**
     * Filters the list of students based on the search query
     * and updates the displayed list.
     * @param query The text to search for.
     */
    private void filterStudents(String query) {
        filteredStudents.clear();
        if (query.isEmpty()) {
            filteredStudents.addAll(allStudents); // If query is empty, show all students
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (String student : allStudents) {
                if (student.toLowerCase().contains(lowerCaseQuery)) {
                    filteredStudents.add(student);
                }
            }
        }
        displayStudents(filteredStudents);
    }

    /**
     * Dynamically creates and displays CardViews for each student in the given list.
     * @param students The list of student names to display.
     */
    private void displayStudents(List<String> students) {
        studentListLayout.removeAllViews(); // Clear existing views

        if (students.isEmpty()) {
            TextView noResultsText = new TextView(this);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            noResultsText.setLayoutParams(textParams);
            noResultsText.setText("No students found.");
            noResultsText.setTextSize(16);
            noResultsText.setPadding(0, dpToPx(20), 0, 0);
            noResultsText.setTextColor(getResources().getColor(android.R.color.darker_gray));
            noResultsText.setGravity(Gravity.CENTER_HORIZONTAL);
            studentListLayout.addView(noResultsText);
        } else {
            for (String studentName : students) {
                // Create a new CardView for each student
                CardView cardView = new CardView(this);
                LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                cardParams.setMargins(0, 0, 0, dpToPx(8)); // 8dp bottom margin
                cardView.setLayoutParams(cardParams);
                cardView.setRadius(dpToPx(12)); // 12dp corner radius
                cardView.setCardElevation(dpToPx(4)); // 4dp elevation
                // Corrected: Using a direct hex color value to avoid resource error
                cardView.setCardBackgroundColor(0xFFE0E0E0); // Light gray color (AARRGGBB format)


                // Create a LinearLayout inside the CardView
                LinearLayout innerLayout = new LinearLayout(this);
                innerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                innerLayout.setOrientation(LinearLayout.HORIZONTAL);
                innerLayout.setPadding(dpToPx(12), dpToPx(12), dpToPx(12), dpToPx(12)); // 12dp padding
                innerLayout.setGravity(Gravity.CENTER_VERTICAL);

                // Add Profile ImageView
                ImageView profileImage = new ImageView(this);
                LinearLayout.LayoutParams profileParams = new LinearLayout.LayoutParams(dpToPx(40), dpToPx(40));
                profileParams.setMarginEnd(dpToPx(12));
                profileImage.setLayoutParams(profileParams);
                profileImage.setImageResource(R.drawable.profile_button); // Make sure you have this drawable
                profileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                innerLayout.addView(profileImage);

                // Add Student Name TextView
                TextView studentNameTv = new TextView(this);
                LinearLayout.LayoutParams textWeightParams = new LinearLayout.LayoutParams(
                        0, // 0dp for width when weight is used
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1f // Weight to take remaining space
                );
                studentNameTv.setLayoutParams(textWeightParams);
                studentNameTv.setText(studentName);
                studentNameTv.setTextSize(16); // 16sp
                studentNameTv.setTypeface(null, Typeface.BOLD);
                studentNameTv.setTextColor(getResources().getColor(android.R.color.black));
                innerLayout.addView(studentNameTv);

                // Add Edit ImageView
                ImageView editImage = new ImageView(this);
                LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(dpToPx(24), dpToPx(24));
                iconParams.setMarginEnd(dpToPx(12));
                editImage.setLayoutParams(iconParams);
                editImage.setImageResource(R.drawable.edit); // Make sure you have this drawable
                editImage.setContentDescription("Edit");
                editImage.setOnClickListener(v -> Toast.makeText(this, "Edit: " + studentName, Toast.LENGTH_SHORT).show());
                innerLayout.addView(editImage);

                // Add Delete ImageView
                ImageView deleteImage = new ImageView(this);
                deleteImage.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(24), dpToPx(24)));
                deleteImage.setImageResource(R.drawable.delete); // Make sure you have this drawable
                deleteImage.setContentDescription("Delete");
                deleteImage.setOnClickListener(v -> Toast.makeText(this, "Delete: " + studentName, Toast.LENGTH_SHORT).show());
                innerLayout.addView(deleteImage);

                // Add the inner layout to the CardView
                cardView.addView(innerLayout);

                // Add the CardView to the studentListLayout
                studentListLayout.addView(cardView);
            }
        }
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}