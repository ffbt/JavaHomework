package jni;

public class HelloWorld
{
    public native void displayHelloWorld();

    static
    {
        System.loadLibrary("HelloWorldImpl");
    }

    public static void main(String[] args)
    {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.displayHelloWorld();
    }
}
