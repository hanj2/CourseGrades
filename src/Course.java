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
    public Course(String newCRN, String newSubject, int newNumber, String newTitle, String newSection,
                  String newType, String newTerm, String newInstructor, int[] newGrades, double newAverage){
        this.CRN = newCRN;
        this.Subject = newSubject;
        this.Number = newNumber;
        this.Title = newTitle;
        this.Section = newSection;
        this.Type = newType;
        this.Term = newTerm;
        this.Instructor = newInstructor;
        this.Average = newAverage;
        this.Grades = new int[newGrades.length];
        System.arraycopy(newGrades, 0, Grades, 0, Grades.length);
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

    //helper function to see if two Grades arrays are equal
    private static boolean areGradesArraysEqual(int[] Grades1, int[] Grades2){
        if (Grades1.length != Grades2.length){
            return false;
        }
        for (int i = 0; i < Grades1.length; i++){
            if (Grades1[i] != Grades2[i]){
                return false;
            }
        }
        return true;
    }

    //Course Object isEqual method, if term and CRN are both the same
    public static boolean isCourseEqual(Course course1, Course course2) throws NullPointerException{
        return !course1.getCRN().equals(course2.getCRN()) || !course2.getTerm().equals(course2.getTerm());

    }
}
