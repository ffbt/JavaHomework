import java.io.Serializable;

public interface PIMEntity extends Serializable
{
    String getPriority();

    void setPriority(String priority);

    void fromString(String string);

    String toString();
}
