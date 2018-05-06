import java.util.*;

public class Test
{
    public static void main(String[] args)
    {
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            Student student = new Student();
            student.setId(i);
            student.setName("name" + i);
            list.add(student);
        }
        LinkedList<Student> linkedList = new LinkedList<>();
        for (Student student : list)
        {
            linkedList.add(student);
        }
        linkedList.get(5).setName("name11");
        for (Student student : list)
        {
            System.out.println(student.getId() + " " + student.getName());
        }
    }
}

class Student
{
    int id;
    String name;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
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
