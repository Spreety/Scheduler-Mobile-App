package android.melissaeppersonc196.scheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.melissaeppersonc196.scheduler.Entity.AssessmentsEntity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.melissaeppersonc196.R;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;
       // private final TextView assessmentItemView2;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.assessmentTextView);
           // assessmentItemView2 = itemView.findViewById(R.id.assessmentTextView2);
           //This gets what is clicked on and stores the information so it can auto populate the next screen
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                final AssessmentsEntity current = mAssessments.get(position);
                Intent intent = new Intent(context, AssessmentViewActivity.class);
                intent.putExtra("title", current.getTitle());
                intent.putExtra("type", current.getType());
                intent.putExtra("endDate", current.getEndDate());
                intent.putExtra("assessmentID", current.getAssessmentID());
                intent.putExtra("position", position);
                context.startActivity(intent);
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<AssessmentsEntity> mAssessments; //cached copy of words

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    /**
    This inflates the layout to show data
     */
    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);

        return new AssessmentViewHolder(itemView);
    }

    /**
     * this binds what you want to show on the activity_assessment_list screen.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            final AssessmentsEntity current = mAssessments.get(position);
            //This sets it to display the assessment title only
            holder.assessmentItemView.setText(current.getTitle());
        } else {
            //this displays if the data is not ready yet
            holder.assessmentItemView.setText("No Assessment Title");
           // holder.assessmentItemView2.setText("No Assessment ID");
        }
    }

    //This sets the text in the activity_assessment_list layout to display assessments
    public void setAssessmentText(List<AssessmentsEntity> assessmentText) {
        mAssessments = assessmentText;
        notifyDataSetChanged();
    }

    //getItemCount returns null when first called. mText starts as a null but we cant return null
    @Override
    public int getItemCount() {
        if (mAssessments != null)
            return mAssessments.size();
        else return 0;
    }
}

