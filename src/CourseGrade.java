import com.google.gson.Gson;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * CourseGrade a class with loading, filtering, and aggregation methods.
 */
public class CourseGrade {
    /**
     *  methods load the provided Json File and parse it.
     * @param jsonFile the input json file
     * @return an arrayList of Courses in the json file
     */
    public static ArrayList<Course> loadJson(String jsonFile){
        Gson gson = new Gson();
        Course[] coursesOfSemesterArray = gson.fromJson(jsonFile,Course[].class);
        ArrayList<Course> coursesOfSemester = new ArrayList<>(Arrays.asList(coursesOfSemesterArray));
        return coursesOfSemester;
    }

    // method that parses the Json File by its file name
    public static ArrayList<Course> loadJsonByFileName(String jsonFileName){
        String jsonFile = Data.getFileContentsAsString(jsonFileName);
        ArrayList<Course> coursesOfSemester = loadJson(jsonFile);
        return coursesOfSemester;
    }
    /*
     * a method Loading all Json from Files into a list of Course array
     * first, get the String list of all json file names
     * get the courses arraylist semester by semester and add them togther in one new list
     */
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

    /**
     * filtered by given department.
     * @param subject the given subject
     * @param courses all courses to be fliter
     * @return the new courseArrayList filtered by the subject
     */
    public ArrayList<Course> filteredBySubject(String subject, ArrayList<Course> courses){
        ArrayList<Course> filteredCourses = new ArrayList<Course>();
        for (Course course:courses){
            String subjectOfCourse = course.getSubject();
            if (subjectOfCourse.equals(subject)){
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
    }

    //filterd by the given string in the instructor's name case-insensitively
    public ArrayList<Course> filteredByInstructor(String stringInName, ArrayList<Course> courses){
        ArrayList<Course> filteredCourses = new ArrayList<Course>();
        for (Course course:courses){
            String nameOfInstructor = course.getInstructor().toLowerCase();
            if (nameOfInstructor.contains(stringInName.toLowerCase())){
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
    }

    //filterd by the given class number range
    //three parameters: upperBound and LowerBound and course ArrayList
    public ArrayList<Course> filteredByCourseNumRange(int upperBound, int lowerBound, ArrayList<Course> courses){
        ArrayList<Course> filteredCourses = new ArrayList<Course>();
        for (Course course:courses){
            int courseNum = course.getNumber();
            if (courseNum >= upperBound && courseNum <= lowerBound){
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
    }


    //a helper function that calculates the number of students of a course
    //sum of grade = number of students * average GPA
    public int numberOfStudents(Course course){
        int[] grades = course.getGrades();
        int sumOfGrades = 0;
        for (int i = 0; i < grades.length; i++){
            sumOfGrades += grades[i];
        }
        int numberOfStudents = (int) Math.round(sumOfGrades/course.getAverage());
        return numberOfStudents;
    }











}
