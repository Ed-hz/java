/**
 * @param employee
 * @return nothing
 * @throws no
 * @author Ed
 * @version 1
 * @see https://www.bilibili.com/video/BV1334y1R7sz
 * @link https://www.bilibili.com/video/BV1Px411p7iE?p=13&spm_id_from=pageDriver
 */
//& 编译器处理文件，解释器加载类

import java.io.*;
import java.text.NumberFormat;
import java.util.*;
import java.time.*;
import static java.lang.System.*;

@SuppressWarnings("unused")

public class Chap4_OOP {
    // @employee类
    public static /* 必须为静态类main才能使用，因为main是静态的 */class employee {
        // @变量
        private final String name;
        // & final 类生成时必须初始化的量 且不能通过类更改！
        // !但private final Stringbuilder sb可以用本来的append更改
        private static int number;
        // & 静态字段又叫类字段，所有employee对象都共享同一个
        // - System.out是System类里
        // - public static final PrintStream=out...
        // ? 由于是final可以用setOut修改System.out，是原生方法，用其他语言实现
        private LocalDate day;

        private static int nextId;
        // & 未初始化可以自动初始化为数值0，布尔false，对象引用null，但方法局部变量可以不初始化

        // @构造函数
        // *初始化块，调用哪个构造函数都会运行
        {
            number = 0;
            day = LocalDate.of(1999, 2, 12);// 更改器const

        }
        static {// & 静态初始化块
            // 甚至JDK6之前在没用main的情况下调用类也可以运行
            var generator = new Random();
            nextId = generator.nextInt(20);// & 表示0-19随机数
        }

        public employee(String n, int number, LocalDate day) {
            // ! 不能写String name = n;会把外面的name遮蔽掉
            name = Objects.requireNonNull(n, "I don't need Null!");
            // & null时自定义报错
            employee.number += number;
            this.day = day;
        }// & 有了构造器后不会自动生成默认构造employee()
         // -由于对象没用子对象，所以不需要初始化列

        public employee(String n) {
            this(n, 1, LocalDate.now());
            // & 调用employee(String n, int number, int day)
        }

        // @方法
        // ! java所有函数在类内定义
        public String getName() {
            return name;
        }

        public static int getNumber() {
            return number;
        }// & 静态方法，不需要对象实例化
         // & math.pow就是静态方法

        public void setDay(LocalDate day) {
            this.day = day;
        }

        // @每个类都可以有属于自己的main，可以选择运行哪个
        // * Chap4_OOP$employee
        public static void main(String[] args) {
            // fill the staff array with three Employee objects
            employee[] staff = new employee[3];

            staff[0] = new employee("Carl Cracker");
            staff[1] = new employee("Harry Hacker");
            staff[2] = new employee("Tony Tester");

            // print out information about all Employee objects
            for (employee e : staff)
                System.out.println("name=" + e.getName() + ",Number=" + employee.getNumber());
        }
        // @垃圾回收
        // & 自动垃圾回收没有~
        // -close提前回收
        // -Runtime.addShutdownHook等到虚拟机退出关闭
        // -finalize已放弃
    }

    // @main
    public static void main(String[] args) {
        // ? main是一个静态方法，在一个大类里访问非静态方法必须从实例进入，静态方法已在内存中

        // @类间的关系
        // *is-a 继承
        // *has-a 聚合
        // A包括一些B对象
        // *uses-a 依赖
        // A可以操纵B对象

        // @对象构造
        // ? Java里的对象变量相当于C++里的对象指针
        // -java里复制必须用clone
        Date d1 = new Date();
        System.out.println(d1.toString());// System.out为final static
        d1 = null;// & 可以把对象变量声明null
        // -更改器方法 可更改对象
        // -访问器方法 只访问对象

        var harry = new employee("ww");
        // & Java里的必须用new，不能C++里employ a("aaa")
        // *静态方法
        System.out.println(employee.getNumber());
        // - C++里employee::getNumber()
        // & 静态方法用类名引用，最好不要用实例调用

        // *静态工厂方法
        // & 方法返回一个自己的对象，可以避免每次都new，而且避免构造函数和类名相同的问题
        NumberFormat currencyFmt = NumberFormat.getCurrencyInstance();
        NumberFormat percentFmt = NumberFormat.getPercentInstance();
        // & 这里的静态工厂方法实际返回NumberFormat的子类DecimalFormat,构造函数无法做到这一点
        double x = 0.3;
        System.out.println(currencyFmt.format(x));// ?0.30
        System.out.println(percentFmt.format(x));// 30%

        // ! employee a = null, String s = a.toString() 很严重错,类似索引越界
        // var nonull = new employee(null);
        // ! Exception in thread "main" java.lang.NullPointerException: I don't need
        // Null!

        // ! java是值调用,因此不能交换，C++有&因此可以
        /*
         * 类注释
         */

    }
}

// @dog类
class Dog implements Cloneable {
    private String name;
    private int age;
    final int code; // & 相当于const

    public Dog(String name, int age, final int code) {
        super();
        this.name = name;
        this.age = age;
        this.code = code;
    }

    @Override
    public String toString() {
        return "Dog [name=" + name + ", age=" + age + "]";
    }

    // 把这个方法重写一下就行，什么都不写
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // bitwise
    }

    public Dog getNextDog() {
        Dog newdog = new Dog(this.name, this.age + 1, this.code * 3);
        return newdog;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject)
            return true;
        if (otherObject == null)
            return false;
        if (getClass() != otherObject.getClass())
            return false;

        var other = (Dog) otherObject;
        return Objects.equals(name, other.name)
                && age == other.age && code == other.code;
    }

    public int hashCode() {
        return 7 * Objects.hashCode(name) + 11 * Integer.hashCode(age) + 13 * Integer.hashCode(code);
    }
}