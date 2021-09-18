package android.melissaeppersonc196.scheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.melissaeppersonc196.scheduler.Entity.AssessmentsEntity;
import android.melissaeppersonc196.scheduler.Entity.CoursesEntity;
import android.melissaeppersonc196.scheduler.Entity.TermsEntity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.melissaeppersonc196.R;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    class TermViewHolder extends RecyclerView.ViewHolder {

        private final TextView termItemView;
        //private final TextView termItemView2;

        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.termTextView);
           // termItemView2 = itemView.findViewById(R.id.termTextView2);
            //This gets what is clicked on and stores the information so it can auto populate the next screen
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                final TermsEntity current = mTerms.get(position);
                Intent intent = new Intent(context, TermViewActivity.class);
                //Intent intent = new Intent(context, CourseViewActivity.class); ///this will pull correct course if CourseViewActivity
                intent.putExtra("title", current.getTitle());
                intent.putExtra("startDate", current.getStartDate());
                intent.putExtra("endDate", current.getEndDate());
                intent.putExtra("termID", current.getTermID());
                intent.putExtra("position", position);
                context.startActivity(intent);
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<TermsEntity> mTerms; //cached copy of words

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    /**
     This inflates the layout to show data
     */
    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);

        return new TermViewHolder(itemView);
    }


    /**
     * this binds what you want to show on the activity_term_list screen
     * @param holder
     * @param position
     */
    public void onBindViewHolder(TermAdapter.TermViewHolder holder, int position) {
        if (mTerms != null) {
            final TermsEntity current = mTerms.get(position);
            //This sets it to display the term title, startdate, and enddate in the list view
            holder.termItemView.setText(current.getTitle() + "     " + current.getStartDate() + " - " + current.getEndDate());
          //  holder.termItemView2.setText(current.getStartDate() + " - " + current.getEndDate());
        } else {
            //this displays if the data is not ready yet
            holder.termItemView.setText("No Terms");
           // holder.termItemView2.setText("No term Dates");
        }
    }

    //This sets the text in the activity_terms_list screen to display terms
    public void setTermText(List<TermsEntity> termText) {
        mTerms = termText;
        notifyDataSetChanged();
    }

    //getItemCount returns null when first called. mText starts as a null but we cant return null
    public int getItemCount() {
        if (mTerms != null)
            return mTerms.size();
        else return 0;
    }
}
