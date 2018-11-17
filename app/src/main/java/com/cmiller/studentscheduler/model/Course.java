package com.cmiller.studentscheduler.model;

public class Course {

    private int courseID;
    private String title;
    private String status;
    private String start;
    private String end;
    private String note;
    private int termID;

    public Course() {
    }

    public Course(int courseID, String title){
        this.courseID = courseID;
        this.title = title;
    }

    public Course(int courseID, String title, String status, String start, String end) {
        this.courseID = courseID;
        this.title = title;
        this.status = status;
        this.start = start;
        this.end = end;
    }

    public Course(String title, String status, String start, String end) {
        this.title = title;
        this.status = status;
        this.start = start;
        this.end = end;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getNote() {
        return note;
    }

    public int getTermID() {
        return termID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }


    @Override
    public String toString() {
        return title;
    }
}
