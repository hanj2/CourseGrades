import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CourseStructureTest {
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
        ArrayList<Course> coursesInTest = Load.loadJson(TEST_JSON);
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
        ArrayList<Course> coursesInTest = Load.loadJsonByFileName("TestExample.json");
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
        ArrayList<Course> coursesInTest = Load.loadAllJsonFlies();
        int allCourses = NUM_OF_COURSES_FA2013 + NUM_OF_COURSES_FA2014 + NUM_OF_COURSES_SP2013
                + NUM_OF_COURSES_SP2014 + NUM_OF_COURSES_SU2013 + NUM_OF_COURSES_SU2014;
        assertEquals(allCourses, coursesInTest.size());
        assertEquals("41758", coursesInTest.get(0).getCRN());
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

    //aggregation method tests
    @Test
    public void totalNumOfStudentsTest(){
        ArrayList<Course> coursesInTest = new ArrayList<>();
        coursesInTest.add(testArrayList.get(0));
        coursesInTest.add(testArrayList.get(1));
        assertEquals(67,Aggregator.totalNumOfStudents(coursesInTest));
    }

    @Test
    public void studentsIngGradeRangeTest(){
        assertEquals(5, Aggregator.studentsInGradeRange("W","F",testArrayList));
    }

    private static final double MAX_ERROR_RANGE = 0.001;
    @Test
    public void meanOfGradeWeightTest(){
        ArrayList<Course> coursesInTest = new ArrayList<>();
        coursesInTest.add(testArrayList.get(0));
        coursesInTest.add(testArrayList.get(1));
        assertEquals(3.7276, Aggregator.meanOfGradeWeight(coursesInTest), MAX_ERROR_RANGE);
    }

    //Exception tests
    @Test
    public void loadJsonErrorTest(){
        try {
            Load.loadJson(null);
        } catch (NullPointerException e){
            assertEquals(ErrorMessage.NULL_JSON_FILE, e.getMessage());
        }
    }
    @Test
    public void loadJsonByFileNameErrorTest(){
        try {
            Load.loadJsonByFileName(null);
        } catch (NullPointerException e){
            assertEquals(e.getMessage(), ErrorMessage.NULL_JSON_FILE_NAME);
        }
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
        } try {
            Filter.filteredBySubject("david",null);
        } catch (IllegalArgumentException e){
            assertEquals(ErrorMessage.NULL_COURSE, e.getMessage());
        } try {
            Filter.filteredBySubject("fakeInstructor", testArrayList);
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
        }try {
            Filter.filteredBySubject("fakeInstructor", testArrayList);
        }catch (Error e){
            assertEquals(ErrorMessage.NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void studentsInGradeRangeErrorTest(){
        try {
            Aggregator.studentsInGradeRange("F-", "B-", testArrayList);
        }catch (IndexOutOfBoundsException e){
            assertEquals(ErrorMessage.INVALID_GRADE, e.getMessage());
        }
        try {
            Aggregator.studentsInGradeRange("A+", "B-", testArrayList);
        }catch (Error e){
            assertEquals(ErrorMessage.INVALID_BOUNDS, e.getMessage());
        }
    }
}
