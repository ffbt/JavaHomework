import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息
 *
 * @author 范博涛 15130110029 565267339@qq.com
 */
public class User implements Serializable
{
    private String username;
    private String password;

    /**
     * @param username 用户名
     * @param password 密码
     */
    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public User()
    {
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * 验证用户信息
     *
     * @param username 用户名
     * @param password 密码
     * @return 若验证成功，返回User对象，否则返回null
     */
    public static User verify(String username, String password)
    {
        File file = new File("user.txt");
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file)))
        {
            User user;
            while ((user = (User) ois.readObject()) != null)
            {
                if (user.getUsername().equals(username) && user.getPassword().equals(password))
                    return user;
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
//        return null;
        return new User(username, password);
    }

    public static void main(String[] args) throws Exception
    {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++)
        {
            User user = new User();
            user.setUsername("user" + i);
            user.setPassword("user" + i);
            users.add(user);
        }
        File file = new File("user.txt");
        if (!file.exists())
            file.createNewFile();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        for (User user : users)
            oos.writeObject(user);
        oos.close();
    }
}
