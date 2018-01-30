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

    //filtering methods tests
    @Test
    public void filteredBySubjectTest(){
        assertEquals("34304", CourseStructure.filteredBySubject("STAT", testArrayList).get(0).getCRN());
    }

    @Test
    public void filteredByInstructorTest(){
        assertEquals("34304", CourseStructure.filteredByInstructor("David",
                testArrayList).get(0).getCRN());
        assertEquals("34304", CourseStructure.filteredByInstructor("david",
                testArrayList).get(0).getCRN());
    }

    @Test
    public void filteredByCourseNumRange(){
        assertEquals("30486", CourseStructure.filteredByCourseNumRange(100,
                200, testArrayList).get(0).getCRN());
    }

    @Test
    public void numberOfStudentsTest(){
        assertEquals(35, CourseStructure.numberOfStudents(testArrayList.get(0)));
    }

    @Test
    public void filteredByStudentNumTest(){
        assertEquals("34304",
                CourseStructure.filteredByStudentNum(35,35, testArrayList).get(0).getCRN());
    }

    @Test
    public void filteredByTermTest(){
        ArrayList<Course> allCourses = CourseStructure.loadAllJsonFlies();
        assertEquals("37453", CourseStructure.filteredByTerm("120135", allCourses).get(0).getCRN());
    }

    @Test
    public void filteredByCourseAndInstructorTest(){
        ArrayList<Course> coursesIntest = CourseStructure.filteredByCourseAndInstructor("stat",
                440, "david", testArrayList);
        assertEquals("34304", coursesIntest.get(0).getCRN());
    }

    //aggregation method tests
    @Test
    public void totalNumOfStudentsTest(){
        ArrayList<Course> coursesInTest = new ArrayList<>();
        coursesInTest.add(testArrayList.get(0));
        coursesInTest.add(testArrayList.get(1));
        assertEquals(67,CourseStructure.totalNumOfStudents(coursesInTest));
    }

    @Test
    public void studentsIngGradeRangeTest(){
        assertEquals(5, CourseStructure.studentsInGradeRange("W","F",testArrayList));
    }

    private static final double MAX_ERROR_RANGE = 0.001;
    @Test
    public void meanOfGradeWeightTest(){
        ArrayList<Course> coursesInTest = new ArrayList<>();
        coursesInTest.add(testArrayList.get(0));
        coursesInTest.add(testArrayList.get(1));
        assertEquals(3.7276, CourseStructure.meanOfGradeWeight(coursesInTest), MAX_ERROR_RANGE);
    }

    //Exception tests
    @Test
    public void loadJsonErrorTest(){
        try {
            CourseStructure.loadJson(null);
        } catch (NullPointerException e){
            assertEquals(ErrorMessage.NULL_JSON_FILE, e.getMessage());
        }
    }
    @Test
    public void loadJsonByFileNameErrorTest(){
        try {
            CourseStructure.loadJsonByFileName(null);
        } catch (NullPointerException e){
            assertEquals(e.getMessage(), ErrorMessage.NULL_JSON_FILE_NAME);
        }
    }
    @Test
    public void filteredBySubjectErrorTest(){
        try {
             CourseStructure.filteredBySubject(null, EMPTY_ARRAYLIST);
        } catch (IllegalArgumentException e){
            assertEquals(ErrorMessage.EMPTY_COURSE_ARRAYLIST, e.getMessage());
        } catch (NullPointerException e) {
            assertEquals(ErrorMessage.NULL_SUBJECT, e.getMessage());
        }
        try {
            CourseStructure.filteredBySubject("STAT",null);
        }catch (IllegalArgumentException e){
            assertEquals(ErrorMessage.NULL_COURSE, e.getMessage());
        }
        try {
            CourseStructure.filteredBySubject("fakeSubject", testArrayList);
        }catch (Error e){
            assertEquals(ErrorMessage.NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void filteredByInstructorErrorTest(){
        try {
            CourseStructure.filteredByInstructor(null, EMPTY_ARRAYLIST);
        } catch (IllegalArgumentException e){
            assertEquals(ErrorMessage.EMPTY_COURSE_ARRAYLIST, e.getMessage());
        } catch (NullPointerException e) {
            assertEquals(ErrorMessage.NULL_INSTRUCTOR_NAME, e.getMessage());
        } try {
            CourseStructure.filteredBySubject("david",null);
        } catch (IllegalArgumentException e){
            assertEquals(ErrorMessage.NULL_COURSE, e.getMessage());
        } try {
            CourseStructure.filteredBySubject("fakeInstructor", testArrayList);
        }catch (Error e){
            assertEquals(ErrorMessage.NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void filteredByCourseNumberRangeErrorTest(){
        try {
            CourseStructure.filteredByCourseNumRange(200, 100, EMPTY_ARRAYLIST);
        }catch (Error e){
            assertEquals( ErrorMessage.INVALID_BOUNDS, e.getMessage());
        }catch (IllegalArgumentException e){
            assertEquals(ErrorMessage.EMPTY_COURSE_ARRAYLIST, e.getMessage());
        }try {
            CourseStructure.filteredBySubject("fakeInstructor", testArrayList);
        }catch (Error e){
            assertEquals(ErrorMessage.NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void studentsInGradeRangeErrorTest(){
        try {
            CourseStructure.studentsInGradeRange("F-", "B-", testArrayList);
        }catch (IndexOutOfBoundsException e){
            assertEquals(ErrorMessage.INVALID_GRADE, e.getMessage());
        }
        try {
            CourseStructure.studentsInGradeRange("A+", "B-", testArrayList);
        }catch (Error e){
            assertEquals(ErrorMessage.INVALID_BOUNDS, e.getMessage());
        }
    }
}
