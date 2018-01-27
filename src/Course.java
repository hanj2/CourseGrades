import java.util.List;

public class Course {
    public Course(){

    }
    // the variables of Course
    private String CRN;
    private String Subject;
    private String Number;
    private String Title;
    private String Section;
    private String Term;
    private String Type;
    private String Instructor;
    private String[] Grades;
    private String Average;

    //the getters, read-only for security reason
    public String getCRN(){
        return CRN;
    }
    public String getSubject(){
        return Subject;
    }
    public String getNumber(){
        return Number;
    }
    public String getTitle(){
        return Title;
    }
    public String getSection(){
        return Section;
    }
    public String getTerm(){
        return Term;
    }
    public String getType(){
        return Type;
    }
    public String getInstructor(){
        return Instructor;
    }
    public String[] getGrades(){
        return Grades;
    }
    public String getAverage(){
        return Average;
    }

}
