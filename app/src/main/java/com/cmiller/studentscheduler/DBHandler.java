package com.cmiller.studentscheduler;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;

import com.cmiller.studentscheduler.model.Assessment;
import com.cmiller.studentscheduler.model.Course;
import com.cmiller.studentscheduler.model.Mentor;
import com.cmiller.studentscheduler.model.Term;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scheduler.db";


    private static final String TABLE_TERM = "term";
    private static final String TERM_ID = "term_id";
    private static final String TERM_TITLE = "title";
    private static final String TERM_START = "start_date";
    private static final String TERM_END = "end_date";

    private static final String TABLE_COURSE = "course";
    private static final String COURSE_ID = "course_id";
    private static final String COURSE_TITLE = "title";
    private static final String COURSE_STATUS = "status";
    private static final String COURSE_START = "start_date";
    private static final String COURSE_END = "end_date";
    private static final String COURSE_NOTE = "note";
    private static final String COURSE_TERM_ID = "term_id";

    private static final String TABLE_ASSESSMENT = "assessment";
    private static final String ASSESSMENT_ID = "assessment_id";
    private static final String ASSESSMENT_TITLE = "title";
    private static final String ASSESSMENT_TYPE = "type";
    private static final String ASSESSMENT_GOAL= "goal_date";
    private static final String ASSESSMENT_COURSE_ID = "course_id";

    private static final String TABLE_MENTOR = "mentor";
    private static final String MENTOR_ID = "mentor_id";
    private static final String MENTOR_NAME = "name";
    private static final String MENTOR_PHONE = "phone";
    private static final String MENTOR_EMAIL = "email";
    private static final String MENTOR_COURSE_ID = "course_id";



    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_TERM + "(" +
                        TERM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        TERM_TITLE + " TEXT NOT NULL," +
                        TERM_START + " TEXT NOT NULL," +
                        TERM_END + " TEXT NOT NULL);");

        db.execSQL(
                "CREATE TABLE " + TABLE_COURSE + "(" +
                        COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COURSE_TITLE + " TEXT NOT NULL," +
                        COURSE_STATUS + " TEXT," +
                        COURSE_START + " TEXT NOT NULL," +
                        COURSE_END + " TEXT NOT NULL," +
                        COURSE_NOTE + " TEXT," +
                        COURSE_TERM_ID + " INTEGER," +
                        "FOREIGN KEY(" + COURSE_TERM_ID + ") " +
                        "REFERENCES " + TABLE_TERM + "(" + TERM_ID + "));");


        db.execSQL(
                "CREATE TABLE " + TABLE_ASSESSMENT + "(" +
                        ASSESSMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ASSESSMENT_TITLE + " TEXT NOT NULL," +
                        ASSESSMENT_TYPE + " TEXT NOT NULL," +
                        ASSESSMENT_GOAL + " TEXT NOT NULL," +
                        ASSESSMENT_COURSE_ID + " INTEGER," +
                        "FOREIGN KEY(" + ASSESSMENT_COURSE_ID + ") " +
                        "REFERENCES " + TABLE_COURSE + "(" + COURSE_ID + "));");

        db.execSQL(
                "CREATE TABLE " + TABLE_MENTOR + "(" +
                        MENTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        MENTOR_NAME + " TEXT NOT NULL," +
                        MENTOR_PHONE + " TEXT NOT NULL," +
                        MENTOR_EMAIL + " TEXT NOT NULL," +
                        MENTOR_COURSE_ID + " INTEGER," +
                        "FOREIGN KEY(" + MENTOR_COURSE_ID + ") " +
                        "REFERENCES " + TABLE_COURSE + "(" + COURSE_ID + "));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERM + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSESSMENT + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENTOR + ";");
        onCreate(db);
    }

    public void addTerm(Term term) {
        ContentValues values = new ContentValues();
        values.put(TERM_TITLE, term.getTitle());
        values.put(TERM_START, term.getStart());
        values.put(TERM_END, term.getEnd());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_TERM, null, values);
        db.close();
    }

    public void updateTerm(Term term) {
        ContentValues values = new ContentValues();
        values.put(TERM_TITLE, term.getTitle());
        values.put(TERM_START, term.getStart());
        values.put(TERM_END, term.getEnd());
        String where = TERM_ID + " = " + term.getTermID();
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_TERM, values, where ,null);
        db.close();
    }

    public void addCourse(Course course) {
        ContentValues values = new ContentValues();
        values.put(COURSE_TITLE, course.getTitle());
        values.put(COURSE_STATUS, course.getStatus());
        values.put(COURSE_START, course.getStart());
        values.put(COURSE_END, course.getEnd());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_COURSE, null, values);
        db.close();
    }

    public void updateCourse(Course course) {
        ContentValues values = new ContentValues();
        values.put(COURSE_TITLE, course.getTitle());
        values.put(COURSE_STATUS, course.getStatus());
        values.put(COURSE_START, course.getStart());
        values.put(COURSE_END, course.getEnd());
        String where = COURSE_ID + " = " + course.getCourseID();
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_COURSE, values, where, null);
        db.close();
    }

    public void addAssessment(Assessment assessment) {
        ContentValues values = new ContentValues();
        values.put(ASSESSMENT_TITLE, assessment.getTitle());
        values.put(ASSESSMENT_TYPE, assessment.getType());
        values.put(ASSESSMENT_GOAL, assessment.getGoal());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ASSESSMENT, null, values);
        db.close();
    }

    public void updateAssessment(Assessment assessment) {
        ContentValues values = new ContentValues();
        values.put(ASSESSMENT_TITLE, assessment.getTitle());
        values.put(ASSESSMENT_TYPE, assessment.getType());
        values.put(ASSESSMENT_GOAL, assessment.getGoal());
        String where = ASSESSMENT_ID + " = " + assessment.getAssessmentID();
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_ASSESSMENT, values, where, null);
        db.close();
    }

    public void addMentor(Mentor mentor) {
        ContentValues values = new ContentValues();
        values.put(MENTOR_NAME, mentor.getMentorName());
        values.put(MENTOR_PHONE, mentor.getMentorPhone());
        values.put(MENTOR_EMAIL, mentor.getMentorEmail());
        values.put(MENTOR_COURSE_ID, mentor.getCourseID());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_MENTOR, null, values);
        db.close();
    }
    public ArrayList<Term> getTermList() {
        ArrayList<Term> termList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_TERM, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            int termID = res.getInt(0);
            String title = res.getString(1);
            String start = res.getString(2);
            String end = res.getString(3);
            termList.add(new Term(termID, title, start, end));
            res.moveToNext();
        }
        res.close();
        return termList;
    }

    public ArrayList<Mentor> getMentorList(int courseID) {
        ArrayList<Mentor> mentorList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_MENTOR +
                " WHERE " + MENTOR_COURSE_ID + " = ?", new String[]{String.valueOf(courseID)});

        res.moveToFirst();
        while (!res.isAfterLast()) {
            int mentorID = res.getInt(0);
            String mentorName = res.getString(1);
            String mentorPhone = res.getString(2);
            String mentorEmail = res.getString(3);
            mentorList.add(new Mentor(mentorID, mentorName, mentorPhone, mentorEmail));
            res.moveToNext();
        }
        res.close();
        return mentorList;
    }


    public ArrayList<Course> getCourseList() {
        ArrayList<Course> courseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_COURSE, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            int courseID = res.getInt(0);
            String title = res.getString(1);
            String status = res.getString(2);
            String start = res.getString(3);
            String end = res.getString(4);
            courseList.add(new Course(courseID, title, status, start, end));
            res.moveToNext();
        }
        res.close();
        return courseList;
    }

    public ArrayList<Assessment> getAssessmentList() {
        ArrayList<Assessment> assessmentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_ASSESSMENT, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            int assessmentID = res.getInt(0);
            String title = res.getString(1);
            String type = res.getString(2);
            String goal = res.getString(3);
            assessmentList.add(new Assessment(assessmentID, title, type, goal));
            res.moveToNext();
        }
        res.close();
        return assessmentList;
    }


    public ArrayList<Course> getTermCourseList(int termID) {
        ArrayList<Course> courseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_COURSE +
                " WHERE " + COURSE_TERM_ID + " = ?", new String[]{String.valueOf(termID)});
        res.moveToFirst();
        while (!res.isAfterLast()) {
            int courseID = res.getInt(0);
            String title = res.getString(1);
            String status = res.getString(2);
            String start = res.getString(3);
            String end = res.getString(4);
            courseList.add(new Course(courseID, title, status, start, end));
            res.moveToNext();
        }
        res.close();
        return courseList;
    }

    public ArrayList<Assessment> getCourseAssessmentList(int courseID) {
        ArrayList<Assessment> assessmentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_ASSESSMENT +
                " WHERE " + ASSESSMENT_COURSE_ID + " = ?", new String[]{String.valueOf(courseID)});
        res.moveToFirst();
        while (!res.isAfterLast()) {
            int assessmentID = res.getInt(0);
            String title = res.getString(1);
            String type = res.getString(2);
            String goal = res.getString(3);
            assessmentList.add(new Assessment(assessmentID, title, type, goal));
            res.moveToNext();
        }
        res.close();
        return assessmentList;
    }

    public ArrayList<Course> getSpinnerCourses() {
        ArrayList<Course> courseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_COURSE +
                " WHERE " + COURSE_TERM_ID + " IS NULL", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            int courseID = res.getInt(0);
            String title = res.getString(1);
            String status = res.getString(2);
            String start = res.getString(3);
            String end = res.getString(4);
            courseList.add(new Course(courseID, title, status, start, end));
            res.moveToNext();
        }
        res.close();
        return courseList;
    }

    public ArrayList<Assessment> getSpinnerAssessments() {
        ArrayList<Assessment> assessmentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_ASSESSMENT +
                " WHERE " + ASSESSMENT_COURSE_ID + " IS NULL", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            int assessmentID = res.getInt(0);
            String title = res.getString(1);
            String type = res.getString(2);
            String goal = res.getString(3);
            assessmentList.add(new Assessment(assessmentID, title, type, goal));
            res.moveToNext();
        }
        res.close();
        return assessmentList;
    }

    public void addCourseToTerm(Course course, int termID){
        int courseID = course.getCourseID();
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_COURSE + " SET " + COURSE_TERM_ID + " = " + termID +
                " WHERE " + COURSE_ID + " = " + courseID;
        db.execSQL(query);
        db.close();
    }

    public void addAssessmentToCourse(Assessment assessment, int courseID){
        int assessmentID = assessment.getAssessmentID();
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_ASSESSMENT + " SET " + ASSESSMENT_COURSE_ID + " = " + courseID +
                " WHERE " + ASSESSMENT_ID + " = " + assessmentID;
        db.execSQL(query);
        db.close();
    }

    public void deleteAssessment(int assessID){
        SQLiteDatabase db = getWritableDatabase();
        String query = "delete from " + TABLE_ASSESSMENT + " WHERE " + ASSESSMENT_ID + " = " + assessID;
        db.execSQL(query);
        db.close();
    }

    public void deleteCourse(int courseID){
        SQLiteDatabase db = getWritableDatabase();
        String query = "delete from " + TABLE_COURSE + " WHERE " + COURSE_ID + " = " + courseID;
        db.execSQL(query);
        db.close();
    }

    public void deleteTerm(int termID){
        SQLiteDatabase db = getWritableDatabase();
        String query = "delete from " + TABLE_TERM + " WHERE " + TERM_ID + " = " + termID;
        db.execSQL(query);
        db.close();
    }

    public String getNotes(int courseID){
        String note = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT " + COURSE_NOTE + " FROM " + TABLE_COURSE +
                " WHERE " + COURSE_ID + " = " + courseID, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            if(res.getString(0) == null){
                note = note + "Enter Note...";
            } else {
                note = note + res.getString(0);
            }
            res.moveToNext();
        }
        res.close();
        return note;
    }

    public void saveNotes(String note, int courseID) {
        ContentValues values = new ContentValues();
        values.put(COURSE_NOTE, note);
        String where = COURSE_ID + " = " + courseID;
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_COURSE, values, where, null);
        db.close();
    }

}
