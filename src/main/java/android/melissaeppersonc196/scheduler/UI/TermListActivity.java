package android.melissaeppersonc196.scheduler.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.melissaeppersonc196.scheduler.Database.SchedulerRepository;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.melissaeppersonc196.R;

public class TermListActivity extends AppCompatActivity {

    int ID;
    String title;
    String startDate;
    String endDate;
    int courseID;
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;

    public static int termCount;

    private SchedulerRepository schedulerRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        schedulerRepository = new SchedulerRepository(getApplication());
        schedulerRepository.getAllTerms();
        RecyclerView recyclerView = findViewById(R.id.termListText);

        //this displays all terms in the activity_terms_list screen
        final TermAdapter adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTermText(schedulerRepository.getAllTerms());
        System.out.println(schedulerRepository.getAllTerms());
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

    public void addNewTerm(View view) {
        Intent intent=new Intent(TermListActivity.this,TermViewActivity.class);
        startActivity(intent);
    }
}