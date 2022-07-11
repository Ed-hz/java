
import java.nio.charset.StandardCharsets;
import java.io.*;
//import java.nio.*;
import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.*;// 不是lang包时一定要导入

@SuppressWarnings("unused")

public class chap3_io {
    public static void main(String[] args) throws IOException {
        // @scanner
        Scanner in = new Scanner(System.in);// &构造Scanner对象
        System.out.print("Hello:");
        String name = in.nextLine();// & 读一行
        /*
         * String nextName = in.next();
         * int num = in.nextInt();
         * double dou = in.nextDouble();
         */

        // @console
        Console cons = System.console();
        String username = cons.readLine("What's name:");
        char[] password = cons.readPassword("Password :");
        // & console只能读一整行
        // &读密码而且不显示在屏幕上
        in.close();// & 关闭Scanner对象,关闭后Console也不能用

        // @格式化输出
        // %h散列码
        String mess = String.format("His passport is %s", String.valueOf(password)/* 或Arrays.toString(password) */);
        System.out.printf("Say %s to %s\n", name, username);
        System.out.println(mess);
        System.out.printf("%1$s %2$tB %<te %<tY", "due date", new Date());
        // & 1$表示索引，1$就是"due date"，2$就是new Date()，<表示和前一项一样
        System.out.println();

        // @文件输入
        String dir = System.getProperty("user.dir");// F:\c_filed\Java
        System.out.println(dir);
        // Scanner in2 = new Scanner(Path.of("F:\\c_filed\\java\\a1_type.java"),
        // StandardCharsets.UTF_8);
        // PrintWriter out = new PrintWriter("F:\\c_filed\\java\\a1_type.java",
        // StandardCharsets.UTF_8);
        // out.close();
    }
}
