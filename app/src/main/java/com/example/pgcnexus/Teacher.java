package com.example.pgcnexus;

public class Teacher {
    private String id;
    private String fullName;
    private String email;
    private String department;
    private String phone;
    private String qualification;
    private String subjects;

    // Default constructor required for Firebase
    public Teacher() {
    }

    public Teacher(String fullName, String email, String department, String phone, String qualification, String subjects) {
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.phone = phone;
        this.qualification = qualification;
        this.subjects = subjects;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }
} 