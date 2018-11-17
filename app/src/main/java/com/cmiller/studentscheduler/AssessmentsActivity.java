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

import com.cmiller.studentscheduler.adapters.AssessmentAdapter;
import com.cmiller.studentscheduler.model.Assessment;

import java.util.ArrayList;

public class AssessmentsActivity extends AppCompatActivity {

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);
        dbHandler = new DBHandler(this, null, null, 1);
        setAssessmentList();
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
                startActivity(new Intent(this, EditAssessmentActivity.class));
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

    private void setAssessmentList() {
        ListView listView = (ListView) findViewById(R.id.lvAssessments);
        ArrayList<Assessment> assessmentList = (ArrayList<Assessment>) dbHandler.getAssessmentList();
        ArrayAdapter adapter = new AssessmentAdapter(this, assessmentList);
        listView.setAdapter(adapter);
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
}
