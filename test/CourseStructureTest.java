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
    private static final String COURSES_OF_SUMMER_2013_JSON = Data.getFileContentsAsString("Summer2013.json");
    private  static final ArrayList<Course> EMPTY_ARRAYLIST = new ArrayList<>();
    private  ArrayList<Course> courseArrayList;

    @Before
    public void setUpForLoadingTests(){
        Gson gson = new Gson();
        courseArrayList = gson.fromJson(COURSES_OF_SUMMER_2013_JSON, new TypeToken<List<Course>>(){}.getType());
    }

    //loading methods tests
    @Test
    public void loadJsonTest(){
        ArrayList<Course> coursesInTest = CourseStructure.loadJson(COURSES_OF_SUMMER_2013_JSON);
        boolean isEquals = true;
        if (courseArrayList.size() != coursesInTest.size()){
            isEquals = false;
        }
        for (int i = 0; i < courseArrayList.size(); i++){
            if(!Course.areCoursesEqual(courseArrayList.get(i),coursesInTest.get(i))){
                isEquals = false;
            }
        }
        assertTrue(isEquals);
    }

    @Test
    public void loadJsonByFileName(){
        ArrayList<Course> coursesInTest = CourseStructure.loadJsonByFileName("Summer2013.json");
        boolean isEquals = true;
        if (courseArrayList.size() != coursesInTest.size()){
            isEquals = false;
        }
        for (int i = 0; i < courseArrayList.size(); i++){
            if(!Course.areCoursesEqual(courseArrayList.get(i),coursesInTest.get(i))){
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
        ArrayList<Course> coursesInTest = CourseStructure.loadAllJsonFlies();
        int allCourses = NUM_OF_COURSES_FA2013 + NUM_OF_COURSES_FA2014 + NUM_OF_COURSES_SP2013
                + NUM_OF_COURSES_SP2014 + NUM_OF_COURSES_SU2013 + NUM_OF_COURSES_SU2014;
        assertEquals(allCourses, coursesInTest.size());
        assertEquals("41758", coursesInTest.get(0).getCRN());
    }

    //filtering methods tests
    @Test
    public void filteredBySubjectTest(){
        assertEquals("30486", CourseStructure.filteredBySubject("THEA", courseArrayList).get(0).getCRN());
    }

    @Test
    public void filteredByInstructorTest(){
        assertEquals("37453", CourseStructure.filteredByInstructor("Peter",
                courseArrayList).get(0).getCRN());
        assertEquals("37453", CourseStructure.filteredByInstructor("peter",
                courseArrayList).get(0).getCRN());
    }

    @Test
    public void filteredByCourseNumRange(){
        assertEquals("37453", CourseStructure.filteredByCourseNumRange(200,
                400, courseArrayList).get(0).getCRN());
    }

    @Test
    public void numberOfStudentsTest(){
        assertEquals(22, CourseStructure.numberOfStudents(courseArrayList.get(0)));
    }

    @Test
    public void filteredByStudentNumTest(){
        assertEquals("37453",
                CourseStructure.filteredByStudentNum(CourseStructure.numberOfStudents(courseArrayList.get(0)),
                50, courseArrayList).get(0).getCRN());
    }

    @Test
    public void filteredByTermTest(){
        ArrayList<Course> allCourses = CourseStructure.loadAllJsonFlies();
        assertEquals("37453", CourseStructure.filteredByTerm("120135", allCourses).get(0).getCRN());
    }

    @Test
    public void filteredByCourseAndInstructorTest(){
        ArrayList<Course> coursesIntest = CourseStructure.filteredByCourseAndInstructor("math",
                241, "Lakeland", courseArrayList);
        assertEquals("33477", coursesIntest.get(0).getCRN());
    }

    //aggregation method tests
    @Test
    public void totalNumOfStudentsTest(){
        ArrayList<Course> coursesInTest = new ArrayList<>();
        coursesInTest.add(courseArrayList.get(0));
        coursesInTest.add(courseArrayList.get(1));
        assertEquals(45,CourseStructure.totalNumOfStudents(coursesInTest));
    }

    @Test
    public void studentsIngGradeRangeTest(){
        ArrayList<Course> coursesInTest = new ArrayList<>();
        coursesInTest.add(courseArrayList.get(0));
        coursesInTest.add(courseArrayList.get(1));
        assertEquals(17, CourseStructure.studentsInGradeRange("a-","A+",coursesInTest));
    }

    private static final double MAX_ERROR_RANGE = 0.001;
    @Test
    public void meanOfGradeWeightTest(){
        ArrayList<Course> coursesInTest = new ArrayList<>();
        coursesInTest.add(courseArrayList.get(0));
        coursesInTest.add(courseArrayList.get(1));
        double expected = 3.257;
        assertTrue(Math.abs(expected -  CourseStructure.meanOfGradeWeight(coursesInTest)) <= MAX_ERROR_RANGE);
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
            CourseStructure.filteredBySubject("MATH",null);
        }catch (IllegalArgumentException e){
            assertEquals(ErrorMessage.NULL_COURSE, e.getMessage());
        }
        try {
            CourseStructure.filteredBySubject("fakeSubject", courseArrayList);
        }catch (Error e){
            assertEquals(ErrorMessage.NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void filteredByCourseNumberRangeErrorTest(){
        try {
            CourseStructure.filteredByCourseNumRange(200, 100, courseArrayList);
        } catch (Error e){
            assertEquals( ErrorMessage.INVALID_BOUNDS, e.getMessage());
        }
    }

    @Test
    public void studentsInGradeRangeErrorTest(){
        try {
            CourseStructure.studentsInGradeRange("F-", "B-", courseArrayList);
        }catch (IndexOutOfBoundsException e){
            assertEquals(ErrorMessage.INVALID_GRADE, e.getMessage());
        }
        try {
            CourseStructure.studentsInGradeRange("A+", "B-", courseArrayList);
        }catch (Error e){
            assertEquals(ErrorMessage.INVALID_BOUNDS, e.getMessage());
        }
    }

}
