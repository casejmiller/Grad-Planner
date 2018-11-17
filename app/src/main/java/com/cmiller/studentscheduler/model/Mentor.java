package com.cmiller.studentscheduler.model;

public class Mentor {
    private int mentorID;
    private String mentorName;
    private String mentorPhone;
    private String mentorEmail;
    private int courseID;

    public Mentor() {

    }

    public Mentor(String mentorName, String mentorPhone, String mentorEmail, int courseID){
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.courseID = courseID;
    }


    public Mentor(int mentorID, String mentorName, String mentorPhone, String mentorEmail){
        this.mentorID = mentorID;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
    }


    public int getMentorID() {
        return mentorID;
    }

    public void setMentorID(int mentorID) {
        this.mentorID = mentorID;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorPhone() {
        return mentorPhone;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}