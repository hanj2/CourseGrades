import java.util.ArrayList;
/**
 * a class that have all the filtering methods
 */
public class Filter {
    public static ArrayList<Course> filteredBySubject
            (String subject, ArrayList<Course> courses) throws NullPointerException, IllegalArgumentException{
        if (subject == null){
            throw new NullPointerException(ErrorMessage.NULL_SUBJECT);
        }
        if (courses == null){
            throw new IllegalArgumentException(ErrorMessage.NULL_COURSE);
        }
        if (courses.size() == 0){
            throw new IllegalArgumentException(ErrorMessage.EMPTY_COURSE_ARRAYLIST);
        }

        ArrayList<Course> filteredCourses = new ArrayList<>();
        for (Course course : courses){
            String subjectOfCourse = course.getSubject();
            if (subjectOfCourse.equalsIgnoreCase(subject)){
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
    }

    //filtered by the given string in the instructor's name case-insensitively
    public static ArrayList<Course> filteredByInstructor(String stringInName, ArrayList<Course> courses)
            throws IllegalArgumentException, NullPointerException{
        if (stringInName == null){
            throw new NullPointerException(ErrorMessage.NULL_INSTRUCTOR_NAME);
        }
        if (courses == null){
            throw new IllegalArgumentException(ErrorMessage.NULL_COURSE);
        }
        if (courses.size() == 0){
            throw new IllegalArgumentException(ErrorMessage.EMPTY_COURSE_ARRAYLIST);
        }

        ArrayList<Course> filteredCourses = new ArrayList<>();
        for (Course course:courses){
            String nameOfInstructor = course.getInstructor().toLowerCase();
            if (nameOfInstructor.contains(stringInName.toLowerCase())){
                filteredCourses.add(course);
            }
        }
        if (filteredCourses.size() == 0){
            throw new Error(ErrorMessage.NOT_FOUND);
        }
        return filteredCourses;
    }

    //filtered by the given class number range, inclusively
    //three parameters: upperBound and LowerBound and course ArrayList
    public static ArrayList<Course> filteredByCourseNumRange(int lowerBound, int upperBound, ArrayList<Course> courses)
            throws Error, NullPointerException, IllegalArgumentException{
        if (lowerBound > upperBound){
            throw new Error(ErrorMessage.INVALID_BOUNDS);
        }
        if (courses == null){
            throw new NullPointerException(ErrorMessage.NULL_COURSE);
        }
        if (courses.size() == 0){
            throw new IllegalArgumentException(ErrorMessage.EMPTY_COURSE_ARRAYLIST);
        }

        ArrayList<Course> filteredCourses = new ArrayList<>();
        for (Course course:courses){
            int courseNum = course.getNumber();
            if (courseNum >= lowerBound && courseNum <= upperBound){
                filteredCourses.add(course);
            }
        }
        if (filteredCourses.size() == 0){
            throw new Error(ErrorMessage.NOT_FOUND);
        }
        return filteredCourses;
    }

    //a helper function that calculates the number of students of a course
    public static int numberOfStudents(Course course) throws IllegalArgumentException{
        if (course == null){
            throw new IllegalArgumentException(ErrorMessage.NULL_COURSE);
        }
        int[] grades = course.getGrades();
        int sumOfStudents = 0;
        for (int grade : grades){
            sumOfStudents += grade;
        }
        return sumOfStudents;
    }

    //filtered by range of student number, inclusively
    public static ArrayList<Course> filteredByStudentNum(int lowerBound, int upperBound, ArrayList<Course> courses)
            throws IllegalArgumentException, Error{
        if (lowerBound > upperBound){
            throw new Error(ErrorMessage.INVALID_BOUNDS);
        }
        if (courses == null){
            throw new IllegalArgumentException(ErrorMessage.NULL_COURSE);
        }
        if (courses.size() == 0){
            throw new IllegalArgumentException(ErrorMessage.EMPTY_COURSE_ARRAYLIST);
        }

        ArrayList<Course> filteredCourses = new ArrayList<>();
        for (Course course:courses){
            int studentNum = numberOfStudents(course);
            if (studentNum >= lowerBound && studentNum <= upperBound){
                filteredCourses.add(course);
            }
        }
        if (filteredCourses.size() == 0){
            throw new Error(ErrorMessage.NOT_FOUND);
        }
        return filteredCourses;
    }

    //filtered by term/semester, case in-sensitively
    public static ArrayList<Course> filteredByTerm (String term, ArrayList<Course> courses)
            throws NullPointerException, IllegalArgumentException, Error{
        if (term == null){
            throw new IllegalArgumentException(ErrorMessage.NULL_TERM);
        }
        if (courses == null){
            throw new IllegalArgumentException(ErrorMessage.NULL_COURSE);
        }
        if (courses.size() == 0){
            throw new IllegalArgumentException(ErrorMessage.EMPTY_COURSE_ARRAYLIST);
        }

        ArrayList<Course> filteredCourses = new ArrayList<>();
        for (Course course:courses){
            String termOfCourse = course.getTerm();
            if (termOfCourse.equalsIgnoreCase(term)){
                filteredCourses.add(course);
            }
        }
        if (filteredCourses.size() == 0){
            throw new Error(ErrorMessage.NOT_FOUND);
        }
        return filteredCourses;
    }

    //filtered by subject, course number and substring of instructor's name
    //first filtered by subject and instructor, then by course number
    public static ArrayList<Course> filteredByCourseAndInstructor (String subject, int courseNumber,
                                                                   String nameOfInstructor, ArrayList<Course> courses)
            throws NullPointerException, IllegalArgumentException, Error{
        if (subject == null){
            throw new NullPointerException(ErrorMessage.INVALID_BOUNDS);
        }
        if (nameOfInstructor == null){
            throw new NullPointerException(ErrorMessage.NULL_INSTRUCTOR_NAME);
        }
        if (courses == null){
            throw new IllegalArgumentException(ErrorMessage.NULL_COURSE);
        }
        if (courses.size() == 0){
            throw new IllegalArgumentException(ErrorMessage.EMPTY_COURSE_ARRAYLIST);
        }

        courses = filteredByInstructor(nameOfInstructor, filteredBySubject(subject,courses));
        ArrayList<Course> filteredCourses = new ArrayList<>();
        for (Course course : courses){
            if (course.getNumber() == courseNumber){
                filteredCourses.add(course);
            }
        }
        if (filteredCourses.size() == 0){
            throw new Error(ErrorMessage.NOT_FOUND);
        }
        return filteredCourses;
    }
}
