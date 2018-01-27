import com.google.gson.Gson;
import java.util.List;

public class CourseGrade {
    //methods load the provided Json File and parse it
    //helper function
    public static Course[] loadJsonFile(String jsonFile){
        Gson gson = new Gson();
        Course[] coursesOfSemester = gson.fromJson(jsonFile,Course[].class);
        return coursesOfSemester;
    }

    public static Course[] loadJsonFileByFileName(String jsonFileName){
        String jsonFile = Data.getFileContentsAsString(jsonFileName);
        Course[] coursesOfSemester = loadJsonFile(jsonFile);
        return coursesOfSemester;
    }

    /*
     * a method Loading all Json from Files into a list of Course array
     * first, get the String list of all json file names
     * then, convert all file names to Course array Objects and add them to the list
     */
    public static List<Course[]> loadAllJsonFlies(){
        List<String> jsonFileNames = Data.getJsonFilesAsList();
        List<Course[]> coursesOfAllSemesters = null;
        for (String jsonFileName : jsonFileNames ){
            Course[] courseOfOneSemester = loadJsonFileByFileName(jsonFileName);
            coursesOfAllSemesters.add(courseOfOneSemester);
        }
        return coursesOfAllSemesters;
    }


    //filtering methods




}
