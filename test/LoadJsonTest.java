import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoadJsonTest {
    private static final String TEST_JSON = Data.getFileContentsAsString("TestExample.json");
    private static final ArrayList<Course> EMPTY_ARRAYLIST = new ArrayList<>();
    private  ArrayList<Course> testArrayList = new ArrayList<>();

    @Before
    public void setUpForLoadingTests(){
        Gson gson = new Gson();
        testArrayList = gson.fromJson(TEST_JSON, new TypeToken<List<Course>>(){}.getType());
    }

    //loading methods tests
    @Test
    public void loadJsonTest(){
        ArrayList<Course> coursesInTest = CourseStructure.loadJson(TEST_JSON);
        boolean isEqual = true;
        if (testArrayList.size() != coursesInTest.size()){
            isEqual = false;
        }
        for (int i = 0; i < testArrayList.size(); i++){
            if(!Course.isCourseEqual(testArrayList.get(i),coursesInTest.get(i))){
                isEqual = false;
            }
        }
        assertTrue(isEqual);
    }

    @Test
    public void loadJsonByFileName(){
        ArrayList<Course> coursesInTest = CourseStructure.loadJsonByFileName("TestExample.json");
        boolean isEqual = true;
        if (testArrayList.size() != coursesInTest.size()){
            isEqual = false;
        }
        for (int i = 0; i < testArrayList.size(); i++){
            if(!Course.isCourseEqual(testArrayList.get(i),coursesInTest.get(i))){
                isEqual = false;
            }
        }
        assertTrue(isEqual);
    }

    private static final int NUM_OF_COURSES_FA2013 = 2586;
    private static final int NUM_OF_COURSES_FA2014 = 2538;
    private static final int NUM_OF_COURSES_SP2013 = 2273;
    private static final int NUM_OF_COURSES_SP2014 = 2236;
    private static final int NUM_OF_COURSES_SU2013 = 186;
    private static final int NUM_OF_COURSES_SU2014 = 175;

    @Test
    public void loadAllJsonFilesTest(){
        ArrayList<Course> coursesInTest = CourseStructure.loadAllJsonFlies();
        int allCourses = NUM_OF_COURSES_FA2013 + NUM_OF_COURSES_FA2014 + NUM_OF_COURSES_SP2013
                + NUM_OF_COURSES_SP2014 + NUM_OF_COURSES_SU2013 + NUM_OF_COURSES_SU2014;
        assertEquals(allCourses, coursesInTest.size());
        assertEquals("41758", coursesInTest.get(0).getCRN());
    }
}
