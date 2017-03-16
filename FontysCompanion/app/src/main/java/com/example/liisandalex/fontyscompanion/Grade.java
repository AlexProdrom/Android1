package com.example.liisandalex.fontyscompanion;

/**
 * Created by Liis on 15/03/2017.
 */

public class Grade {

    private String date;
    private String subject;
    private String subjectCode;
    private Number grade;
    private boolean passed;

    public Grade(String date, String subject, String subjectCode, Number grade, boolean passed) {
        this.date = date;
        this.subject = subject;
        this.subjectCode = subjectCode;
        this.grade = grade;
        this.passed = passed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Number getGrade() {
        return grade;
    }

    public void setGrade(Number grade) {
        this.grade = grade;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }


}
