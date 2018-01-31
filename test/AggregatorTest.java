import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AggregatorTest {
    private static final String TEST_JSON = Data.getFileContentsAsString("TestExample.json");
    private  ArrayList<Course> testArrayList = new ArrayList<>();

    @Before
    public void setUpForAggregatorTests(){
        Gson gson = new Gson();
        testArrayList = gson.fromJson(TEST_JSON, new TypeToken<List<Course>>(){}.getType());
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
