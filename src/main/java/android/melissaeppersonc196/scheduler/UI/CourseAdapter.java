package android.melissaeppersonc196.scheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.melissaeppersonc196.scheduler.Entity.AssessmentsEntity;
import android.melissaeppersonc196.scheduler.Entity.CoursesEntity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.melissaeppersonc196.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;
        //private final TextView courseItemView2;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.courseTextView);
            //courseItemView2 = itemView.findViewById(R.id.courseListText2);
            // This gets what is clicked on and stores the information so it can auto populate the next screen
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                final CoursesEntity current = mCourses.get(position);
                Intent intent = new Intent(context, CourseViewActivity.class);
                intent.putExtra("title", current.getTitle());
                intent.putExtra("startDate", current.getStartDate());
                intent.putExtra("endDate", current.getEndDate());
                intent.putExtra("status", current.getStatus());
                intent.putExtra("instructorName", current.getInstructorName());
                intent.putExtra("instructorPhone", current.getInstructorPhone());
                intent.putExtra("instructorEmail", current.getInstructorEmail());
                intent.putExtra("courseID", current.getCourseID());
                intent.putExtra("position", position);
                context.startActivity(intent);
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<CoursesEntity> mCourses; //cached copy of words

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

  /*  //this gets the course in the selected position
    public CoursesEntity getSelectedCoursePosition(int position) {
        return mCourses.get(position);
    }*/

    /**
     This inflates the layout to show data
     */
    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);

        return new CourseViewHolder(itemView);
    }

    /**
     * this binds what you want to show on the activity_course_list screen
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        if (mCourses != null) {
            final CoursesEntity current = mCourses.get(position);
            //This sets it to display the assessment title only
            holder.courseItemView.setText(current.getTitle());
        } else {
            //this displays if the data is not ready yet
            holder.courseItemView.setText("No Course Title");
            //holder.courseItemView2.setText("No Course ID");

        }
    }
    //getItemCount returns null when first called. mText starts as a null but we cant return null
    @Override
    public int getItemCount() {
        if (mCourses != null)
            return mCourses.size();
        else return 0;
    }

    //This sets the text in the activity_course_list screen to display courses
    public void setCourseText(List<CoursesEntity> courseText) {
        mCourses = courseText;
        notifyDataSetChanged();
    }
}
