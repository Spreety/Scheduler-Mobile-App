package android.melissaeppersonc196.scheduler.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.melissaeppersonc196.scheduler.Database.SchedulerRepository;
import android.melissaeppersonc196.scheduler.Entity.AssessmentsEntity;
import android.melissaeppersonc196.scheduler.Entity.CoursesEntity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.melissaeppersonc196.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.melissaeppersonc196.R.layout.activity_course_list;

public class CourseListActivity extends AppCompatActivity {

    int ID;
    static int ID2;
    String title;
    String startDate;
    String endDate;
    String status;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    String notes;
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    EditText editStatus;
    EditText editInstructorName;
    EditText editInstructorPhone;
    EditText editInstructorEmail;
    public static int courseCount;

    private SchedulerRepository schedulerRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        schedulerRepository = new SchedulerRepository(getApplication());
        schedulerRepository.getAllCourses();
        //List<CoursesEntity> allCourses = schedulerRepository.getAllCourses();
        RecyclerView recyclerView = findViewById(R.id.courseListText);


        //this sets the text to display all courses on the activity_course_list layout
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourseText(schedulerRepository.getAllCourses());
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

    public void courseViewScreen(View view) {
        Intent intent = new Intent(CourseListActivity.this, CourseViewActivity.class);
        startActivity(intent);
    }

    public void addNewCourse(View view) {
        Intent intent = new Intent(CourseListActivity.this, CourseViewActivity.class);
        startActivity(intent);
    }

}