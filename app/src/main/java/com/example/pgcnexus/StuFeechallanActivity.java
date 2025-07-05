package com.example.pgcnexus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class StuFeechallanActivity extends AppCompatActivity {

    private LinearLayout challanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_feechallan);

        // Initialize the LinearLayout where the challans will be added
        challanList = findViewById(R.id.challan_list);

        // Remove the template view (it's just for reference in XML)
        View template = findViewById(R.id.challan_item_template);
        // Example of adding challan entries dynamically
        addChallan("20250100919", "Paid", "Rs: 500", "2025-01-02", "2025-01-10", "2025-01-14", "Fall 2024");
        addChallan("20241104807", "Paid", "Rs: 18,625", "2024-11-25", "2024-12-17", "2024-12-17", "Fall 2024");
        addChallan("20241004200", "paid", "Rs: 5,700", "2024-10-22", "2024-11-02", "2025-04-02", "Fall 2024");
        addChallan("20241004244", "paid", "Rs: 5,700", "2024-10-22", "2024-11-02", "2025-08-02", "Fall 2024");
        addChallan("20241004312", "Unpaid", "Rs: 5,700", "2024-10-22", "2024-11-02", "Not Paid", "Fall 2024");
    }

    private void addChallan(String challanNumber, String status, String amount,
                            String issueDate, String dueDate, String paidDate, String semester) {
        // Inflate a new CardView instance
        CardView newChallan = (CardView) LayoutInflater.from(this)
                .inflate(R.layout.card_challan_item, challanList, false);

        // Get references to all views inside the inflated CardView
        TextView challanNumberText = newChallan.findViewById(R.id.challan_number);
        TextView paidStatusText = newChallan.findViewById(R.id.paid_status);
        TextView amountText = newChallan.findViewById(R.id.amount);
        TextView issueDateText = newChallan.findViewById(R.id.issue_date);
        TextView dueDateText = newChallan.findViewById(R.id.due_date);
        TextView paidDateText = newChallan.findViewById(R.id.paid_date);
        TextView semesterText = newChallan.findViewById(R.id.semester);

        // Set the data into the respective views
        challanNumberText.setText("Challan No: " + challanNumber);
        amountText.setText(amount);
        issueDateText.setText("Issue Date: " + issueDate);
        dueDateText.setText("Due Date: " + dueDate);
        paidDateText.setText("Paid Date: " + paidDate);
        semesterText.setText("Semester: " + semester);

        // Set the paid status with the corresponding color
        if ("Paid".equalsIgnoreCase(status)) {
            paidStatusText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            paidStatusText.setText("Paid");
        } else {
            paidStatusText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            paidStatusText.setText("Unpaid");
        }

        // Add the populated CardView to the LinearLayout
        challanList.addView(newChallan);
    }
}
