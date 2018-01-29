import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * a class to turn Json files to java objects Courses.
 * CourseGrade a class with loading methods to parse JSON files
 * filtering methods to filter Array lists of Courses
 * and aggregation methods to aggregate courses in some similar cases
 */
public class CourseGrade {
    // method that load the provided Json File and parse it into an array list
    // parameters can't be null, so throw an IllegalArgumentException
    public static ArrayList<Course> loadJson(String jsonFile) throws IllegalArgumentException{
        Gson gson = new Gson();
        ArrayList<Course> coursesOfSemester = gson.fromJson(jsonFile, new TypeToken<List<Course>>(){}.getType());
        return coursesOfSemester;
    }

    // method that parses the Json File by its file name
    public static ArrayList<Course> loadJsonByFileName(String jsonFileName) throws IllegalArgumentException{
        String jsonFile = Data.getFileContentsAsString(jsonFileName);
        ArrayList<Course> coursesOfSemester = loadJson(jsonFile);
        return coursesOfSemester;
    }

    // a method Loading all Json from Files into a list of Course array
    // first, get the String list of all json file names
    // get the courses arraylist semester by semester and add them togther in one new list
    public static ArrayList<Course> loadAllJsonFlies(){
        List<String> jsonFileNames = Data.getJsonFilesAsList();
        ArrayList<Course> coursesOfAllSemesters = new ArrayList<>();
        for (String jsonFileName:jsonFileNames){
            ArrayList<Course> coursesOfSemester = loadJsonByFileName(jsonFileName);
            coursesOfAllSemesters.addAll(coursesOfSemester);
        }
        return coursesOfAllSemesters;
    }

    //filtering methods, set a null error message first
    //filtered by given department, case-insensitively
    public static ArrayList<Course> filteredBySubject(String subject,
                                                      ArrayList<Course> courses) throws IllegalArgumentException{
        ArrayList<Course> filteredCourses = new ArrayList<>();
        for (Course course : courses){
            String subjectOfCourse = course.getSubject();
            if (subjectOfCourse.equalsIgnoreCase(subject)){
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
    }

    //filtered by the given string in the instructor's name case-insensitively
    public static ArrayList<Course> filteredByInstructor(String stringInName,
                                                         ArrayList<Course> courses) throws IllegalArgumentException{
        ArrayList<Course> filteredCourses = new ArrayList<>();
        for (Course course:courses){
            String nameOfInstructor = course.getInstructor().toLowerCase();
            if (nameOfInstructor.contains(stringInName.toLowerCase())){
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
    }

    //filtered by the given class number range, inclusively
    //three parameters: upperBound and LowerBound and course ArrayList
    public static ArrayList<Course> filteredByCourseNumRange(int lowerBound, int upperBound,
                                                             ArrayList<Course> courses) throws IllegalArgumentException{
        ArrayList<Course> filteredCourses = new ArrayList<>();
        for (Course course:courses){
            int courseNum = course.getNumber();
            if (courseNum >= lowerBound && courseNum <= upperBound){
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
    }

    //a helper function that calculates the number of students of a course
    public static int numberOfStudents(Course course) throws IllegalArgumentException{
        int[] grades = course.getGrades();
        int sumOfStudents = 0;
        for (int i = 0; i < grades.length; i++){
            sumOfStudents += grades[i];
        }
        return sumOfStudents;
    }

    //filtered by range of student number, inclusively
    public static ArrayList<Course> filteredByStudentNum(int lowerBound,int upperBound,
                                                         ArrayList<Course> courses) throws IllegalArgumentException{
        ArrayList<Course> filteredCourses = new ArrayList<>();
        for (Course course:courses){
            int studentNum = numberOfStudents(course);
            if (studentNum >= lowerBound && studentNum <= upperBound){
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
    }

    //filtered by term/semester, case in-sensitively
    public static ArrayList<Course> filteredByTerm (String term,
                                                    ArrayList<Course> courses) throws IllegalArgumentException{
        ArrayList<Course> filteredCourses = new ArrayList<>();
        for (Course course:courses){
            String termOfCourse = course.getTerm();
            if (termOfCourse.equalsIgnoreCase(term)){
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
    }

    //filtered by subject, course number and substring of instructor's name
    //first filtered by subject and instructor, then by course number
    public static ArrayList<Course> filteredByCourseAndInstructor(String subject, int courseNumber,
                                                                  String nameOfInstructor, ArrayList<Course> courses) throws NullPointerException{
        if(courses == null){
            throw new NullPointerException("NULL INPUT!");
        }
        courses = filteredByInstructor(nameOfInstructor, filteredBySubject(subject,courses));
        ArrayList<Course> filteredCourses = new ArrayList<>();
        for (Course course : courses){
            if (course.getNumber() == courseNumber){
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
    }

    //aggregation methods
    //total number of students who take the collection of courses
    public static int totalNumOfStudents(ArrayList<Course> courses) throws IllegalArgumentException{
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
    public static int studentsInGradeRange(String lowerBound, String upperBound,
                                    ArrayList<Course> courses) throws IllegalArgumentException{
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

        int gradeLowerBound = indexOfGPA.get(lowerBound.toUpperCase());
        int gradeUpperBound = indexOfGPA.get(upperBound.toUpperCase());
        int studentsInGradeRange = 0;
        for (Course course:courses){
            int[] grades = course.getGrades();
            for (int i = gradeUpperBound; i <= gradeLowerBound; i++){
                studentsInGradeRange += grades[i];
            }
        }
        return studentsInGradeRange;
    }

    private static final String ERROR_OF_DIVIDER_AS_ZERO = "THE DIVISOR CAN'T BE ZERO!";
    /* a method calculating the arithmetic mean of the gpa averages weighted by the course enrollment
     * weight = (sum of student number * avg GPA of each course)/total student number
     * throw an exception if the number of total students is zero
     */
    public static double meanOfGradeWeight(ArrayList<Course> courses) throws IllegalArgumentException{
        double studentsOfAllCourses = 0;
        double sumOfGrades = 0;
        for (Course course:courses){
            studentsOfAllCourses += numberOfStudents(course);
            sumOfGrades += numberOfStudents(course) * course.getAverage();
        }
        if (studentsOfAllCourses == 0){
            try {
                throw new Exception(ERROR_OF_DIVIDER_AS_ZERO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sumOfGrades / studentsOfAllCourses;
    }
}
