package android.melissaeppersonc196.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.melissaeppersonc196.scheduler.Database.SchedulerRepository;
import android.melissaeppersonc196.scheduler.Entity.CoursesEntity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.melissaeppersonc196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AssessmentListActivity extends AppCompatActivity {

    int ID;
    String title;
    String type;
    String endDate;
    EditText editTitle;
    EditText editType;
    EditText editEndDate;
    CoursesEntity currentCourse;
    public static int assessmentCount;
    public static int alertCount;

    private SchedulerRepository schedulerRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        schedulerRepository = new SchedulerRepository(getApplication());
        schedulerRepository.getAllAssessments();
        RecyclerView recyclerView = findViewById(R.id.assessmentListText);

        //this sets the activity_assesssment_list layout to show all assessments
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessmentText(schedulerRepository.getAllAssessments());


    }

    public void assessmentViewScreen(View view) {
        Intent intent = new Intent(AssessmentListActivity.this, AssessmentViewActivity.class);
        startActivity(intent);
    }

    public void addNewAssessment(View view) {
        Intent intent = new Intent(AssessmentListActivity.this, AssessmentViewActivity.class);
        startActivity(intent);
    }

    /**
     * This inflates the option menu
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }
    /**
     * This sets up the options in the menu and how they respond
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //This takes you back to the main screen.
            case R.id.home:
                Intent backToMain = new Intent(this, MainActivity.class);
                startActivity(backToMain);
                return true;
        }
            return super.onOptionsItemSelected(item);
    }

}