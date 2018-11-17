package com.cmiller.studentscheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cmiller.studentscheduler.adapters.AssessmentAdapter;
import com.cmiller.studentscheduler.adapters.MentorAdapter;
import com.cmiller.studentscheduler.model.Assessment;
import com.cmiller.studentscheduler.model.Mentor;
import com.cmiller.studentscheduler.receivers.AlarmReceiver;

import java.util.ArrayList;
import java.util.Calendar;

public class CourseDetailedActivity extends AppCompatActivity {


    private int courseID;
    private TextView courseTitle;
    private TextView courseStatus;
    private TextView courseStart;
    private TextView courseEnd;
    private ListView listView;
    private ArrayAdapter lvAdapter;

    private CheckBox alertStart;
    private CheckBox alertEnd;

    private DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detailed);
        dbHandler = new DBHandler(this, null, null, 1);

        courseTitle = (TextView) findViewById(R.id.courseTitle);
        courseStatus = (TextView) findViewById(R.id.courseStatus);
        courseStart = (TextView) findViewById(R.id.courseStartDate);
        courseEnd = (TextView) findViewById(R.id.courseEndDate);

        alertStart = (CheckBox) findViewById(R.id.alertStart);
        alertEnd = (CheckBox) findViewById(R.id.alertEnd);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            courseTitle.setText(extras.getString("courseTitle"));
            courseStatus.setText(extras.getString("courseStatus"));
            courseStart.setText(extras.getString("courseStart"));
            courseEnd.setText(extras.getString("courseEnd"));
            courseID = extras.getInt("courseID");

            Log.d("one", String.valueOf(courseID));
        }

        setStartChecker();
        setEndChecker();
        alertStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("alertInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                if(alertStart.isChecked()){
                    editor.putString("alertStart"+String.valueOf(courseID), "ON");
                    editor.commit();
                    grabStartTime();
                }else{
                    editor.remove("alertStart"+String.valueOf(courseID));
                    editor.commit();
                    cancelStartAlarm();
                }
            }
        });

        alertEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("alertInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                if(alertEnd.isChecked()){
                    editor.putString("alertEnd"+String.valueOf(courseID), "ON");
                    editor.commit();
                    grabEndTime();
                }else{
                    editor.remove("alertEnd"+String.valueOf(courseID));
                    editor.commit();
                    cancelEndAlarm();
                }
            }
        });

        initializeUI();
        setMentorList();
        setCourseListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuEdit:
                optionEdit();
                return true;
            case R.id.menuDelete:
                optionDelete();
                return true;
            case R.id.menuHome:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.menuTerms:
                startActivity(new Intent(this, TermsActivity.class));
                return true;
            case R.id.menuCourses:
                startActivity(new Intent(this, CoursesActivity.class));
                return true;
            case R.id.menuAssessments:
                startActivity(new Intent(this, AssessmentsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setStartChecker(){
        SharedPreferences sp = getSharedPreferences("alertInfo", Context.MODE_PRIVATE);

        String key = "alertStart"+String.valueOf(courseID);
        String check = sp.getString(key, "");
        if(check.equals("ON")){
            alertStart.setChecked(true);
        } else {
            alertStart.setChecked(false);
        }
    }

    public void setEndChecker(){
        SharedPreferences sp = getSharedPreferences("alertInfo", Context.MODE_PRIVATE);

        String key = "alertEnd"+String.valueOf(courseID);
        String check = sp.getString(key, "");
        if(check.equals("ON")){
            alertEnd.setChecked(true);
        } else {
            alertEnd.setChecked(false);
        }
    }

    public void optionEdit(){

        Intent intent = new Intent(getBaseContext(), EditCourseActivity.class);

        intent.putExtra("editCourseID", courseID);
        intent.putExtra("editCourseTitle", courseTitle.getText().toString());
        intent.putExtra("editCourseStatus", courseStatus.getText().toString());
        intent.putExtra("editCourseStart", courseStart.getText().toString());
        intent.putExtra("editCourseEnd", courseEnd.getText().toString());

        startActivity(intent);
    }

    public void optionDelete(){
            dbHandler.deleteCourse(courseID);
            startActivity(new Intent(this, CoursesActivity.class));
    }


    private void initializeUI() {
        final Spinner addAssessmentSpinner = (Spinner) findViewById(R.id.addAssessmentSpinner);

        ArrayList<Assessment> assessments = dbHandler.getSpinnerAssessments();
        Assessment defaultFiller = new Assessment(0, "Add Assessment");

        ArrayAdapter<Assessment> adapter =
                new ArrayAdapter<Assessment>(this, android.R.layout.simple_spinner_dropdown_item, assessments){
                    @Override
                    public boolean isEnabled(int position){
                        if(position == 0)
                        { return false; }
                        else
                        { return true; }
                    }
                    @Override
                    public View getDropDownView(int position, View convertView,
                                                ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if(position == 0){ tv.setTextColor(Color.GRAY); }
                        else { tv.setTextColor(Color.BLACK); }
                        return view;
                    }
                };
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        assessments.add(0, defaultFiller);
        addAssessmentSpinner.setAdapter(adapter);
        addAssessmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Assessment selectedItem = (Assessment) parent.getItemAtPosition(position);
                if(position > 0){
                    dbHandler.addAssessmentToCourse(selectedItem, courseID);
                    addAssessmentSpinner.setSelection(0);
                    setCourseListView();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void toNotes(View view) {
        Intent intent = new Intent(getBaseContext(), NotesActivity.class);

        intent.putExtra("editCourseID", courseID);
        intent.putExtra("editCourseTitle", courseTitle.getText().toString());
        intent.putExtra("editCourseStatus", courseStatus.getText().toString());
        intent.putExtra("editCourseStart", courseStart.getText().toString());
        intent.putExtra("editCourseEnd", courseEnd.getText().toString());
        startActivity(intent);
    }

    public void toMentor(View view) {
        Intent intent = new Intent(getBaseContext(), MentorActivity.class);

        intent.putExtra("editCourseID", courseID);
        intent.putExtra("editCourseTitle", courseTitle.getText().toString());
        intent.putExtra("editCourseStatus", courseStatus.getText().toString());
        intent.putExtra("editCourseStart", courseStart.getText().toString());
        intent.putExtra("editCourseEnd", courseEnd.getText().toString());
        startActivity(intent);
    }

    private void setCourseListView() {
        listView = (ListView) findViewById(R.id.lvAssessmentsInCourse);
        ArrayList<Assessment> assessmentList = (ArrayList<Assessment>) dbHandler.getCourseAssessmentList(courseID);
        lvAdapter = new AssessmentAdapter(this, assessmentList);
        listView.setAdapter(lvAdapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Assessment selectedAssessment = (Assessment) adapterView.getAdapter().getItem(i);

                        String title = selectedAssessment.getTitle();
                        String type = selectedAssessment.getType();
                        String goal = selectedAssessment.getGoal();
                        int assessmentID = selectedAssessment.getAssessmentID();

                        Intent intent = new Intent(getBaseContext(), AssessmentDetailedActivity.class);

                        intent.putExtra("assessID", assessmentID);
                        intent.putExtra("assessTitle", title);
                        intent.putExtra("assessType", type);
                        intent.putExtra("assessGoal", goal);

                        startActivity(intent);
                    }
                }
        );
    }

    public void setMentorList() {
        listView = (ListView) findViewById(R.id.lvMentors);
        ArrayList<Mentor> mentorList = (ArrayList<Mentor>) dbHandler.getMentorList(courseID);
        lvAdapter = new MentorAdapter(this, mentorList);
        listView.setAdapter(lvAdapter);
    }

    @Override
    protected void onResume() {

        super.onResume();
        setMentorList();
    }


    public void grabStartTime() {
        String startDate = courseStart.getText().toString();
        String str[] = startDate.split("/");
        int month = Integer.parseInt(str[0]);
        int day = Integer.parseInt(str[1]);
        int year = Integer.parseInt(str[2]);
        Calendar cal = Calendar.getInstance();
        cal.set(
                year,
                month-1,
                day,
                8,00
        );
        setStartAlarm(cal.getTimeInMillis());
    }

    public void grabEndTime() {
        String startDate = courseEnd.getText().toString();
        String str[] = startDate.split("/");
        int month = Integer.parseInt(str[0]);
        int day = Integer.parseInt(str[1]);
        int year = Integer.parseInt(str[2]);
        Calendar cal = Calendar.getInstance();
        cal.set(
                year,
                month-1,
                day,
                8,00
        );
        setEndAlarm(cal.getTimeInMillis());
    }

    private void setStartAlarm(long timeInMillis) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        String alarmCode = "2";
        intent.putExtra("TITLE",  courseTitle.getText().toString());
        intent.putExtra("CODE", alarmCode);
        int uniqueInt = Integer.valueOf((alarmCode) + String.valueOf(courseID));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, uniqueInt, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        am.set(AlarmManager.RTC, timeInMillis, pendingIntent);

        String text = "You have set an alert for " + courseStart.getText().toString() + " for the start of " +
                courseStart.getText().toString();

        Toast.makeText(this, text, Toast.LENGTH_LONG).show();

    }

    private void setEndAlarm(long timeInMillis) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        String alarmCode = "3";
        intent.putExtra("TITLE",  courseTitle.getText().toString());
        intent.putExtra("CODE", alarmCode);
        int uniqueInt = Integer.valueOf((alarmCode) + String.valueOf(courseID));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, uniqueInt, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        am.set(AlarmManager.RTC, timeInMillis, pendingIntent);

        String text = "You have set an alert for " + courseEnd.getText().toString() + " for the end of " +
                courseEnd.getText().toString();
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();

    }

    private void cancelStartAlarm(){
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);

        String alarmCode = "2";
        int uniqueInt = Integer.valueOf((alarmCode) + String.valueOf(courseID));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, uniqueInt, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        am.cancel(pendingIntent);

        String text = "You have canceled an alert for the " + courseTitle.getText().toString() +
                " course";

        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void cancelEndAlarm(){
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);

        String alarmCode = "3";
        int uniqueInt = Integer.valueOf((alarmCode) + String.valueOf(courseID));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, uniqueInt, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        am.cancel(pendingIntent);

        String text = "You have canceled an alert for the " + courseTitle.getText().toString() +
                " course";

        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
