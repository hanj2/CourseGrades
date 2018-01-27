import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CourseTest {
    private static final String COURSE_JSON = " { \"CRN\": 41758, \"Subject\": \"AAS\", \"Number\": 100, \"Title\": \"Intro Asian American Studies\", \"Section\": \"AD1\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Arai, Sayuri\", \"Grades\": [6, 16, 5, 3, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.72 } ";
    public static final String COURSES_OF_SUMMER_2013_JSON = Data.getFileContentsAsString("Summer2013.json");
    private Course course;
    private Course[] courses;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();
        course = gson.fromJson(COURSE_JSON, Course.class);
        courses = gson.fromJson(COURSES_OF_SUMMER_2013_JSON, Course[].class);
    }

    //getter tests
    @Test
    public void getCRNTest(){
        assertEquals("41758", course.getCRN());
    }
    @Test
    public void getSubjectTest(){
        assertEquals("AAS", course.getSubject());
    }
    @Test
    public void getNumberTest(){
        assertTrue(100 == course.getNumber());
    }
    @Test
    public void getTitleTest(){
        assertEquals("Intro Asian American Studies", course.getTitle());
    }
    @Test
    public void getSectionTest(){
        assertEquals("AD1", course.getSection());
    }
    @Test
    public void getTypeTest(){
        assertEquals("DIS", course.getType());
    }
    @Test
    public void getTermTest(){
        assertEquals("120138", course.getTerm());
    }
    @Test
    public void getInstructorTest(){
        assertEquals("Arai, Sayuri", course.getInstructor());
    }
    @Test
    public void getGradesTest(){
        boolean isEqual = true;
        int[] grades = course.getGrades();
        int[] expectedGrades = new int[] {6, 16, 5, 3, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0};
        if (grades.length != expectedGrades.length) {
            isEqual = false;
        } else {
            for (int i = 0; i < grades.length; i++){
                if (grades[i] != expectedGrades[i]){
                    isEqual = false;
                }
            }
        }
        assertTrue(isEqual);
    }

    public static final double MAX_ERROR_RANGE = 0.00001;
    @Test
    public void getAverageTest(){
        assertTrue(Math.abs(course.getAverage() - 3.72) <= MAX_ERROR_RANGE);
    }
}
