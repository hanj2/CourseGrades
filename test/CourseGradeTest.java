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
    private  ArrayList<Course> courseArrayList;

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
    @Test
    public void filteredBySubjectTest(){
        assertEquals("30486", CourseGrade.filteredBySubject("THEA", courseArrayList).get(0).getCRN());
    }

    @Test
    public void filteredByInstructorTest(){
        assertEquals("37453", CourseGrade.filteredByInstructor("Peter",
                courseArrayList).get(0).getCRN());
        assertEquals("37453", CourseGrade.filteredByInstructor("peter",
                courseArrayList).get(0).getCRN());
    }

    @Test
    public void filteredByCourseNumRange(){
        assertEquals("37453", CourseGrade.filteredByCourseNumRange(200,
                400, courseArrayList).get(0).getCRN());
    }

    @Test
    public void numberOfStudentsTest(){
        assertEquals(22, CourseGrade.numberOfStudents(courseArrayList.get(0)));
    }

    @Test
    public void filteredByStudentNumTest(){
        assertEquals("37453", CourseGrade.filteredByStudentNum(CourseGrade.numberOfStudents(courseArrayList.get(0)),
                50, courseArrayList).get(0).getCRN());
    }

    @Test
    public void filteredByTermTest(){
        ArrayList<Course> allCourses = CourseGrade.loadAllJsonFlies();
        assertEquals("37453", CourseGrade.filteredByTerm("120135", allCourses).get(0).getCRN());
    }

    @Test
    public void filteredByCourseAndInstructorTest(){
        ArrayList<Course> coursesIntest = CourseGrade.filteredByCourseAndInstructor("math", 241,
                "Lakeland", courseArrayList);
        assertEquals("33477", coursesIntest.get(0).getCRN());
    }

    


}
