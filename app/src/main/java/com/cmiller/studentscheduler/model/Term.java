package com.cmiller.studentscheduler.model;

public class Term {

    private int termID;
    private String title;
    private String start;
    private String end;


    public Term() {

    }



    public Term(int termID, String title, String start, String end) {
        this.termID = termID;
        this.title = title;
        this.start = start;
        this.end = end;
    }
    public Term(String title, String start, String end) {

        this.title = title;
        this.start = start;
        this.end = end;
    }


    public void setTermID(int termID) {
        this.termID = termID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getTermID() {
        return termID;
    }

    public String getTitle() {
        return title;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
