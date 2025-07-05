package com.example.pgcnexus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class RegisteredCoursesActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_courses);

        // Initializing ViewPager and TabLayout
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        // Set up the ViewPager Adapter
        SchemeOfStudyAdapter schemeOfStudyAdapter = new SchemeOfStudyAdapter(getSupportFragmentManager(), 8); // 8 semesters
        viewPager.setAdapter(schemeOfStudyAdapter);

        // Setting up the TabLayout with the ViewPager
        tabLayout.setupWithViewPager(viewPager);
    }

    // Adapter for handling semesters and fragments
    class SchemeOfStudyAdapter extends FragmentStatePagerAdapter {
        private int numOfTabs;

        public SchemeOfStudyAdapter(FragmentManager fm, int numOfTabs) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.numOfTabs = numOfTabs;
        }

        @Override
        public int getCount() {
            return numOfTabs; // 8 semesters
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt("semester", position + 1); // Passing semester number to fragment

            SemesterFragment fragment = new SemesterFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Display "Semester 1", "Semester 2", etc. as tab titles
            return "Semester " + (position + 1);
        }
    }

    // Fragment for each semester
    public static class SemesterFragment extends Fragment {
        ListView coursesListView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_semester, container, false);
            coursesListView = view.findViewById(R.id.coursesListView);

            // Set the background with the rounded border for ListView
            coursesListView.setBackgroundResource(R.drawable.rounded_border);

            // Get the semester number passed from the adapter
            int semester = getArguments().getInt("semester", 1);
            updateCourseList(semester);

            return view;
        }

        // Method to update courses based on the selected semester
        private void updateCourseList(int semester) {
            String[] courses;

            // Assign courses based on the semester
            switch (semester) {
                case 1:
                    courses = new String[]{
                            "ENGL101: English-I (Credit Hours: 3)",
                            "ISLM101: Islamic Studies (Credit Hours: 3)",
                            "COMP101: Introduction to Computer (Credit Hours: 3)",
                            "POL101: Principles of Political Science (Credit Hours: 3)",
                            "MATH100: General Mathematics (Credit Hours: 3)",
                            "MCOM101: Introduction to Mass Communication (Credit Hours: 3)"
                    };
                    break;
                case 2:
                    courses = new String[]{
                            "PHYS101: Physics-I (Credit Hours: 3)",
                            "CHEM101: Chemistry-I (Credit Hours: 3)",
                            "MATH102: Advanced Mathematics (Credit Hours: 3)",
                            "CS101: Introduction to Programming (Credit Hours: 3)",
                            "ECON101: Microeconomics (Credit Hours: 3)"
                    };
                    break;
                case 3:
                    courses = new String[]{
                            "STAT101: Statistics (Credit Hours: 3)",
                            "CS201: Data Structures (Credit Hours: 3)",
                            "CS202: Computer Architecture (Credit Hours: 3)",
                            "MATH103: Discrete Mathematics (Credit Hours: 3)",
                            "BUS101: Business Communication (Credit Hours: 3)"
                    };
                    break;
                case 4:
                    courses = new String[]{
                            "CS301: Object-Oriented Programming (Credit Hours: 3)",
                            "CS302: Database Systems (Credit Hours: 3)",
                            "MATH104: Linear Algebra (Credit Hours: 3)",
                            "ACCT101: Financial Accounting (Credit Hours: 3)",
                            "CS303: Software Engineering (Credit Hours: 3)"
                    };
                    break;
                case 5:
                    courses = new String[]{
                            "CS401: Operating Systems (Credit Hours: 3)",
                            "CS402: Software Development (Credit Hours: 3)",
                            "CS403: Web Development (Credit Hours: 3)",
                            "CS404: Mobile App Development (Credit Hours: 3)",
                            "MATH105: Numerical Methods (Credit Hours: 3)"
                    };
                    break;
                case 6:
                    courses = new String[]{
                            "CS501: Cloud Computing (Credit Hours: 3)",
                            "CS502: Artificial Intelligence (Credit Hours: 3)",
                            "CS503: Data Science (Credit Hours: 3)",
                            "CS504: Computer Networks (Credit Hours: 3)",
                            "MATH106: Computational Mathematics (Credit Hours: 3)"
                    };
                    break;
                case 7:
                    courses = new String[]{
                            "CS601: Machine Learning (Credit Hours: 3)",
                            "CS602: Distributed Systems (Credit Hours: 3)",
                            "CS603: Data Visualization (Credit Hours: 3)",
                            "CS604: Cybersecurity (Credit Hours: 3)",
                            "CS605: Research Methods (Credit Hours: 3)"
                    };
                    break;
                case 8:
                    courses = new String[]{
                            "CS701: Big Data Analytics (Credit Hours: 3)",
                            "CS702: Cloud Security (Credit Hours: 3)",
                            "CS703: Internet of Things (Credit Hours: 3)",
                            "CS704: Blockchain Technology (Credit Hours: 3)",
                            "CS705: Capstone Project (Credit Hours: 3)"
                    };
                    break;
                default:
                    courses = new String[]{"No courses available for this semester"};
            }

            // Set up the ListView with the courses
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, courses);
            coursesListView.setAdapter(adapter);
        }
    }
}
