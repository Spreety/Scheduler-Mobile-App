package android.melissaeppersonc196.scheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessmentsTable")

public class AssessmentsEntity {
    @PrimaryKey
    private int assessmentID;

    private String title;
    private String type;
    private String endDate;
    private int courseID;

    //need to fix this so it has everything I need
    @Override
    public  String toString() {
        return "AssessmentsEntity{" +
                "assessmentID=" + assessmentID +
                ", title=" + title + '\'' +
                ", type=" + type + '\'' +
                ", endDate=" + endDate +
                ", courseID=" + courseID +
                '}';
    }

    public AssessmentsEntity(int assessmentID, String title, String type, String endDate, int courseID) {
        this.assessmentID = assessmentID;
        this.title = title;
        this.type = type;
        this.endDate = endDate;
        this.courseID = courseID;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
