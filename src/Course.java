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
        this.subject = newSubject;
        this.number = newNumber;
        this.title = newTitle;
        this.section = newSection;
        this.type = newType;
        this.term = newTerm;
        this.instructor = newInstructor;
        this.average = newAverage;
        this.grades = new int[newGrades.length];
        System.arraycopy(newGrades, 0, grades, 0, grades.length);
    }

    // the variables of Course
    private String CRN;
    private String subject;
    private int number;
    private String title;
    private String section;
    private String type;
    private String term;
    private String instructor;
    private int[] grades;
    private double average;

    //the getters, read-only for security reason
    public String getCRN(){
        return CRN;
    }
    public String getSubject(){
        return subject;
    }
    public int getNumber(){
        return number;
    }
    public String getTitle(){
        return title;
    }
    public String getSection(){
        return section;
    }
    public String getType(){
        return type;
    }
    public String getTerm(){
        return term;
    }
    public String getInstructor(){
        return instructor;
    }
    public int[] getGrades(){
        return grades;
    }
    public double getAverage(){
        return average;
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

    //Course Object isEqual method
    public static boolean areCoursesEqual(Course course1, Course course2) throws NullPointerException{
        return !course1.getCRN().equals(course2.getCRN())
                || !course1.getSubject().equals(course2.getSubject())
                || course1.getNumber() != course2.getNumber()
                || !course1.getTitle().equals(course2.getTitle())
                || !course1.getSection().equals(course2.getSection())
                || !course1.getTerm().equals(course2.getTerm())
                || !course1.getType().equals(course2.getType())
                || !course1.getInstructor().equals(course2.getInstructor())
                || !areGradesArraysEqual(course1.getGrades(), course2.getGrades())
                || course1.getAverage() != course2.getAverage();
    }
}
