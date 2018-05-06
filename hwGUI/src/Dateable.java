import java.util.Date;

/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public interface Dateable
{
    boolean setDate(String date);

    void setDate(Date date);

    String getDate();
}
