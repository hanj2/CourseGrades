import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class FilterTest {
    private static final String TEST_JSON = Data.getFileContentsAsString("TestExample.json");
    private static final ArrayList<Course> EMPTY_ARRAYLIST = new ArrayList<>();
    private  ArrayList<Course> testArrayList = new ArrayList<>();

    @Before
    public void setUpForFilteringTests(){
        Gson gson = new Gson();
        testArrayList = gson.fromJson(TEST_JSON, new TypeToken<List<Course>>(){}.getType());
    }


}
