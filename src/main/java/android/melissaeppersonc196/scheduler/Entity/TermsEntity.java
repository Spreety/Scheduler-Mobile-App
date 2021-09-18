package android.melissaeppersonc196.scheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "termsTable")
public class TermsEntity {
    @PrimaryKey
    private int termID;

    private String title;
    private String startDate;
    private String endDate;
    private int courseID;

    @Override
    public String toString() {
        return "TermsEntity{" +
                ", termID=" + termID +
                ", title=" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate + '\'' +
                ", courseID=" + courseID +
                '}';
    }

    public TermsEntity(int termID, String title, String startDate, String endDate, int courseID) {
        this.termID = termID;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseID = courseID;
    }

    public int getTermID() {
        return termID;
    }

    public String getTitle() {
        return title;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
