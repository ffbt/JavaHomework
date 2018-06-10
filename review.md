# Java

## OOP-Lang

### `switch-case`

参数可以是 `byte`, `short`, `int`, `char` 及其包装类型以及 `Enum`, `String`.

### Array literals

**used only for initialization**

```java
int[] a = {1, 3, 5, 7, 9};
```

### Code point

```java
String smile = "😁";
System.out.println(smile.length());
System.out.printf("0x%x\n", smile.codePointAt(0));
System.out.printf("0x%x\n", smile.codePointAt(1));

String pi = "π";
System.out.println(pi.length());
System.out.printf("0x%x\n", pi.codePointAt(0));
```

output:

```java
2
0x1f601
0xde01
1
0x3c0
```

### `goto`

- `break`:

  ```java
  outer:
  for (int i = 0; i < 2; i++)
  {
      for (int j = 0; j < 3; j++)
      {
          if (j == 1)
              break outer;
          System.out.println("i, j = " + i + ", " + j);
      }
  }
  ```

  output:

  ```java
  i, j = 0, 0
  ```

- `continue`:

  ```java
  outer:
  for (int i = 0; i < 2; i++)
  {
      for (int j = 0; j < 3; j++)
      {
          if (j == 1)
              continue outer;
          System.out.println("i, j = " + i + ", " + j);
      }
  }
  ```

  output:

  ```java
  i, j = 0, 0
  i, j = 1, 0
  ```

## Initialization & Cleanup

### Overloading with primitives

> If you have a data type that is **smaller** than the argument in the method, that data type is **promoted**.

> If your argument is **bigger** than the argument expected by the overloaded method, you must **cast** to the necessary type by placing the type name inside parentheses.
> – If you don’t do this, the compiler will issue an **error** message
> – narrowing conversion

### Member initialization

- 类中的成员会被初始化（基本类型：0， 引用类型：null）

- 方法中的成员不会被初始化

  ```java
  void f() 
  {
      int i;
      i++; 	// compile-time error
  }
  ```

## Access Control

### Legalizing package names

|          Domain Name          |      Package Name Prefix      |
| :---------------------------: | :---------------------------: |
| `hyphenated-name.example.org` | `org.example.hyphenated_name` |
|         `example.int`         |        `int_.example`         |
|     `123name.example.com`     |    `com.example._123name`     |

### Static import

```java
import static java.lang.Math.PI;
import static java.lang.Math.*;
```

### [Top 10 Mistakes Java Developers](https://www.programcreek.com/2014/05/top-10-mistakes-java-developers-make/)

**note**:

1. `java.util.ArrayList` or `java.util.Arrays.ArrayList`

2. `Arrays.asList(arr).contains(targetValue);`

3. remove an element from a list

   ```java
   ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
   list.removeIf(s -> s.equals("a"));
   ```

   or

   ```java
   Iterator<String> iter = list.iterator();
   while (iter.hasNext()) 
   {
   	String s = iter.next();
   	if (s.equals("a"))
   		iter.remove();	// 使用迭代器的remove()方法
   }
   ```

   source code:

   ```java
   public Iterator<E> iterator() 
   {
   	return new Itr();
   }
   
   private class Itr implements Iterator<E>
   {
   	// ...
       public E next() 
       {
           // ...
           int i = cursor;
           // ...
           cursor = i + 1;
           return (E) elementData[lastRet = i];
       }
   	// ...
   }
   ```

   **the following code will throw **`ConcurrentModificationException`

   ```java
   for (String s : list) 
   {
   	if (s.equals("a"))
   		list.remove(s);		// 使用list的remove()方法
   }
   ```

   ==

   ```java
   for (Iterator<String> iter = list.iterator(); iter.hasNext(); )
   {
       String s = (String) iter.next();
       if (s.equals("a"))
   		list.remove(s);
   }
   ```

   > Iterator 是工作在一个独立的线程中，并且拥有一个 `mutex` 锁。 Iterator 被创建之后会建立一个指向原来对象的单链索引表，当原来的对象数量发生变化时，这个索引表的内容不会同步改变，所以当索引指针往后移动的时候就找不到要迭代的对象，所以按照 fail-fast 原则 Iterator 会马上抛出`java.util.ConcurrentModificationException`异常。 

4. no

5. no

6. no

7. no

8. [Why String is immutable in Java?](https://www.programcreek.com/2013/04/why-string-is-immutable-in-java/)

9. no

10. no

### Access level

> Note that a class cannot be private (that would make it accessible to no one but the class) or protected.

|                 作用域                  | Private | Default(Friendly) | Protected | Public |
| :-------------------------------------: | :-----: | :---------------: | :-------: | :----: |
|            只在本类内部访问             |    √    |         √         |     √     |   √    |
| 同一包中的类（包括子类，以及对象.成员） |         |         √         |   **√**   |   √    |
|           其他包中的子类内部            |         |                   |     √     |   √    |
|   其他包中的类（对象.成员，不是子类）   |         |                   |           |   √    |

## Reusing Classes

### Inheritance syntax

- call base-class method: `super().method();`
- 子类覆盖的方法同父类的方法要保持名称、返回值类型、参数列表的统一。
- 改写后的方法不能比被覆盖的方法有更严格的访问权限。
-  改写后的方法不能比被覆盖的方法产生更多的异常。

### final data

- Compile-time constant

  - Must be given a value at point of definition

    ```java
    final static int NINE = 9;
    ```

  - May be “folded” into a calculation by the compiler

    ```java
    final static int NINE = 3 * 3;
    ```

- Run-time constant

  - Cannot be changed from initialization value

    ```java
    final int RNUM = (int) (Math.random() * 20);
    ```

  - **final static**: only one instance per class, initialized at the time the class is loaded, cannot be changed.

  - **final references**: cannot be re-bound to other objects

## Polymorphism

### Dynamic Binding in Java

binding occurs at run time, based on the type of object. Late binding is also called **dynamic** binding or **run-time** binding.

### 静态变量与静态方法的继承

- 子类是不继承父类的 static 变量和方法的。因为这是属于类本身的。但是子类是可以访问的。 
- 子类和父类中同名的 static 变量和方法都是相互独立的，并不存在任何的重写的关系。 

```java
public class Static
{
    static class A
    {
        static int a = 1;
        static int b = 2;

        public static void printA()
        {
            System.out.println(a);
        }

        public static void printB()
        {
            System.out.println(b);
        }
    }

    static class B extends A
    {
        static int a = 3;
        static int b = 4;

        public static void printB()
        {
            System.out.println(b);
        }
    }

    public static void main(String[] args)
    {
        A.printA();
        A.printB();
        B.printA();
        B.printB();
        System.out.println(B.a);
    }
}
```

output:

```java
1
2
1
4
3
```

### 多态+方法重载练习

```java
public class Polymorphism
{
    public static void main(String[] args)
    {
        A a1 = new A();
        A a2 = new B();

        B b = new B();
        C c = new C();
        D d = new D();
        E e = new E();

        a1.show(b);     // A and A
        a1.show(c);     // A and A
        a1.show(d);     // A and D
        System.out.println(a1.s);   // 123

        a2.show(b);     // B and A
        a2.show(c);     // B and A
        a2.show(d);     // A and D
        //a2.show(e);	//这句编译不过
        System.out.println(a2.s); // 123

        b.show(b);      // B and B
        b.show(c);      // B and B
        b.show(d);      // A and D
        b.show(e);      // B and E
        System.out.println(b.s);    // 456
    }
}

class A 
{
    public String s = "123";

    public void show(A obj)
    {
        System.out.println("A and A");
    }

    public void show(D obj)
    {
        System.out.println("A and D");
    }
}

class B extends A
{
    public String s = "456";

    public void show(A obj)
    {
        System.out.println("B and A");
    }

    public void show(B obj)
    {
        System.out.println("B and B");
    }

    public void show(E obj)
    {
        System.out.println("B and E");
    }
}

class C extends B
{
}

class D extends B
{
}

class E
{
}
```

### 类初始化顺序

#### 示例一

```java
public class Sequence extends B
{
    public static void main(String[] args)
    {
        Sequence sequence;
        System.out.println("init");
        sequence = new Sequence();
    }

    private static Test sequence_static_test = new Test("sequence static");
    private Test sequence_normal_test = new Test("sequence normal");

    public Sequence()
    {
        System.out.println("Sequence()");
    }
}

class Test
{
    public Test(String info)
    {
        System.out.println(info + " test");
    }
}

class A
{
    private static Test father_static_test = new Test("father static");
    private Test father_normal_test = new Test("father normal");

    public A()
    {
        System.out.println("A()");
    }
}

class B extends A
{
    private static Test child_static_test = new Test("child static");
    private Test child_normal_test = new Test("child normal");

    public B()
    {
        System.out.println("B()");
    }
}
```

output:

```java
father static test
child static test
sequence static test
init
father normal test
A()
child normal test
B()
sequence normal test
Sequence()
```

------

#### 示例二

```java
public class Sequence
{
    public static void main(String[] args)
    {
        System.out.println("init");
//        new Sequence();
        new B();
    }

    private static Test sequence_static_test = new Test("sequence static");
    private Test sequence_normal_test = new Test("sequence normal");
}

class Test
{
    public Test(String info)
    {
        System.out.println(info + " test");
    }
}

class A
{
    private static Test father_static_test = new Test("father static");
    private Test father_normal_test = new Test("father normal");
}

class B extends A
{
    private static Test child_static_test = new Test("child static");
    private Test child_normal_test = new Test("child normal");
}
```

output:

```java
sequence static test
init
father static test
child static test
father normal test
child normal test
```

------

#### 示例三

```java
public class Sequence1
{
    public static void main(String[] args)
    {
        new RoundGlyph();
        System.out.println("================================================");
        new RoundGlyph(5);
    }
}

class Glyph
{
    void draw()
    {
        System.out.println("Glyph.draw()");     // 未被调用  Glyph.draw()
    }

    Glyph()
    {
        System.out.println("Glyph() before draw()");
        draw();     // 子类重写后，new子类对象--调用子类重写后的方法
        System.out.println("Glyph() after draw()");
    }

    Glyph(int k)
    {
        System.out.println("arg: " + k);
        System.out.println("Glyph() before draw()");
        draw();     // 子类重写后，new子类对象--调用子类重写后的方法
        System.out.println("Glyph() after draw()");
    }
}

class RoundGlyph extends Glyph
{
    private int radius = 1;    //  隐式调用 super() 时，radius为0
    //private static int radius = 1;        //  private static 优先于 构造函数

    RoundGlyph(int r)
    {     // 隐式调用 super()
        radius = r;
        System.out.println("RoundGlyph.RoundGlyph(), radius = " + radius);
    }

    RoundGlyph()
    {
        super(5);
        System.out.println("RoundGlyph.RoundGlyph(), radius = " + radius);
    }

    void draw()
    {
        System.out.println("RoundGlyph.draw(), radius = " + radius);
    }
}
```

output:

```java
arg: 5
Glyph() before draw()
RoundGlyph.draw(), radius = 0
Glyph() after draw()
RoundGlyph.RoundGlyph(), radius = 1
================================================
Glyph() before draw()
RoundGlyph.draw(), radius = 0
Glyph() after draw()
RoundGlyph.RoundGlyph(), radius = 5
```

## Abstract Class & Interface

### function interface

- A functional interface is any interface that contains *only one* **abstract** method**(necessary)** and *one or more* **default** and **static** methods**(optional)**.
  - support **functional programming** with **lambdas and streams**
  - **`@FunctionalInterface`**(加不加 `@FunctionalInterface` 对于接口是不是函数式接口没有影响，该注解只是提醒编译器去检查该接口是否仅包含一个抽象方法 )
- default and static methods have implementations
- 函数式接口里允许声明 `java.lang.Object` 里的 public 方法
- static method may be invoked on containing interface class only[^3]

```java
@FunctionalInterface
interface DoIt
{
    void h();

    default void f()
    {
        System.out.println("f()");
    }

    static void g()
    {
        System.out.println("g()");
    }

    @Override
    String toString();
}

class DoItImpl implements DoIt
{
    @Override
    public void h()
    {
        System.out.println("DoItImpl.h()");
    }
}

public class Test
{
    public static void main(String[] args)
    {
        new DoItImpl().f();
        new DoItImpl().h();
//        DoItImpl.g();   // compiler error
//        DoItExte.g();   // compiler error
        DoIt.g();
        DoIt doIt = () -> System.out.println("Test.h()");
        doIt.h();
    }
}
```

output:

```java
f()
DoItImpl.h()
g()
Test.h()
```

### Name collisions update

```java
interface OperateCar
{
    default public void startEngine(EncryptedKey key)
    {

    }
}

interface FlyCar
{
    default public void startEngine(EncryptedKey key)
    {

    }
}

class FlyingCar implements OperateCar, FlyCar
{
    @Override
    public void startEngine(EncryptedKey key)
    {
        FlyCar.super.startEngine(key);		// 注意super的用法
        OperateCar.super.startEngine(key);
    }
}
```

### Grouping constants

- any fields you put into an interface are automatically **public**, **static** and **final**
- creating groups of constant values before Java 5.  as an `enum` in Java 5+.

```java
public interface Months 
{
	int
		JANUARY = 1, FEBRUARY = 2, MARCH = 3,
		APRIL = 4, MAY = 5, JUNE = 6, JULY = 7,
		AUGUST = 8, SEPTEMBER = 9, OCTOBER = 10,
		NOVEMBER = 11, DECEMBER = 12;
}
```

## Generic

### `java.util.Comparator` *&* `java.lang.Comparable`

```java
public class Test
{
    public static void main(String[] args)
    {
        Person1[] person1s = new Person1[]{new Person1(2), new Person1(1), new Person1(3)};
        Person2[] person2s = new Person2[]{new Person2(2), new Person2(1), new Person2(3)};
        List<Person1> list1 = new ArrayList<>(Arrays.asList(person1s));
        Collections.sort(list1, new PersonComparator());
        System.out.println(list1);
        List<Person2> list2 = new ArrayList<>(Arrays.asList(person2s));
        Collections.sort(list2);
        System.out.println(list2);
    }
}

class Person1
{
    private int age;

    public Person1(int age)
    {
        this.age = age;
    }

    public int getAge()
    {
        return age;
    }

    @Override
    public String toString()
    {
        return "Person1{" +
                "age=" + age +
                '}';
    }
}

class Person2 implements Comparable<Person2>
{
    private int age;

    public Person2(int age)
    {
        this.age = age;
    }

    @Override
    public String toString()
    {
        return "Person2{" +
                "age=" + age +
                '}';
    }

    @Override
    public int compareTo(Person2 o)
    {
        return Integer.compare(age, o.age);
    }
}

class PersonComparator implements Comparator<Person1>
{
    @Override
    public int compare(Person1 o1, Person1 o2)
    {
        return Integer.compare(o1.getAge(), o2.getAge());
    }
}
```

### Row Types

```java
Box<Integer> intBox = new Box<>();
Box rawBox = new Box(); //raw type of the Box<T>
Box<String> stringBox = new Box<>();
Box rawBox = stringBox; // OK
Box<Integer> intBox = rawBox; // warning: unchecked conversion
rawBox.set(8); // warning: unchecked invocation to set(T)
```

[泛型的内部原理：类型擦除以及类型擦除带来的问题](https://blog.csdn.net/lonelyroamer/article/details/7868820)

- Java 中的泛型基本上都是在编译器这个层次来实现的。在生成的 Java 字节码中是不包含泛型中的类型信息的。使用泛型的时候加上的类型参数，会在编译器在编译的时候去掉。这个过程就称为类型擦除。 
- 原始类型（raw type）就是擦除去了泛型信息，最后在字节码中的类型变量的真正类型。无论何时定义一个泛型类型，相应的原始类型都会被自动地提供。类型变量被擦除（erased），并使用其限定类型（无限定的变量用 Object）替换。 
- java 编译器是通过先检查代码中泛型的类型，然后再进行类型擦除，再进行编译的。类型检查就是针对引用的，谁是一个引用，用这个引用调用泛型方法，就会对这个引用调用的方法进行类型检测，而无关它真正引用的对象。 
- 因为类型擦除的问题，所以所有的泛型类型变量最后都会被替换为原始类型。 但是在获取的时候不需要强制类型转换的原因：编译器在调用的地方自动强制转换。

```java
public class Test
{
    public static void main(String[] args)
    {
        Box<String> stringBox = new Box<>();
        Box box = stringBox;
        box.setT(new Tmp(1, 2));
        System.out.println(box.getT());
        Box<Integer> integerBox = box;
        integerBox.setT(2);
        System.out.println(integerBox.getT());
    }
}

class Tmp
{
    private int a;
    private int b;

    public Tmp(int a, int b)
    {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString()
    {
        return "Tmp{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}

class Box<T>
{
    private T t;

    public void setT(T t)
    {
        this.t = t;
    }

    public T getT()
    {
        return t;
    }
}
```

output:

```java
Tmp{a=1, b=2}
2
```

### Generic Methods

```java
class Util
{
    public static <K, V> boolean compare(Pair<K, V> p1, Pair<K, V> p2)
    {
        return p1.getKey().equals(p2.getKey()) && p1.getValue().equals(p2.getValue());
    }
}

public class Test
{
    public static void main(String[] args)
    {
        Pair<Integer, String> p1 = new Pair<>(1, "apple");
        Pair<Integer, String> p2 = new Pair<>(1, "pear");
//        boolean same = Util.<Integer, String>compare(p1, p2);
        boolean same = Util.compare(p1, p2);
        System.out.println(same);
    }
}
```

## Inner classes

### 为什么要使用内部类？

> 使用内部类最吸引人的原因是：每个内部类都能独立地继承一个（接口的）实现，所以无论外围类是否已经继承了某个（接口的）实现，对于内部类都没有影响。 
>
> 接口只是解决了部分问题，而内部类使得多重继承的解决方案变得更加完整。
>
> 1. 内部类可以用多个实例，每个实例都有自己的状态信息，并且与其他外围对象的信息相互独立。 
> 2. 在单个外围类中，可以让多个内部类以不同的方式实现同一个接口，或者继承同一个类。 
> 3. 创建内部类对象的时刻并不依赖于外围类对象的创建。 
> 4. 内部类并没有令人迷惑的 “is-a” 关系，他就是一个独立的实体。 
> 5. 内部类提供了更好的封装，除了该外围类，其他类都不能访问。 

### 内部类基础

* To make an object of the inner class anywhere except from within a **non-static** method of the outer class, you must specify the type of that object as `OuterClassName.InnerClassName`
* .this & .new

### 成员内部类

- 成员内部类也是最普通的内部类，它是外围类的一个成员，所以他是可以无限制的访问外围类的所有成员属性和方法，尽管是 private 的，但是外围类要访问内部类的成员属性和方法则需要通过内部类实例来访问。

- 成员内部类中不能存在任何 static 的**变量**和**方法**，但可以有静态**常量**。[^4]

- 成员内部类是依附于外围类的，所以只有先**创建**了外部类才能够**创建**内部类。 

- 内部类**加载**是在外部类对象**创建**之前完成的。

  ```java
  public class OuterClass
  {
      static int k = printI();
  
      static int printI()
      {
          System.out.println("Inner Class is loading before creating OuterClass instance");
          return InnerClass.i;
      }
  
      OuterClass()
      {
          System.out.println("OuterClass constructor");
      }
  
      class InnerClass
      {
          private static final int i = 1;
      }
  
      public static void main(String[] args)
      {
          OuterClass oc;
      }
  }
  ```

  output:

  ```java
  Inner Class is loading before creating OuterClass instance
  ```

  

- 

## Collections of Objects

### 线程安全的类

- `Vector`：比`ArrayList`多了同步化机制
- `HashTable`：比`HashMap`多了同步化机制
- `ConcurrentHashMap`：高效但线程安全的集合
- `Stack`：继承于`Vector`
- `StringBuffer`：比`StringBuilder`多了同步化机制

## I/O System

### 分类[^1]

#### 分类一：按操作方式（类结构）

##### 字节流和字符流

字节流：8位，可以读取任何类型数据 (`InputStream`, `OutputStream`)

字符流：16位，只能读取字符类型 (`Reader`, `Writer`)

##### 输出流和输入流

输出流：从内存读出到文件

输入流：从文件读入到内存

##### 节点流和处理流

节点流：直接与数据源相连 (`File`, `Piped`, `Char`, `Byte`)

处理流：**套接在节点流上**

##### 分类结构图

![img](https://pic3.zhimg.com/80/v2-6a68758ec960e05fd07ae9438ea1b832_hd.jpg)

##### 字符流与字节流转换

```java
Writer out = new OutputStreamWriter(new FileOutputStream(file));
out.write(string);
out.close();
```

```java
Reader read = new InputStreamReader(new FileInputStream(file));
char[] b = new char[100];
int len = read.read(b);
read.close();
```

#### 分类二：按操作对象

##### 分类结构图

![img](https://pic3.zhimg.com/80/v2-1a7a2ae7ed9a13910aecebbed9a00e72_hd.jpg)

### File and Scanner

```java
File inputFile = new File("input.txt");
try
{
    Scanner in = new Scanner(inputFile);	// will throw FileNotFoundException
    while (in.hasNextDouble())
    {
        try
        {
            double value = in.nextDouble();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
catch (FileNotFoundException e)
{
    e.printStackTrace();
}
```

## JDBC

### [JDBC驱动的四种类型](https://blog.csdn.net/qq_35448976/article/details/75219243)

### Typical JDBC  Programming Procedure

- Load the database **driver**

  ```java
  private static final String DRIVER = "com.mysql.jdbc.Driver";
  
  try
  {
      Class.forName(DRIVER);
  }
  catch (ClassNotFoundException e)
  {
      e.printStackTrace();
  }
  ```

- Obtain a **connection**

  ```java
  private static final String URL = "jdbc:mysql://localhost:3306/databaseName";
  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";
  
  Connection conn = null;
  
  try
  {
      conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
  }
  catch (SQLException e)
  {
      e.printStackTrace();
  }
  ```

- Create and execute **statements **(SQL queries) *&* Getting **result sets** (tables) from tables

  ```java
  String sql = ...;
  ResultSet rs = null;
  try
  {
      Statement statement = conn.createStatement();
      statement.executeUpdate(sql);	// update/create/alter/drop a table
      rs = statement.executeQuery(sql);	// SQL SELECT statements
  }
  catch (SQLException e)
  {
      e.printStackTrace();
  }
  ```

- navigate through the **result sets**

  1. **next**
  2. getter methods

  ```java
  if (rs.next())
  {
      String s = rs.getString("firstName");
      String s = rs.getString(2);
      int math = rs.getInt("authorID");
      int math = rs.getInt(1);
  }
  ```

- **Close** the connection

  ```java
  try
  {
      conn.close();
  }
  catch (SQLException e)
  {
      e.printStackTrace();
  }
  ```

## Network Programming -- Socket

### URI

A **URI** has two specializations known as **URL** and **URN**.

[difference](https://stackoverflow.com/questions/4913343/what-is-the-difference-between-uri-url-and-urn)

### TCP

- package:

  ```java
  import java.net.ServerSocket;
  import java.net.Socket;
  ```

- fields:

  ```java
  int port = 8080;
  String host = "127.0.0.1";
  ```

- server:

  ```java
  ServerSocket ss = new ServerSocket(port);
  Socket socket = ss.accept();
  ```

- client:

  ```java
  Socket socket = new Socket(host, port);
  ```

- communicate:

  ```java
  DataInputStream dis = new DataInputStream(socket.getInputStream());
  DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
  ```

- close socket:

  ```java
  socket.close();
  ```

### UDP

- Create a UDP socket

  ```java
  // DatagramSocket();
  // DatagramSocket(int localPort);
  // DatagramSocket(int localPort, InetAddress localAddress);
  DatagramSocket datagramSocket = new DatagramSocket(port);
  ```

- Receive/send data

  ```java
  // void receive(DatagramPacket packet);
  // void send(DatagramPacket packet);
  String message = "message";
  byte[] bytes = message.getBytes();
  DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
  datagramSocket.receive(datagramPacket);
  datagramSocket.send(datagramPacket);
  ```

- Close a UDP socket

  ```java
  datagramSocket.close();
  ```

## Other

### The finally Block[^2]

> *The finally block always executes when the try block exits. This ensures that the finally block is executed even if an unexpected exception occurs. But finally is useful for more than just exception handling — it allows the programmer to avoid having cleanup code accidentally bypassed by a return, continue, or break. Putting cleanup code in a finally block is always a good practice, even when no exceptions are anticipated.*
>
> *Note:* *If the JVM exits while the try or catch code is being executed, then the finally block may not execute. Likewise, if the thread executing the try or catch code is interrupted or killed, the finally block may not execute even though the application as a whole continues.*

[^1]: [Java IO，硬骨头也能变软](https://zhuanlan.zhihu.com/p/28286559 )
[^2]: [关于 Java 中 finally 语句块的深度辨析](https://www.ibm.com/developerworks/cn/java/j-lo-finally/index.html)
[^3]: [In java 8, why cannot call the interface static method that the current class is implementing](https://stackoverflow.com/questions/29383083/in-java-8-why-cannot-call-the-interface-static-method-that-the-current-class-is)
[^4]: [Why can't we have static method in a (non-static) inner class?](https://stackoverflow.com/questions/975134/why-cant-we-have-static-method-in-a-non-static-inner-class)