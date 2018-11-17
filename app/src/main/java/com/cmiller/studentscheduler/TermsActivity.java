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

import com.cmiller.studentscheduler.adapters.TermAdapter;
import com.cmiller.studentscheduler.model.Term;

import java.util.ArrayList;

public class TermsActivity extends AppCompatActivity {

    private Term selectedTerm;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        dbHandler = new DBHandler(this, null, null, 1);

        setTermListView();
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
                startActivity(new Intent(this, EditTermsActivity.class));
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


    private void setTermListView() {
        ListView listView = (ListView) findViewById(R.id.lvTerms);
        ArrayList<Term> termList = (ArrayList<Term>) dbHandler.getTermList();
        ArrayAdapter adapter = new TermAdapter(this, termList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Term selectedTerm = (Term) adapterView.getAdapter().getItem(i);

                        String title = selectedTerm.getTitle();
                        int termID = selectedTerm.getTermID();
                        String start = selectedTerm.getStart();
                        String end = selectedTerm.getEnd();

                        Intent intent = new Intent(getBaseContext(), TermsDetailedActivity.class);

                        intent.putExtra("termID", termID);
                        intent.putExtra("termTitle", title);
                        intent.putExtra("termStart", start);
                        intent.putExtra("termEnd", end);
                        startActivity(intent);
                    }
                }
        );
    }


}


