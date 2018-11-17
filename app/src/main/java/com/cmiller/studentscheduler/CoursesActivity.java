package com.cmiller.studentscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cmiller.studentscheduler.adapters.CourseAdapter;
import com.cmiller.studentscheduler.model.Course;

import java.util.ArrayList;

public class CoursesActivity extends AppCompatActivity {

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        dbHandler = new DBHandler(this, null, null, 1);
        setCourseListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAdd:
                startActivity(new Intent(this, EditCourseActivity.class));
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

    private void setCourseListView() {
        ListView listView = (ListView) findViewById(R.id.lvCourseList);
        ArrayList<Course> courseList = (ArrayList<Course>) dbHandler.getCourseList();
        ArrayAdapter adapter = new CourseAdapter(this, courseList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(
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
