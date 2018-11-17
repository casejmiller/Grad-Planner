package com.cmiller.studentscheduler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.cmiller.studentscheduler.model.Course;

import java.util.Calendar;

public class EditCourseActivity extends AppCompatActivity {

    private int courseID;
    String spinnerStart;
    private TextView courseStart;
    private TextView courseEnd;
    private TextView courseName;
    private Spinner courseStatus;
    private DatePickerDialog.OnDateSetListener startDateListener;
    private DatePickerDialog.OnDateSetListener endDateListener;

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);


        courseStart = (TextView) findViewById(R.id.courseStart);
        courseEnd = (TextView) findViewById(R.id.courseEnd);
        courseName = (TextView) findViewById(R.id.courseName);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            courseName.setText(extras.getString("editCourseTitle"));
            courseStart.setText(extras.getString("editCourseStart"));
            courseEnd.setText(extras.getString("editCourseEnd"));
            courseID = extras.getInt("editCourseID");

            spinnerStart = extras.getString("editCourseStatus");

            Log.d("one", String.valueOf(courseID));
        }

        courseStatus = (Spinner) findViewById(R.id.courseStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseStatus.setAdapter(adapter);
        if (spinnerStart != null) {
            int spinnerPosition = adapter.getPosition(spinnerStart);
            courseStatus.setSelection(spinnerPosition);
        }

        dbHandler = new DBHandler(this, null, null, 1);

        courseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditCourseActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        startDateListener,
                        year, month, day);
                dialog.show();
            }
        });

        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = month + 1 + "/" + day + "/" + year;
                courseStart.setText(date);
            }
        };

        courseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditCourseActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        endDateListener,
                        year, month, day);
                dialog.show();
            }
        });

        endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = month + 1 + "/" + day + "/" + year;
                courseEnd.setText(date);
            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSave:
                Course course = new Course(
                        courseID,
                        courseName.getText().toString(),
                        courseStatus.getSelectedItem().toString(),
                        courseStart.getText().toString(),
                        courseEnd.getText().toString()
                );

                if(courseID > 0){
                    dbHandler.updateCourse(course);
                } else {
                    dbHandler.addCourse(course);
                }
                startActivity(new Intent(this, CoursesActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
