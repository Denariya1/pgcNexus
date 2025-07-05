package com.example.pgcnexus;

import android.animation.ObjectAnimator;
import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get references to the views
        ImageView logo = findViewById(R.id.logo);
        TextView tagline = findViewById(R.id.tagline);
        Button btnGetStarted = findViewById(R.id.btnGetStarted);

        // Make the tagline initially invisible
        tagline.setAlpha(0f);

        // Logo animation (Bottom to Top)
        ObjectAnimator logoAnimator = ObjectAnimator.ofFloat(logo, "translationY", 1000f, 0f);
        logoAnimator.setDuration(1000);  // 1 second duration

        // Fade-in animation for logo
        ObjectAnimator logoFadeIn = ObjectAnimator.ofFloat(logo, "alpha", 0f, 1f);
        logoFadeIn.setDuration(1000);  // 1 second duration

        // Start the logo animations together
        logoAnimator.start();
        logoFadeIn.start();

        // Listener for when the logo animation ends
        logoAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                // Start the tagline animation after the logo animation ends

                // Tagline animation (Left to Right)
                ObjectAnimator textAnimator = ObjectAnimator.ofFloat(tagline, "translationX", -1000f, 0f);
                textAnimator.setDuration(800);  // 1 second duration
                textAnimator.start();

                // Fade-in animation for tagline
                ObjectAnimator textFadeIn = ObjectAnimator.ofFloat(tagline, "alpha", 0f, 1f);
                textFadeIn.setDuration(1000);  // 1 second duration
                textFadeIn.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        // Button click listener to navigate to SignIn Activity
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to SignIn Activity
                Intent intent = new Intent(MainActivity.this, SignIn.class);
                startActivity(intent);
            }
        });
    }
}
