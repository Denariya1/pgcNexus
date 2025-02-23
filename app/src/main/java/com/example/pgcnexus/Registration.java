package com.example.pgcnexus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration); // Set the layout for RegistrationActivity

        // Find the TextView for the Sign In prompt
        TextView tvSignInPrompt = findViewById(R.id.tvSignInPrompt);

        // Full text of the TextView
        String fullText = "Already have an account? Sign In";

        // Create a SpannableString to style part of the text
        SpannableString spannableString = new SpannableString(fullText);

        // Define a ClickableSpan for the "Sign In" part of the text
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Open the SignIn activity
                Intent intent = new Intent(Registration.this, signupScreen.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.RED); // Set the text color to red
                ds.setUnderlineText(false); // Remove underline
            }
        };

        Button guestButton = findViewById(R.id.btn_guest);
        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, GuestHomeActivity.class);
                startActivity(intent);
            }
        });

        // Apply the ClickableSpan to the "Sign In" part of the text
        int startIndex = fullText.indexOf("Sign In");
        int endIndex = startIndex + "Sign In".length();
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the SpannableString to the TextView
        tvSignInPrompt.setText(spannableString);
        tvSignInPrompt.setMovementMethod(LinkMovementMethod.getInstance()); // Make the text clickable
    }
}