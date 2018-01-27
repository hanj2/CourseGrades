/**
 * the course being represented by a piece of Json text file.
 * each course has a CRN. subject, course number, course title, section, term, type, instructor,
 * an int array of grades that has 14 positions, which are A+, A, A-, B+, B, B-, C+, C, C-, D+, D, F, and W
 * storing the students who get each of those grades,
 * and the average GPA of the course
 */
public class Course {
    public Course(){

    }
    // the variables of Course
    private String CRN;
    private String Subject;
    private int Number;
    private String Title;
    private String Section;
    private String Type;
    private String Term;
    private String Instructor;
    private int[] Grades;
    private double Average;

    //the getters, read-only for security reason
    public String getCRN(){
        return CRN;
    }
    public String getSubject(){
        return Subject;
    }
    public int getNumber(){
        return Number;
    }
    public String getTitle(){
        return Title;
    }
    public String getSection(){
        return Section;
    }
    public String getType(){
        return Type;
    }
    public String getTerm(){
        return Term;
    }
    public String getInstructor(){
        return Instructor;
    }
    public int[] getGrades(){
        return Grades;
    }
    public double getAverage(){
        return Average;
    }
}
