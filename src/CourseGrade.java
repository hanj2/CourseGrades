import com.google.gson.Gson;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class CourseGrade {
    /**
     *  methods load the provided Json File and parse it
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
    //filtered by given department




}
