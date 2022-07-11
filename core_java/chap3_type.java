
import static java.lang.Math.*;//& 这样就可以不用写Math.了
import java.math.*;
import java.util.*;

//import static java.lang.String.*;
//import static java.lang.StringBuilder.*;
import java.util.*;
import java.sql.*;
import static java.lang.System.*;

//package com.horstmann.corejava;
@SuppressWarnings("unused")

public class chap3_type {
    public static final/* static final类常量，在main外部，可以多个类使用 */ double INCH = 3.2;

    enum size {
        SMALL, LARGE
    }

    public/* 访问修饰符，main必须是public */ static /* strictfp 严格浮点运算 可以用机器上最大字长计算 */void main/* main必须是静态的，有一个shell类 */(
            String\u005B\u005D/* [] */ args) {
        // - System.out.print()不在输出后换行
        int n;
        {
            int k;
            // int n;
        } // ! 不能在嵌套块声明同名变量

        // @ 8种类型 short int long byte float double char
        // @数字类型
        // - BigDecimal类加减没有舍入误差
        // Double.POSITIVE_INFINITY Double.NEGATIVE_INFINITY Double.NaN
        // ? java没有无符号数
        byte by = -4;
        int IntB = 0b0011_1101_0110;// & 二进制 可以加_
        float f = 3.16F;// & 不加F默认是double
        System.out.println(Byte.toUnsignedInt(by));// 252
        System.out.println(IntB);// 982
        System.out.println(f);// 3.16

        // @char类型和unicode
        // & char类型描述UTF-16里的一个码元
        // & unicode转义会在解析代码前处理，所以"\u0022+\u0022"会转为""+""
        // !注释里的转义也很麻烦 \\u000A会转为换行,C:\\users会无效
        // *bool Java里“if(x=0)”会报错

        // @变量
        // ! 但不要在代码里用$字符，只能在编译器内自己用
        var ismoney = Character.isJavaIdentifierStart('$');// & 局部变量可以用var
        System.out.println(ismoney);// true
        // - 尽量初始化
        // - java不区分声明和定义
        /*
         * ! 只能放在主函数外
         * enum size {
         * SMALL, LARGE
         * }
         */
        size s = size.SMALL;
        System.out.println(s);// SMALL

        // @Math
        // - Math.multiplyExact(1000000000, 3);修复普通乘法不会报错
        double x = Math.sqrt(8);// & 静态方法
        double time = floorMod(-99, -12);// & 恒为后面那个数同号，修正%可能不同号问题
        System.out.println(x);// 2.8284271247461903
        System.out.println(time);// -3.0
        // Math.sin Math.cos Math.tan Math.atan Math.atan2 Math.E Math.PI Math.log
        // Math.exp Math.log10

        // @类型转换
        /*
         * 二元operator
         * 一个double就转double
         * 一个float就转float
         * 一个long就转long
         * 一个int就转int
         */
        int nx = (int) Math.round(x);
        System.out.println(nx);// 3
        // & 因为round默认输出long
        // ! 不要强制转换bool 必要时用 ?1:0 代替

        // @位运算符
        final float/* 常量 */ vaca = ((int) pow(2, 3) & (1 << 35/* 移位的35要mod32变成3，long就要mod64 */) >>> 2);
        System.out.println(vaca + INCH + "\n");// 3.2
        // & >>用符号，>>>用 0填充高位，没有<<<,c++不保证这一点
        // ? &|在逻辑上和&&||相似，但没有短路

        // @String
        String greet = "Hello";
        String greetsub = (greet.substring(0, 3) + 13).repeat(3);
        System.out.println(greetsub);// Hel0Hel0Hel0
        String all = String.join(",", "H", "e", "l", "l", "o");
        System.out.println(all);// H,e,l,l,o
        // - String不可变，但复制之后可以字符串共享
        // ? String类似于C++里的char*而非char[]
        // & null不为空串""，也没有长度 if(str!=null&&str.length()!=0)判断两个都不是

        // @码点
        // & java 以码点解决大数的问题
        int codenum = greet.codePointCount(0, greet.length());
        char first = greet.charAt(0);
        int index = greet.offsetByCodePoints(0, 3);
        int cp = greet.codePointAt(index);// & 获得第3个码点
        System.out.println(codenum);// 5
        System.out.println(first);// H
        System.out.println(cp);// 108

        int codePoints[] = greet.codePoints().toArray();// 码点数组
        String str = new String(codePoints, 0, codePoints.length);// 再转回字符串
        System.out.println(str);
        if (greetsub.equals("gg")) {
        }
        // greetsub.compareTo("gg")==0一样
        // ==只能判断刚开始字符是否相等，+，substring会有bug
        // equalsIngoreCase不考虑大小写
        System.out.println();

        // @StringBuilder
        StringBuilder builder = new StringBuilder();
        builder.append(greet);
        builder.append('!');
        String complete = builder.toString();
        System.out.println(complete);

        // @BigInteger
        BigInteger verybig = new BigInteger("2345678656467876578976578");
        BigInteger verybig2 = new BigInteger("667896899123");
        BigInteger verybig3 = new BigInteger("65757892343879045");
        BigInteger verybigadd = verybig3.multiply(verybig.add(verybig2.add(BigInteger.valueOf(3))));
        System.out.println(verybigadd);

        // @数组
        int[] nums;// java更喜欢这种风格
        nums = new int[] { 1, 2, 3 };// 匿名数组
        // -允许长度0的数组，与null不同

        // * for each
        int[] nums3 = { 1, 2, 3, 5, 2 };
        for (int i : nums) {
            System.out.println(i);
        }
        // * copy
        int[] nums4 = nums3;// & 指针copy
        int[] nums5 = Arrays.copyOf(nums3, 2 * nums3.length);// & 另一个Array
        // - Java里的int[] a=new int[1000]相当于C++里的int* a=new int[1000]，因为没有指针，不能通过+1获得下个元素

        // *sort
        Arrays.sort(nums3);

        // *二维数组
        int[][] n2um = { { 2, 3, 5 }, { 4, 2, 12 }, { 123, 444 } };
        System.out.println(Arrays.deepToString(n2um));
        // - Java里的int[][] a=new int[100][10]相当于C++里的int** a=new int*[100],然后a[i]=new
        // int[10]

        // @arg[]
        if (args.length == 0 || args[0].equals("-h"))
            System.out.print("Hello, ");
        else if (args[0].equals("-g"))
            System.out.print("Goodbye, ");
        // print the other command-line arguments
        for (int i = 1; i < args.length; i++)
            System.out.print(" " + args[i]);
        System.out.println("! ");
        // Hello, !
        // & 程序名不在args里
        // 用java message -h cruel world
        // args[0] = "-h", args[1] = "cruel", args[2] = "world"

        // @循环
        // switch 可以char
        int i = 1, j = 1;
        read_data: // label
        while (i > 0) {
            System.out.println("go\n");
            while (j > 0) { // java 可以不写括号
                if (j == 3)
                    break read_data;
                j += 1;
            }
        }
        System.out.println("ungo\n");

        // @静态导入
        // ! Date today; 静态导入的ambiguous
        var today = new java.util.Date(); // 自动识别
        var forday = new java.sql.Date(22);
        out.println("good,go!" + today + forday);
        exit(0);// & 静态导入不用加前缀
        // - 不同于C++，Java可以访问package内部，且可以不import显式调用java.util.Date(),类似于namespace

    }

}