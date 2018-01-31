import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class LoadJson {
    public static ArrayList<Course> loadJson(String jsonFile) throws NullPointerException{
        if (jsonFile == null){
            throw new NullPointerException(ErrorMessage.NULL_JSON_FILE);
        }
        Gson gson = new Gson();
        return gson.fromJson(jsonFile, new TypeToken<List<Course>>(){}.getType());
    }

    // method that parses the Json File by its file name
    public static ArrayList<Course> loadJsonByFileName(String jsonFileName) throws NullPointerException{
        if (jsonFileName == null){
            throw new NullPointerException(ErrorMessage.NULL_JSON_FILE_NAME);
        }
        String jsonFile = Data.getFileContentsAsString(jsonFileName);
        return loadJson(jsonFile);
    }

    // a method Loading all Json from Files into a list of Course array
    // first, get the String list of all json file names
    // get the courses arraylist semester by semester and add them together in one new list
    public static ArrayList<Course> loadAllJsonFlies(){
        List<String> jsonFileNames = Data.getJsonFilesAsList();
        ArrayList<Course> coursesOfAllSemesters = new ArrayList<>();
        for (String jsonFileName:jsonFileNames){
            ArrayList<Course> coursesOfSemester = loadJsonByFileName(jsonFileName);
            coursesOfAllSemesters.addAll(coursesOfSemester);
        }
        return coursesOfAllSemesters;
    }
}
