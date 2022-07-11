
import static java.lang.Math.*;//& �����Ϳ��Բ���дMath.��
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
    public static final/* static final�ೣ������main�ⲿ�����Զ����ʹ�� */ double INCH = 3.2;

    enum size {
        SMALL, LARGE
    }

    public/* �������η���main������public */ static /* strictfp �ϸ񸡵����� �����û���������ֳ����� */void main/* main�����Ǿ�̬�ģ���һ��shell�� */(
            String\u005B\u005D/* [] */ args) {
        // - System.out.print()�����������
        int n;
        {
            int k;
            // int n;
        } // ! ������Ƕ�׿�����ͬ������

        // @ 8������ short int long byte float double char
        // @��������
        // - BigDecimal��Ӽ�û���������
        // Double.POSITIVE_INFINITY Double.NEGATIVE_INFINITY Double.NaN
        // ? javaû���޷�����
        byte by = -4;
        int IntB = 0b0011_1101_0110;// & ������ ���Լ�_
        float f = 3.16F;// & ����FĬ����double
        System.out.println(Byte.toUnsignedInt(by));// 252
        System.out.println(IntB);// 982
        System.out.println(f);// 3.16

        // @char���ͺ�unicode
        // & char��������UTF-16���һ����Ԫ
        // & unicodeת����ڽ�������ǰ��������"\u0022+\u0022"��תΪ""+""
        // !ע�����ת��Ҳ���鷳 \\u000A��תΪ����,C:\\users����Ч
        // *bool Java�if(x=0)���ᱨ��

        // @����
        // ! ����Ҫ�ڴ�������$�ַ���ֻ���ڱ��������Լ���
        var ismoney = Character.isJavaIdentifierStart('$');// & �ֲ�����������var
        System.out.println(ismoney);// true
        // - ������ʼ��
        // - java�����������Ͷ���
        /*
         * ! ֻ�ܷ�����������
         * enum size {
         * SMALL, LARGE
         * }
         */
        size s = size.SMALL;
        System.out.println(s);// SMALL

        // @Math
        // - Math.multiplyExact(1000000000, 3);�޸���ͨ�˷����ᱨ��
        double x = Math.sqrt(8);// & ��̬����
        double time = floorMod(-99, -12);// & ��Ϊ�����Ǹ���ͬ�ţ�����%���ܲ�ͬ������
        System.out.println(x);// 2.8284271247461903
        System.out.println(time);// -3.0
        // Math.sin Math.cos Math.tan Math.atan Math.atan2 Math.E Math.PI Math.log
        // Math.exp Math.log10

        // @����ת��
        /*
         * ��Ԫoperator
         * һ��double��תdouble
         * һ��float��תfloat
         * һ��long��תlong
         * һ��int��תint
         */
        int nx = (int) Math.round(x);
        System.out.println(nx);// 3
        // & ��ΪroundĬ�����long
        // ! ��Ҫǿ��ת��bool ��Ҫʱ�� ?1:0 ����

        // @λ�����
        final float/* ���� */ vaca = ((int) pow(2, 3) & (1 << 35/* ��λ��35Ҫmod32���3��long��Ҫmod64 */) >>> 2);
        System.out.println(vaca + INCH + "\n");// 3.2
        // & >>�÷��ţ�>>>�� 0����λ��û��<<<,c++����֤��һ��
        // ? &|���߼��Ϻ�&&||���ƣ���û�ж�·

        // @String
        String greet = "Hello";
        String greetsub = (greet.substring(0, 3) + 13).repeat(3);
        System.out.println(greetsub);// Hel0Hel0Hel0
        String all = String.join(",", "H", "e", "l", "l", "o");
        System.out.println(all);// H,e,l,l,o
        // - String���ɱ䣬������֮������ַ�������
        // ? String������C++���char*����char[]
        // & null��Ϊ�մ�""��Ҳû�г��� if(str!=null&&str.length()!=0)�ж�����������

        // @���
        // & java �����������������
        int codenum = greet.codePointCount(0, greet.length());
        char first = greet.charAt(0);
        int index = greet.offsetByCodePoints(0, 3);
        int cp = greet.codePointAt(index);// & ��õ�3�����
        System.out.println(codenum);// 5
        System.out.println(first);// H
        System.out.println(cp);// 108

        int codePoints[] = greet.codePoints().toArray();// �������
        String str = new String(codePoints, 0, codePoints.length);// ��ת���ַ���
        System.out.println(str);
        if (greetsub.equals("gg")) {
        }
        // greetsub.compareTo("gg")==0һ��
        // ==ֻ���жϸտ�ʼ�ַ��Ƿ���ȣ�+��substring����bug
        // equalsIngoreCase�����Ǵ�Сд
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

        // @����
        int[] nums;// java��ϲ�����ַ��
        nums = new int[] { 1, 2, 3 };// ��������
        // -������0�����飬��null��ͬ

        // * for each
        int[] nums3 = { 1, 2, 3, 5, 2 };
        for (int i : nums) {
            System.out.println(i);
        }
        // * copy
        int[] nums4 = nums3;// & ָ��copy
        int[] nums5 = Arrays.copyOf(nums3, 2 * nums3.length);// & ��һ��Array
        // - Java���int[] a=new int[1000]�൱��C++���int* a=new int[1000]����Ϊû��ָ�룬����ͨ��+1����¸�Ԫ��

        // *sort
        Arrays.sort(nums3);

        // *��ά����
        int[][] n2um = { { 2, 3, 5 }, { 4, 2, 12 }, { 123, 444 } };
        System.out.println(Arrays.deepToString(n2um));
        // - Java���int[][] a=new int[100][10]�൱��C++���int** a=new int*[100],Ȼ��a[i]=new
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
        // & ����������args��
        // ��java message -h cruel world
        // args[0] = "-h", args[1] = "cruel", args[2] = "world"

        // @ѭ��
        // switch ����char
        int i = 1, j = 1;
        read_data: // label
        while (i > 0) {
            System.out.println("go\n");
            while (j > 0) { // java ���Բ�д����
                if (j == 3)
                    break read_data;
                j += 1;
            }
        }
        System.out.println("ungo\n");

        // @��̬����
        // ! Date today; ��̬�����ambiguous
        var today = new java.util.Date(); // �Զ�ʶ��
        var forday = new java.sql.Date(22);
        out.println("good,go!" + today + forday);
        exit(0);// & ��̬���벻�ü�ǰ׺
        // - ��ͬ��C++��Java���Է���package�ڲ����ҿ��Բ�import��ʽ����java.util.Date(),������namespace

    }

}