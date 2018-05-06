import java.io.Serializable;

/**
 * @author 范博涛 15130110029 565267339@qq.com
 */
public interface PIMEntity extends Serializable
{
    String getPriority();

    void setPriority(String priority);

    void fromString(String string);

    String toString();
}
