
import java.nio.charset.StandardCharsets;
import java.io.*;
//import java.nio.*;
import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.*;// ����lang��ʱһ��Ҫ����

@SuppressWarnings("unused")

public class chap3_io {
    public static void main(String[] args) throws IOException {
        // @scanner
        Scanner in = new Scanner(System.in);// &����Scanner����
        System.out.print("Hello:");
        String name = in.nextLine();// & ��һ��
        /*
         * String nextName = in.next();
         * int num = in.nextInt();
         * double dou = in.nextDouble();
         */

        // @console
        Console cons = System.console();
        String username = cons.readLine("What's name:");
        char[] password = cons.readPassword("Password :");
        // & consoleֻ�ܶ�һ����
        // &��������Ҳ���ʾ����Ļ��
        in.close();// & �ر�Scanner����,�رպ�ConsoleҲ������

        // @��ʽ�����
        // %hɢ����
        String mess = String.format("His passport is %s", String.valueOf(password)/* ��Arrays.toString(password) */);
        System.out.printf("Say %s to %s\n", name, username);
        System.out.println(mess);
        System.out.printf("%1$s %2$tB %<te %<tY", "due date", new Date());
        // & 1$��ʾ������1$����"due date"��2$����new Date()��<��ʾ��ǰһ��һ��
        System.out.println();

        // @�ļ�����
        String dir = System.getProperty("user.dir");// F:\c_filed\Java
        System.out.println(dir);
        // Scanner in2 = new Scanner(Path.of("F:\\c_filed\\java\\a1_type.java"),
        // StandardCharsets.UTF_8);
        // PrintWriter out = new PrintWriter("F:\\c_filed\\java\\a1_type.java",
        // StandardCharsets.UTF_8);
        // out.close();
    }
}
