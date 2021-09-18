package android.melissaeppersonc196.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.melissaeppersonc196.R;

public class MainActivity extends AppCompatActivity {

    public static int alertCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void termsScreen(View view) {
        Intent intent = new Intent(MainActivity.this, TermListActivity.class);
        startActivity(intent);
    }
    public void coursesScreen(View view) {
        Intent intent = new Intent(MainActivity.this, CourseListActivity.class);
        startActivity(intent);
    }
    public void assessmentsScreen(View view) {
        Intent intent = new Intent(MainActivity.this, AssessmentListActivity.class);
        startActivity(intent);
    }
}