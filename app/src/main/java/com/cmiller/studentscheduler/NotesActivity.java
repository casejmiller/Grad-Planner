package com.cmiller.studentscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NotesActivity extends AppCompatActivity {

    private EditText notesEmail;
    private int courseID;
    private TextView notesTitle;
    private EditText notes;
    private String spinnerStart;
    private String courseEnd;
    private String courseStart;
    private String courseName;

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        notes = (EditText) findViewById(R.id.notes);
        notesTitle = (TextView) findViewById(R.id.notesTitle);
        notesEmail = (EditText) findViewById(R.id.notesEmail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            notesTitle.setText(extras.getString("editCourseTitle") + " Notes");
            courseID = extras.getInt("editCourseID");
            courseName = (extras.getString("editCourseTitle"));
            courseStart = (extras.getString("editCourseStart"));
            courseEnd = (extras.getString("editCourseEnd"));
            courseID = extras.getInt("editCourseID");

            spinnerStart = extras.getString("editCourseStatus");
        }

        dbHandler = new DBHandler(this, null, null, 1);

        notes.setText(dbHandler.getNotes(courseID));
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
                dbHandler.saveNotes(notes.getText().toString(), courseID);

                Intent intent = new Intent(getBaseContext(), CourseDetailedActivity.class);

                intent.putExtra("courseID", courseID);
                intent.putExtra("courseTitle", courseName);
                intent.putExtra("courseStatus", spinnerStart);
                intent.putExtra("courseStart", courseStart);
                intent.putExtra("courseEnd", courseEnd);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void sendNotes(View view){
        String[] recip = new String[] {notesEmail.getText().toString()};
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recip);
        intent.putExtra(Intent.EXTRA_SUBJECT, notesTitle.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, notes.getText().toString());

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }
}