/**
 * @param employee
 * @return nothing
 * @throws no
 * @author Ed
 * @version 1
 * @see https://www.bilibili.com/video/BV1334y1R7sz
 * @link https://www.bilibili.com/video/BV1Px411p7iE?p=13&spm_id_from=pageDriver
 */
//& �����������ļ���������������

import java.io.*;
import java.text.NumberFormat;
import java.util.*;
import java.time.*;
import static java.lang.System.*;

@SuppressWarnings("unused")

public class Chap4_OOP {
    // @employee��
    public static /* ����Ϊ��̬��main����ʹ�ã���Ϊmain�Ǿ�̬�� */class employee {
        // @����
        private final String name;
        // & final ������ʱ�����ʼ������ �Ҳ���ͨ������ģ�
        // !��private final Stringbuilder sb�����ñ�����append����
        private static int number;
        // & ��̬�ֶ��ֽ����ֶΣ�����employee���󶼹���ͬһ��
        // - System.out��System����
        // - public static final PrintStream=out...
        // ? ������final������setOut�޸�System.out����ԭ������������������ʵ��
        private LocalDate day;

        private static int nextId;
        // & δ��ʼ�������Զ���ʼ��Ϊ��ֵ0������false����������null���������ֲ��������Բ���ʼ��

        // @���캯��
        // *��ʼ���飬�����ĸ����캯����������
        {
            number = 0;
            day = LocalDate.of(1999, 2, 12);// ������const

        }
        static {// & ��̬��ʼ����
            // ����JDK6֮ǰ��û��main������µ�����Ҳ��������
            var generator = new Random();
            nextId = generator.nextInt(20);// & ��ʾ0-19�����
        }

        public employee(String n, int number, LocalDate day) {
            // ! ����дString name = n;��������name�ڱε�
            name = Objects.requireNonNull(n, "I don't need Null!");
            // & nullʱ�Զ��屨��
            employee.number += number;
            this.day = day;
        }// & ���˹������󲻻��Զ�����Ĭ�Ϲ���employee()
         // -���ڶ���û���Ӷ������Բ���Ҫ��ʼ����

        public employee(String n) {
            this(n, 1, LocalDate.now());
            // & ����employee(String n, int number, int day)
        }

        // @����
        // ! java���к��������ڶ���
        public String getName() {
            return name;
        }

        public static int getNumber() {
            return number;
        }// & ��̬����������Ҫ����ʵ����
         // & math.pow���Ǿ�̬����

        public void setDay(LocalDate day) {
            this.day = day;
        }

        // @ÿ���඼�����������Լ���main������ѡ�������ĸ�
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
        // @��������
        // & �Զ���������û��~
        // -close��ǰ����
        // -Runtime.addShutdownHook�ȵ�������˳��ر�
        // -finalize�ѷ���
    }

    // @main
    public static void main(String[] args) {
        // ? main��һ����̬��������һ����������ʷǾ�̬���������ʵ�����룬��̬���������ڴ���

        // @���Ĺ�ϵ
        // *is-a �̳�
        // *has-a �ۺ�
        // A����һЩB����
        // *uses-a ����
        // A���Բ���B����

        // @������
        // ? Java��Ķ�������൱��C++��Ķ���ָ��
        // -java�︴�Ʊ�����clone
        Date d1 = new Date();
        System.out.println(d1.toString());// System.outΪfinal static
        d1 = null;// & ���԰Ѷ����������null
        // -���������� �ɸ��Ķ���
        // -���������� ֻ���ʶ���

        var harry = new employee("ww");
        // & Java��ı�����new������C++��employ a("aaa")
        // *��̬����
        System.out.println(employee.getNumber());
        // - C++��employee::getNumber()
        // & ��̬�������������ã���ò�Ҫ��ʵ������

        // *��̬��������
        // & ��������һ���Լ��Ķ��󣬿��Ա���ÿ�ζ�new�����ұ��⹹�캯����������ͬ������
        NumberFormat currencyFmt = NumberFormat.getCurrencyInstance();
        NumberFormat percentFmt = NumberFormat.getPercentInstance();
        // & ����ľ�̬��������ʵ�ʷ���NumberFormat������DecimalFormat,���캯���޷�������һ��
        double x = 0.3;
        System.out.println(currencyFmt.format(x));// ?0.30
        System.out.println(percentFmt.format(x));// 30%

        // ! employee a = null, String s = a.toString() �����ش�,��������Խ��
        // var nonull = new employee(null);
        // ! Exception in thread "main" java.lang.NullPointerException: I don't need
        // Null!

        // ! java��ֵ����,��˲��ܽ�����C++��&��˿���
        /*
         * ��ע��
         */

    }
}

// @dog��
class Dog implements Cloneable {
    private String name;
    private int age;
    final int code; // & �൱��const

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

    // �����������дһ�¾��У�ʲô����д
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