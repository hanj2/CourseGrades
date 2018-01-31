import java.util.ArrayList;
import java.util.HashMap;

/**
 * a class that have all the aggregation methods
 */
public class Aggregator{
    //a helper function that calculates the number of students of a course
    public static int numberOfStudents(Course course) throws IllegalArgumentException{
        if (course == null){
            throw new IllegalArgumentException(ErrorMessage.NULL_COURSE);
        }
        int[] grades = course.getGrades();
        int sumOfStudents = 0;
        for (int grade : grades){
            sumOfStudents += grade;
        }
        return sumOfStudents;
    }
    //total number of students who take the collection of courses
    public static int totalNumOfStudents(ArrayList<Course> courses) throws IllegalArgumentException{
        if (courses == null){
            throw new IllegalArgumentException(ErrorMessage.NULL_COURSE);
        }
        if (courses.size() == 0){
            throw new IllegalArgumentException(ErrorMessage.EMPTY_COURSE_ARRAYLIST);
        }
        int totalNumOfStudents = 0;
        for (Course course:courses){
            totalNumOfStudents += numberOfStudents(course);
        }
        return totalNumOfStudents;
    }
    // some global variables representing the index in the grade array of each course, final for safety reason
    private static final int GRADE_A_PLUS = 0;
    private static final int GRADE_A = 1;
    private static final int GRADE_A_MINUS = 2;
    private static final int GRADE_B_PLUS = 3;
    private static final int GRADE_B = 4;
    private static final int GRADE_B_MINUS = 5;
    private static final int GRADE_C_PLUS = 6;
    private static final int GRADE_C = 7;
    private static final int GRADE_C_MINUS = 8;
    private static final int GRADE_D_PLUS = 9;
    private static final int GRADE_D = 10;
    private static final int GRADE_D_MINUS = 11;
    private static final int GRADE_F = 12;
    private static final int GRADE_W = 13;

    //students that receives a range of grades, a case-insensitive method
    //create a hashmap to connect grade letters with GPAs
    public static int studentsInGradeRange(String lowerBound, String upperBound, ArrayList<Course> courses)
            throws IndexOutOfBoundsException, Error, IllegalArgumentException{
        HashMap<String, Integer> indexOfGPA = new HashMap<>();
        indexOfGPA.put("A+", GRADE_A_PLUS);
        indexOfGPA.put("A", GRADE_A);
        indexOfGPA.put("A-", GRADE_A_MINUS);
        indexOfGPA.put("B+", GRADE_B_PLUS);
        indexOfGPA.put("B", GRADE_B);
        indexOfGPA.put("B-", GRADE_B_MINUS);
        indexOfGPA.put("C+", GRADE_C_PLUS);
        indexOfGPA.put("C", GRADE_C);
        indexOfGPA.put("C-", GRADE_C_MINUS);
        indexOfGPA.put("D+", GRADE_D_PLUS);
        indexOfGPA.put("D", GRADE_D);
        indexOfGPA.put("D-", GRADE_D_MINUS);
        indexOfGPA.put("F",GRADE_F);
        indexOfGPA.put("W",GRADE_W);
        lowerBound = lowerBound.toUpperCase();
        upperBound = upperBound.toUpperCase();

        if (!indexOfGPA.containsKey(lowerBound)||!indexOfGPA.containsKey(upperBound) ){
            throw new IndexOutOfBoundsException(ErrorMessage.INVALID_GRADE);
        }
        // the index of the lower bound of grade should be larger than that of the higher bound of grade
        if (indexOfGPA.get(lowerBound) < indexOfGPA.get(upperBound)){
            throw new Error(ErrorMessage.INVALID_BOUNDS);
        }
        if (courses == null){
            throw new IllegalArgumentException(ErrorMessage.NULL_COURSE);
        }
        if (courses.size() == 0){
            throw new IllegalArgumentException(ErrorMessage.EMPTY_COURSE_ARRAYLIST);
        }

        int gradeLowerBound = indexOfGPA.get(lowerBound);
        int gradeUpperBound = indexOfGPA.get(upperBound);
        int studentsInGradeRange = 0;
        for (Course course:courses){
            int[] grades = course.getGrades();
            for (int i = gradeUpperBound; i <= gradeLowerBound; i++){
                studentsInGradeRange += grades[i];
            }
        }
        return studentsInGradeRange;
    }

    /* a method calculating the arithmetic mean of the gpa averages weighted by the course enrollment
     * weight = (sum of student number * avg GPA of each course)/total student number
     * throw an exception if the number of total students is zero
     */
    public static double meanOfGradeWeight(ArrayList<Course> courses) throws IllegalArgumentException, Error{
        if (courses == null){
            throw new IllegalArgumentException(ErrorMessage.NULL_COURSE);
        }
        if (courses.size() == 0){
            throw new IllegalArgumentException(ErrorMessage.EMPTY_COURSE_ARRAYLIST);
        }
        double studentsOfAllCourses = 0;
        double sumOfGrades = 0;
        for (Course course:courses){
            studentsOfAllCourses += numberOfStudents(course);
            sumOfGrades += numberOfStudents(course) * course.getAverage();
        }
        if (studentsOfAllCourses == 0){
            throw new Error(ErrorMessage.INVALID_DIVIDER);
        }
        return sumOfGrades / studentsOfAllCourses;
    }

}
