import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CourseGradeTest {
    private static final String COURSES_OF_SUMMER_2013_JSON = Data.getFileContentsAsString("Summer2013.json");
    private  ArrayList<Course> courseArrayList = new ArrayList<>();

    @Before
    public void setUpForLoadingTests(){
        Gson gson = new Gson();
        courseArrayList = gson.fromJson(COURSES_OF_SUMMER_2013_JSON, new TypeToken<List<Course>>(){}.getType());
    }

    //loading methods tests
    @Test
    public void loadJsonTest(){
        ArrayList<Course> coursesInTest = CourseGrade.loadJson(COURSES_OF_SUMMER_2013_JSON);
        boolean isEquals = true;
        if (courseArrayList.size() != coursesInTest.size()){
            isEquals = false;
        }
        for (int i = 0; i < courseArrayList.size(); i++){
            if(!Course.isCourseEqual(courseArrayList.get(i),coursesInTest.get(i))){
                isEquals = false;
            }
        }
        assertTrue(isEquals);
    }

    @Test
    public void loadJsonByFileName(){
        ArrayList<Course> coursesInTest = CourseGrade.loadJsonByFileName("Summer2013.json");
        boolean isEquals = true;
        if (courseArrayList.size() != coursesInTest.size()){
            isEquals = false;
        }
        for (int i = 0; i < courseArrayList.size(); i++){
            if(!Course.isCourseEqual(courseArrayList.get(i),coursesInTest.get(i))){
                isEquals = false;
            }
        }
        assertTrue(isEquals);
    }

    private static final int NUM_OF_COURSES_FA2013 = 2586;
    private static final int NUM_OF_COURSES_FA2014 = 2538;
    private static final int NUM_OF_COURSES_SP2013 = 2273;
    private static final int NUM_OF_COURSES_SP2014 = 2236;
    private static final int NUM_OF_COURSES_SU2013 = 186;
    private static final int NUM_OF_COURSES_SU2014 = 175;

    @Test
    public void loadAllJsonFilesTest(){
        ArrayList<Course> coursesInTest = CourseGrade.loadAllJsonFlies();
        int allCourses = NUM_OF_COURSES_FA2013 + NUM_OF_COURSES_FA2014 + NUM_OF_COURSES_SP2013
                + NUM_OF_COURSES_SP2014 + NUM_OF_COURSES_SU2013 + NUM_OF_COURSES_SU2014;
        assertEquals(allCourses, coursesInTest.size());
        assertEquals("41758", coursesInTest.get(0).getCRN());
    }

    //filtering methods tests
    private static final String TEST_JSON_EXAMPLE = "{ \"CRN\": 41758, \"Subject\": \"AAS\", \"Number\": 100, \"Title\": \"Intro Asian American Studies\", \"Section\": \"AD1\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Arai, Sayuri\", \"Grades\": [6, 16, 5, 3, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.72 },\n" +
            "  { \"CRN\": 47100, \"Subject\": \"AAS\", \"Number\": 100, \"Title\": \"Intro Asian American Studies\", \"Section\": \"AD2\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Arai, Sayuri\", \"Grades\": [6, 11, 4, 5, 6, 1, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.64 },\n" +
            "  { \"CRN\": 47102, \"Subject\": \"AAS\", \"Number\": 100, \"Title\": \"Intro Asian American Studies\", \"Section\": \"AD3\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Davis, Thomas E\", \"Grades\": [2, 24, 1, 2, 4, 1, 1, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.75 },\n" +
            "  { \"CRN\": 51248, \"Subject\": \"AAS\", \"Number\": 100, \"Title\": \"Intro Asian American Studies\", \"Section\": \"AD4\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Davis, Thomas E\", \"Grades\": [7, 16, 4, 4, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.71 },\n" +
            "  { \"CRN\": 51249, \"Subject\": \"AAS\", \"Number\": 100, \"Title\": \"Intro Asian American Studies\", \"Section\": \"AD5\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Thomas, Merin A\", \"Grades\": [4, 12, 6, 3, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.7 },\n" +
            "  { \"CRN\": 51932, \"Subject\": \"AAS\", \"Number\": 100, \"Title\": \"Intro Asian American Studies\", \"Section\": \"AD6\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Thomas, Merin A\", \"Grades\": [12, 14, 2, 2, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.83 },\n" +
            "  { \"CRN\": 58092, \"Subject\": \"AAS\", \"Number\": 287, \"Title\": \"Food and Asian Americans\", \"Section\": \"A\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Manalansan, Martin F\", \"Grades\": [0, 23, 2, 4, 2, 3, 2, 0, 0, 0, 0, 0, 0, 1], \"Average\": 3.65 }\n";
    ArrayList<Course> courses;

    @Before
    public void setUpForFilteringTests(){
        courses = CourseGrade.loadJson(TEST_JSON_EXAMPLE);
    }



}
