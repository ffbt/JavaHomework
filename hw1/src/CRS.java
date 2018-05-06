import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class CRS
{
    private List<Student> students = new ArrayList<>();
    private Map<String, Course> courseMap = new HashMap<>();

    public Map<String, Course> getCourseMap()
    {
        return courseMap;
    }

    public CRS()
    {
        List<Book> javaBooks = new ArrayList<>();
        javaBooks.add(new Book("Thinking in Java"));
        javaBooks.add(new Book("Java 8"));
        Course java = new Course("Java", javaBooks);
        courseMap.put("Java", java);
        List<Book> webEngineeringBooks = new ArrayList<>();
        webEngineeringBooks.add(new Book("Web Engineering"));
        Course webEngineering = new Course("WebEngineering", webEngineeringBooks);
        courseMap.put("WebEngineering", webEngineering);
    }

    public List<Student> getStudents()
    {
        return students;
    }

    public static void main(String[] args)
    {
        CRS crs = new CRS();
        if (args.length < 1)
            System.exit(1);
        Student student = new Student(args[0]);
        List<Course> courses = new ArrayList<>();
        System.out.print(student.getId() + " select ");
        for (int i = 1; i < args.length; i++)
        {
            if (i != 1)
                System.out.print("; and ");
            String courseName = args[i];
            System.out.print(courseName + " with book(s) ");
            Course course = crs.getCourseMap().get(courseName);
            courses.add(course);
            List<Book> books = course.getBooks();
            for (int j = 0; j < books.size(); j++)
            {
                if (j != 0)
                    System.out.print(", ");
                System.out.print(books.get(j).getName());
            }
        }
        System.out.println(".");
        student.setCourses(courses);
        crs.getStudents().add(student);
    }
}

class Course
{
    private String name;
    private List<Book> books;

    public Course()
    {

    }

    public Course(String name)
    {
        this.name = name;
    }

    public Course(String name, List<Book> books)
    {
        this.name = name;
        this.books = books;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Book> getBooks()
    {
        return books;
    }

    public void setBooks(List<Book> books)
    {
        this.books = books;
    }
}

class Book
{
    private String name;

    public Book()
    {

    }

    public Book(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}

class Student
{
    private String id;
    private List<Course> courses;

    public Student()
    {
        courses = new ArrayList<>();
    }

    public Student(String id, List<Course> courses)
    {
        this.id = id;
        this.courses = courses;
    }

    public Student(String id, Course course)
    {
        courses = new ArrayList<>();
        courses.add(course);
        this.id = id;
    }

    public Student(String id)
    {
        this(id, new ArrayList<>());
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public List<Course> getCourses()
    {
        return courses;
    }

    public void setCourses(List<Course> courses)
    {
        this.courses = courses;
    }
}
