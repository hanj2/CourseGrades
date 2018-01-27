import com.google.gson.Gson;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * CourseGrade a class with loading, filtering, and aggregation methods.
 */
public class CourseGrade {
    // methods load the provided Json File and parse it.

    public static ArrayList<Course> loadJson(String jsonFile) throws NullPointerException{
        Gson gson = new Gson();
        Course[] coursesOfSemesterArray = gson.fromJson(jsonFile,Course[].class);
        ArrayList<Course> coursesOfSemester = new ArrayList<>(Arrays.asList(coursesOfSemesterArray));
        return coursesOfSemester;
    }

    // method that parses the Json File by its file name
    public static ArrayList<Course> loadJsonByFileName(String jsonFileName)throws NullPointerException{
        String jsonFile = Data.getFileContentsAsString(jsonFileName);
        ArrayList<Course> coursesOfSemester = loadJson(jsonFile);
        return coursesOfSemester;
    }
    // a method Loading all Json from Files into a list of Course array
    // first, get the String list of all json file names
    // get the courses arraylist semester by semester and add them togther in one new list
    public static ArrayList<Course> loadAllJsonFlies(){
        List<String> jsonFileNames = Data.getJsonFilesAsList();
        ArrayList<Course> coursesOfAllSemesters = new ArrayList<Course>();
        for (String jsonFileName:jsonFileNames){
            ArrayList<Course> coursesOfSemester = loadJsonByFileName(jsonFileName);
            coursesOfAllSemesters.addAll(coursesOfSemester);
        }

        return coursesOfAllSemesters;
    }

    //filtering methods
    //filtered by given department.
    public static ArrayList<Course> filteredBySubject(String subject,
                                                      ArrayList<Course> courses) throws NullPointerException{
        ArrayList<Course> filteredCourses = new ArrayList<Course>();
        for (Course course:courses){
            String subjectOfCourse = course.getSubject();
            if (subjectOfCourse.equals(subject)){
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
    }

    //filtered by the given string in the instructor's name case-insensitively
    public static ArrayList<Course> filteredByInstructor(String stringInName,
                                                         ArrayList<Course> courses) throws NullPointerException{
        ArrayList<Course> filteredCourses = new ArrayList<Course>();
        for (Course course:courses){
            String nameOfInstructor = course.getInstructor().toLowerCase();
            if (nameOfInstructor.contains(stringInName.toLowerCase())){
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
    }

    //filtered by the given class number range
    //three parameters: upperBound and LowerBound and course ArrayList
    public static ArrayList<Course> filteredByCourseNumRange(int lowerBound, int upperBound,
                                                             ArrayList<Course> courses) throws NullPointerException{
        ArrayList<Course> filteredCourses = new ArrayList<Course>();
        for (Course course:courses){
            int courseNum = course.getNumber();
            if (courseNum >= lowerBound && courseNum <= upperBound){
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
    }

    //a helper function that calculates the number of students of a course
    public static int numberOfStudents(Course course) throws NullPointerException{
        int[] grades = course.getGrades();
        int sumOfStudents = 0;
        for (int i = 0; i < grades.length; i++){
            sumOfStudents += grades[i];
        }
        return sumOfStudents;
    }

    //filtered by range of student number
    public static ArrayList<Course> filteredByStudentNum(int upperBound, int lowerBound,
                                                         ArrayList<Course> courses) throws NullPointerException{
        ArrayList<Course> filteredCourses = new ArrayList<Course>();
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
                                                    ArrayList<Course> courses) throws NullPointerException{
        ArrayList<Course> filteredCourses = new ArrayList<Course>();
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
                                                                  String nameOfInstructor, ArrayList<Course> courses)
            throws NullPointerException{
        courses = filteredByInstructor(nameOfInstructor, filteredBySubject(subject,courses));
        ArrayList<Course> filteredCourses = new ArrayList<Course>();
        for (Course course : courses){
            if (course.getNumber() == courseNumber){
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
    }




    //aggregation methods
    //total number of students who take the collection of courses
    public static int totalNumOfStudents(ArrayList<Course> courses) throws NullPointerException{
        int totalNumOfStudents = 0;
        for (Course course:courses){
            totalNumOfStudents += numberOfStudents(course);
        }
        return totalNumOfStudents;
    }

    // some global variables representing index in the grade array of each course

    public static final int GRADE_A_PLUS = 0;
    public static final int GRADE_A = 1;
    public static final int GRADE_A_MINUS = 2;
    public static final int GRADE_B_PLUS = 3;
    public static final int GRADE_B = 4;
    public static final int GRADE_B_MINUS = 5;
    public static final int GRADE_C_PLUS = 6;
    public static final int GRADE_C = 7;
    public static final int GRADE_C_MINUS = 8;
    public static final int GRADE_D_PLUS = 9;
    public static final int GRADE_D = 10;
    public static final int GRADE_D_MINUS = 11;
    public static final int GRADE_F = 12;
    public static final int GRADE_W = 13;

    //students that receives a range of grades, a case-insensitive method
    //create a hashmap to connect grade letters with GPAs
    public int studentsInGradeRange(String lowerBound, String upperBound,
                                    ArrayList<Course> courses) throws NullPointerException{
        HashMap<String, Integer> indexOfGPA = new HashMap<String, Integer>();
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

    public static final String ERROR_OF_DIVIDER_AS_ZERO = "The divider is zero!";
    // a method calculating the arithmetic mean of the gpa averages weighted by the course enrollment
    // weight = (sum of student number * avg GPA of each course)/total student number
    public static double meanOfGradeWeight(ArrayList<Course> courses) throws NullPointerException{
        double studentsOfAllCourses = 0;
        double sumOfGrades = 0;
        for (Course course:courses){
            studentsOfAllCourses += numberOfStudents(course);
            sumOfGrades += numberOfStudents(course) * course.getAverage();
        }
        // throw an exception if the number of total students is zero
        if(studentsOfAllCourses == 0){
            Exception e = new Exception(ERROR_OF_DIVIDER_AS_ZERO);
        }
        double weight = sumOfGrades / studentsOfAllCourses;
        return weight;
    }
}