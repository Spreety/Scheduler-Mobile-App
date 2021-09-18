package android.melissaeppersonc196.scheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.melissaeppersonc196.scheduler.Database.SchedulerRepository;
import android.melissaeppersonc196.scheduler.Entity.AssessmentsEntity;
import android.melissaeppersonc196.scheduler.Entity.NotesEntity;
import android.melissaeppersonc196.scheduler.Entity.TermsEntity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.melissaeppersonc196.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private final TextView noteItemView;
        //private final TextView termItemView2;

        private NoteViewHolder(View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.noteTextView);
            //This gets what is clicked on and stores the information so it can auto populate the next screen
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                final NotesEntity current = mNotes.get(position);
                Intent intent = new Intent(context, NoteActivity.class);
                intent.putExtra("note", current.getNote());
                intent.putExtra("noteID", current.getNoteID());
                intent.putExtra("position", position);
                context.startActivity(intent);
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<NotesEntity> mNotes; //cached copy of words

    public NoteAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    /**
     This inflates the layout to show data
     */
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.note_list_item, parent, false);

        return new NoteAdapter.NoteViewHolder(itemView);
    }


    /**
     * this binds what you want to show on the not text view
     * @param holder
     * @param position
     */
    public void onBindViewHolder(NoteAdapter.NoteViewHolder holder, int position) {
        if (mNotes != null) {
            final NotesEntity current = mNotes.get(position);
            //This sets it to display the term title only
            holder. noteItemView.setText(current.getNote());

        } else {
            //this displays if the data is not ready yet
            holder.noteItemView.setText("No Notes");
        }
    }

    public void setNoteText(List<NotesEntity> text) {
        mNotes = text;
        notifyDataSetChanged();
    }

    //getItemCount returns null when first called. mText starts as a null but we cant return null
    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else return 0;
    }
}
