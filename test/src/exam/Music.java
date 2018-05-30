package exam;

class Note
{
    private int value;

    private Note(int val)
    {
        value = val;
    }

    public static final Note
            MIDDLE_C = new Note(0),
            C_SHARP = new Note(1),
            B_FLAT = new Note(2);
}

class Instrument
{
    public void play(Note n)
    {
        System.out.println("Instrument.play()");
    }
}

class Wind extends Instrument
{
    @Override
    public void play(Note n)
    {
        System.out.println("Wind.play()");
    }
}

public class Music
{
    public static void tune(Instrument i)
    {
        i.play(Note.MIDDLE_C);
    }

    public static void main(String[] args)
    {
        Wind flute = new Wind();
        tune(flute);
    }
}
