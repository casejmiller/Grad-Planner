package com.cmiller.studentscheduler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cmiller.studentscheduler.adapters.CourseAdapter;
import com.cmiller.studentscheduler.model.Course;
import com.cmiller.studentscheduler.model.Term;

import java.util.ArrayList;

public class TermsDetailedActivity extends AppCompatActivity {


    private Term selectedTerm;
    private DBHandler dbHandler;
    private int termID;
    private TextView termTitle;
    private TextView termStart;
    private TextView termEnd;
    private ArrayAdapter lvAdapter;
    private ListView lvCourseList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_detailed);
        dbHandler = new DBHandler(this, null, null, 1);

        termStart = (TextView) findViewById(R.id.courseStartDate);
        termEnd = (TextView) findViewById(R.id.courseEndDate);
        termTitle = (TextView) findViewById(R.id.termTitle);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            termTitle.setText(extras.getString("termTitle"));
            termStart.setText(extras.getString("termStart"));
            termEnd.setText(extras.getString("termEnd"));
            termID = extras.getInt("termID");
            Log.d("one", String.valueOf(termID));
        }


        initializeUI();
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

    public void optionEdit(){

        Intent intent = new Intent(getBaseContext(), EditTermsActivity.class);

        intent.putExtra("editTermID", termID);
        intent.putExtra("editTermTitle", termTitle.getText().toString());
        intent.putExtra("editTermStart", termStart.getText().toString());
        intent.putExtra("editTermEnd", termEnd.getText().toString());

        startActivity(intent);
    }

    public void optionDelete(){
        if(lvAdapter.isEmpty()) {
            dbHandler.deleteTerm(termID);
            startActivity(new Intent(this, TermsActivity.class));
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(TermsDetailedActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Term cannot be deleted if courses are assigned to it.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }


    private void initializeUI() {
        final Spinner addCourseSpinner = (Spinner) findViewById(R.id.addCourseSpinner);

        ArrayList<Course> courses = dbHandler.getSpinnerCourses();
        Course defaultFiller = new Course(0, "Add Course");

        ArrayAdapter<Course> adapter =
                new ArrayAdapter<Course>(this, android.R.layout.simple_spinner_dropdown_item, courses){
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
        courses.add(0, defaultFiller);
        addCourseSpinner.setAdapter(adapter);
        addCourseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Course selectedItem = (Course) parent.getItemAtPosition(position);
                if(position > 0){
                    dbHandler.addCourseToTerm(selectedItem, termID);
                    addCourseSpinner.setSelection(0);
                    setCourseListView();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setCourseListView() {
        lvCourseList = (ListView) findViewById(R.id.lvCourseInTerm);
        ArrayList<Course> courseList = (ArrayList<Course>) dbHandler.getTermCourseList(termID);
        lvAdapter = new CourseAdapter(this, courseList);
        lvCourseList.setAdapter(lvAdapter);
        lvCourseList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Course selectedCourse = (Course) adapterView.getAdapter().getItem(i);

                        String title = selectedCourse.getTitle();
                        String status = selectedCourse.getStatus();
                        int courseID = selectedCourse.getCourseID();
                        String start = selectedCourse.getStart();
                        String end = selectedCourse.getEnd();

                        Intent intent = new Intent(getBaseContext(), CourseDetailedActivity.class);

                        intent.putExtra("courseID", courseID);
                        intent.putExtra("courseTitle", title);
                        intent.putExtra("courseStatus", status);
                        intent.putExtra("courseStart", start);
                        intent.putExtra("courseEnd", end);
                        startActivity(intent);
                    }
                }
        );
    }
}


