package com.example.pgcnexus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BbaActivity extends AppCompatActivity {

    // Arrays to hold semester headers, course lists, and toggle icons
    private final LinearLayout[] semesterHeaders = new LinearLayout[8];
    private final LinearLayout[] courseListLayouts = new LinearLayout[8];
    private final ImageView[] toggleIcons = new ImageView[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bba);

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Loop through 8 semesters and assign views dynamically
        for (int i = 0; i < 8; i++) {
            int semesterNum = i + 1;

            int headerId = getResources().getIdentifier("semester_header" + semesterNum, "id", getPackageName());
            int layoutId = getResources().getIdentifier("course_list_layout" + semesterNum, "id", getPackageName());
            int iconId = getResources().getIdentifier("toggle_icon" + semesterNum, "id", getPackageName());

            semesterHeaders[i] = findViewById(headerId);
            courseListLayouts[i] = findViewById(layoutId);
            toggleIcons[i] = findViewById(iconId);

            // Set initial state to collapsed
            courseListLayouts[i].setVisibility(View.GONE);
            toggleIcons[i].setImageResource(android.R.drawable.arrow_down_float);

            int finalI = i;

            // Set toggle functionality
            semesterHeaders[i].setOnClickListener(view -> {
                boolean isVisible = courseListLayouts[finalI].getVisibility() == View.VISIBLE;
                courseListLayouts[finalI].setVisibility(isVisible ? View.GONE : View.VISIBLE);
                toggleIcons[finalI].setImageResource(isVisible
                        ? android.R.drawable.arrow_down_float
                        : android.R.drawable.arrow_up_float);
            });
        }

        // Apply Now container click functionality
        LinearLayout applyNowContainer = findViewById(R.id.applyNowContainer);
        applyNowContainer.setOnClickListener(v -> {
            String url = "https://onlineadmissions.pgc.edu.pk/#/admission";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
    }
}
