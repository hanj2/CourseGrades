import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FilterTest {
    private static final String TEST_JSON = Data.getFileContentsAsString("TestExample.json");
    private static final ArrayList<Course> EMPTY_ARRAYLIST = new ArrayList<>();
    private  ArrayList<Course> testArrayList = new ArrayList<>();

    @Before
    public void setUpForFilteringTests(){
        Gson gson = new Gson();
        testArrayList = gson.fromJson(TEST_JSON, new TypeToken<List<Course>>(){}.getType());
    }

    //filtering methods tests
    @Test
    public void filteredBySubjectTest(){
        assertEquals("34304", Filter.filteredBySubject("STAT", testArrayList).get(0).getCRN());
    }

    @Test
    public void filteredByInstructorTest(){
        assertEquals("34304", Filter.filteredByInstructor("David",
                testArrayList).get(0).getCRN());
        assertEquals("34304", Filter.filteredByInstructor("david",
                testArrayList).get(0).getCRN());
    }

    @Test
    public void filteredByCourseNumRange(){
        assertEquals("30486", Filter.filteredByCourseNumRange(100,
                200, testArrayList).get(0).getCRN());
    }

    @Test
    public void numberOfStudentsTest(){
        assertEquals(35, Filter.numberOfStudents(testArrayList.get(0)));
    }

    @Test
    public void filteredByStudentNumTest(){
        assertEquals("34304",
                Filter.filteredByStudentNum(35,35, testArrayList).get(0).getCRN());
    }

    @Test
    public void filteredByTermTest(){
        ArrayList<Course> allCourses = Load.loadAllJsonFlies();
        assertEquals("37453", Filter.filteredByTerm("120135", allCourses).get(0).getCRN());
    }

    @Test
    public void filteredByCourseAndInstructorTest(){
        ArrayList<Course> coursesIntest = Filter.filteredByCourseAndInstructor("stat",
                440, "david", testArrayList);
        assertEquals("34304", coursesIntest.get(0).getCRN());
    }

    private static final double MAX_ERROR_RANGE = 0.001;

    //filtering error tests
    @Test
    public void meanOfGradeWeightTest(){
        ArrayList<Course> coursesInTest = new ArrayList<>();
        coursesInTest.add(testArrayList.get(0));
        coursesInTest.add(testArrayList.get(1));
        assertEquals(3.7276, Aggregator.meanOfGradeWeight(coursesInTest), MAX_ERROR_RANGE);
    }
    @Test
    public void filteredBySubjectErrorTest(){
        try {
            Filter.filteredBySubject(null, EMPTY_ARRAYLIST);
        } catch (IllegalArgumentException e){
            assertEquals(ErrorMessage.EMPTY_COURSE_ARRAYLIST, e.getMessage());
        } catch (NullPointerException e) {
            assertEquals(ErrorMessage.NULL_SUBJECT, e.getMessage());
        }
        try {
            Filter.filteredBySubject("STAT",null);
        }catch (IllegalArgumentException e){
            assertEquals(ErrorMessage.NULL_COURSE, e.getMessage());
        }
        try {
            Filter.filteredBySubject("fakeSubject", testArrayList);
        }catch (Error e){
            assertEquals(ErrorMessage.NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void filteredByInstructorErrorTest(){
        try {
            Filter.filteredByInstructor(null, EMPTY_ARRAYLIST);
        } catch (IllegalArgumentException e){
            assertEquals(ErrorMessage.EMPTY_COURSE_ARRAYLIST, e.getMessage());
        } catch (NullPointerException e) {
            assertEquals(ErrorMessage.NULL_INSTRUCTOR_NAME, e.getMessage());
        }
        try {
            Filter.filteredByInstructor("david",null);
        } catch (IllegalArgumentException e){
            assertEquals(ErrorMessage.NULL_COURSE, e.getMessage());
        }
        try {
            Filter.filteredByInstructor("fakeInstructor", testArrayList);
        }catch (Error e){
            assertEquals(ErrorMessage.NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void filteredByCourseNumberRangeErrorTest(){
        try {
            Filter.filteredByCourseNumRange(200, 100, EMPTY_ARRAYLIST);
        }catch (Error e){
            assertEquals( ErrorMessage.INVALID_BOUNDS, e.getMessage());
        }catch (IllegalArgumentException e){
            assertEquals(ErrorMessage.EMPTY_COURSE_ARRAYLIST, e.getMessage());
        }
        try {
            Filter.filteredByCourseNumRange(800,900, testArrayList);
        }catch (Error e){
            assertEquals(ErrorMessage.NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void filteredByStudNumErrorTest(){
        try {
            Filter.filteredByStudentNum(200, 100, EMPTY_ARRAYLIST);
        }catch (Error e){
            assertEquals( ErrorMessage.INVALID_BOUNDS, e.getMessage());
        }catch (IllegalArgumentException e){
            assertEquals(ErrorMessage.EMPTY_COURSE_ARRAYLIST, e.getMessage());
        }
        try {
            Filter.filteredByCourseNumRange(10000,90099, testArrayList);
        }catch (Error e){
            assertEquals(ErrorMessage.NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void filteredByTermErrorTest(){
        try {
            Filter.filteredByTerm(null, EMPTY_ARRAYLIST);
        } catch (IllegalArgumentException e){
            assertEquals(ErrorMessage.EMPTY_COURSE_ARRAYLIST, e.getMessage());
        } catch (NullPointerException e) {
            assertEquals(ErrorMessage.NULL_TERM, e.getMessage());
        }
        try {
            Filter.filteredByTerm("120135",null);
        }catch (IllegalArgumentException e){
            assertEquals(ErrorMessage.NULL_COURSE, e.getMessage());
        }
        try {
            Filter.filteredBySubject("fakeTerm", testArrayList);
        }catch (Error e){
            assertEquals(ErrorMessage.NOT_FOUND, e.getMessage());
        }
    }

}
