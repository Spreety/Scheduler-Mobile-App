package android.melissaeppersonc196.scheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notesTable")
public class NotesEntity {
    @PrimaryKey
    private int  noteID;

    private String note;
    private int courseID;

    @Override
    public String toString() {
        return "NotesEntity{" +
                ", noteID=" + noteID +
                ", note=" + note + '\'' +
                ", courseID=" + courseID +
                '}';
    }

    public NotesEntity(int noteID, String note, int courseID) {
        this.noteID = noteID;
        this.note = note;
        this.courseID = courseID;
    }

    public int getNoteID() {
        return noteID;
    }

    public String getNote() {
        return note;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}

