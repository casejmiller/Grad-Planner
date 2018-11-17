package com.cmiller.studentscheduler.model;

public class Assessment {

    private int assessmentID;
    private String title;
    private String type;
    private String goal;


    public Assessment() {

    }

    public Assessment(int assessmentID, String title){
        this.assessmentID = assessmentID;
        this.title = title;
    }

    public Assessment(String title, String type, String goal){
        this.title = title;
        this.type = type;
        this.goal = goal;
    }

    public Assessment(int assessmentID, String title, String type, String goal){
        this.assessmentID = assessmentID;
        this.title = title;
        this.type = type;
        this.goal = goal;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    @Override
    public String toString() {
        return title;
    }
}
