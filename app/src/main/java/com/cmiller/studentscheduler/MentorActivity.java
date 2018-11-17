package com.cmiller.studentscheduler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.cmiller.studentscheduler.model.Mentor;

public class MentorActivity extends AppCompatActivity {

    private DBHandler dbHandler;
    private int courseID;
    private TextView mentorName;
    private TextView mentorPhone;
    private TextView mentorEmail;
    private String spinnerStart;
    private String courseEnd;
    private String courseStart;
    private String courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor);

        dbHandler = new DBHandler(this, null, null, 1);

        mentorName = findViewById(R.id.lvMentorName);
        mentorPhone = findViewById(R.id.mentorPhone);
        mentorEmail = findViewById(R.id.mentorEmail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            courseID = extras.getInt("editCourseID");
            courseName = (extras.getString("editCourseTitle"));
            courseStart = (extras.getString("editCourseStart"));
            courseEnd = (extras.getString("editCourseEnd"));
            courseID = extras.getInt("editCourseID");

            spinnerStart = extras.getString("editCourseStatus");
        }

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
                Mentor mentor = new Mentor(
                    mentorName.getText().toString(),
                    mentorPhone.getText().toString(),
                    mentorEmail.getText().toString(),
                    courseID
                );
                dbHandler.addMentor(mentor);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
