
import java.util.*;//不会import另一个里面已经import的

@SuppressWarnings("unused")
public class chap5_inher {
    public static void main(String[] args) {
        // - Java没有多重继承
        // @多态
        Dog dog = new Bedog("Big", 12, 12678, 12);
        Bedog bedog = new Bedog("Big", 12, 12678, 12);

        bedog.setBonus(12);
        // ! dog.setBonus(12);
        // & 虽然实际也是但不能用，只能重载已有的函数

        // @类的包括
        Bedog[] abedog = new Bedog[10];
        Dog[] adog = abedog;
        adog[0] = dog;// & 可以
        // adog[0] = new Dog("Big", 12, 12678); 书上可以，实测报错
        // ! 由于abedog和adog是指针的克隆，所以居然Bedog[]里有Dog元素
        // ! abedog[0].setBonus(12);溢出
        abedog[0].setBonus(12);// & 可以

        // @类型转换
        if (adog[1] instanceof Bedog) {
            Bedog dd = (Bedog) adog[1];
        }
        // & 强制类型转换类似于dynamic_cast,转换失败不会变null而是异常

        // - private,static,final方法，构造器为静态绑定，多态为动态绑定
        // & 动态绑定查找method table
        // & 覆盖时权限必须更public，如果基类public，子类不能private

        // * 方法final不能再重载，字段final相当于const，类final只final方法，不final字段
        // & String就是类final

        // @Object
        // & 所有类的超类
        Object obj = bedog;
        obj = abedog;
        obj = 3;
        obj = "abedog";
        obj = new int[10];

        // @hash
        String str = "I'm Ed";
        int hash = 0;
        for (int i = 0; i < str.length(); i++)
            hash = 31 * hash + str.charAt(i);
        System.out.println(hash);// & 和实际的散列一样
        System.out.println(str.hashCode());

        var s = "Ok";
        var t = new String("Ok");
        var sb = new StringBuilder(s);
        var tb = new StringBuilder(t);
        System.out.println(s.hashCode() + " " + sb.hashCode());
        System.out.println(t.hashCode() + " " + tb.hashCode());
        // & s和t散列一样，但StringBuilder没有内置的散列，所以以位置

        System.out.println(dog.hashCode());
        System.out.println();

        // @toString
        System.out.println(bedog.toString());
        System.out.println(dog.equals(bedog));
        System.out.println(System.out);// java.io.PrintStream@28a418fc
        System.out.println(bedog);// & 会调用toString
        System.out.println();

        // & 数组不行
        int[] nums = { 1, 2, 3, 5, 2 };
        int[][] n2ums = { { 2, 3, 5 }, { 4, 2, 12 }, { 123, 444 } };
        System.out.println(nums);// [I@5305068a
        System.out.println(n2ums);// [[I@1f32e575
        System.out.println(Arrays.toString(nums));// [1, 2, 3, 5, 2]
        System.out.println(Arrays.toString(n2ums));
        // [[I@279f2327, [I@2ff4acd0, [I@54bedef2]
        System.out.println(Arrays.deepToString(n2ums));
        // [[2, 3, 5], [4, 2, 12], [123, 444]]
        System.out.println();

        // & class
        // Class a = bedog.getClass();
        // System.out.println(a.getName());
        // System.out.println(a.getSuperclass().getName());

        // @Arraylist
        // -类似C++ vector 但拷贝是拷贝指针，且没有[]运算符
        var ss = new int[s.length()];
        // & 运行时再规定内存大小，但不能再更改
        // - Arraylist取代了vector
        ArrayList<Bedog> bedogs = new ArrayList<Bedog>();
        ArrayList<Bedog> bedogs2 = new ArrayList<>();// & 省略里面的
        var bedogs3 = new ArrayList<Bedog>();// & var
        var arr = new ArrayList<>();// ! 不要这么写，默认Object
        bedogs.add(bedog);// & 自动增加容量
        bedogs.ensureCapacity(10);// & 至少10个

        ArrayList<Bedog> bedogs100 = new ArrayList<>(100);
        // & capcity 100 可能有100个,实际可能少
        Bedog[] bedogs100_array = new Bedog[100];
        // &size 100 肯定有100个
        System.out.println(bedogs100.size());// 0
        System.out.println(bedogs100_array.length);// 100

        bedogs100.trimToSize();// & 变成实际长度

        bedogs.set(0, bedog);// & 取代[]
        Bedog bedog_copy = bedogs.get(0);// & 取代[]
        // ! 如果Arraylist不涉及成Arraylist<>那么get()只能返回Object，必须强制转换，而且set时不会检查变量类型

        /*
         * var list = new ArrayList<Dog>();
         * while(...){
         * x = ...;
         * list.add(x);
         * }
         * var a = new Dog[list.size()];
         * list.toArray(a);
         * 拷贝到数组中
         * int n = list.size()/2;
         * list.add(n,e);
         * & 中间插入则所有元素往后移
         * Dog d = list.remove();
         * ! 插入删除效率很低，最好用链表
         * 
         * -可以用for each
         * for(Dog d:list)
         * ...
         */

        Bedog.update(bedogs);// ! 不安全，P192
        // & @SuppressWarnings("unchecked")
        ArrayList<Bedog> result = (ArrayList<Bedog>) Bedog.find("bedogs");
        // ! 以下警告在类里不用ArrayList<Bedog>而用ArrayList时 出现
        /*
         * ! 若不强制转化 Type safety: The expression of type ArrayList needs unchecked
         * conversion to conform to ArrayList<Bedog>
         * ! 若强制转化 Type safety: Unchecked cast from ArrayList to ArrayList<Bedog>
         */
        // & 这是由于Java泛型限制不严格，ArrayList<Bedog>和ArrayList运行时检查一样

        // @包装器wrapper
        // & 把基本类变成对象
        // -如 int long short double float byte char bool
        // -对应 Integer Long Short Double Float Byte Character Boolean
        // & 包装器默认final,没有子类
        // var list=new ArrayList<int>();//! x 不是类
        var list = new ArrayList<Integer>();
        list.add(2);
        // & 实际是list.add(Integer.valueOf(2));自动装箱 来自C#
        int li = list.get(0);// & 实际是list.get(0).intValue();
        Integer li2 = 3;
        li++;
        System.out.println(li == li2);
        // ! 有可能失败，这里检测的是内存位置，也可能成功
        // -用equals代替
    }
}

// @抽象类
abstract class Be {
    public abstract String toBe();
}

final class Bedog extends Dog {
    private double bonus;

    // & 默认所有都是virtual，除非用final标记，就不能再重载
    public Bedog(String name, int age, int code, int bonus) {
        super(name, age, code);// & 相当于C++ Dog::Dog()
        this.bonus = bonus;
    }

    protected void setBonus(double b) {
        bonus = b;
    }// & protected对本package和所有子类可见，比C++不安全
     // & 什么都不加对本package可见

    public Bedog getNextDog() {
        Bedog newdog = getNextDog();
        return newdog;
    }// & 覆盖方法时不限定返回类型，由于Bedog属于Dog，称为可协变的返回类型

    @Override // & 不必要，但若写成public boolean equals(Bedog otherObject)则不是覆盖，因此加上在那种情况报错
    public boolean equals(Object otherObject) {
        if (this == otherObject)
            return true;
        if (otherObject == null)
            return false;
        if (getClass() != otherObject.getClass())
            return false;
        // & 若子类没有自己的equals则可以instanceOf

        var other = (Bedog) otherObject;
        return super.equals(other)
                && bonus == other.bonus;

    }

    public int hashCode() {
        return super.hashCode() + 17 * Double.hashCode(bonus);
        // & 也可以return 7 * name.hashCode() + 11 * new Integer(age).hashCode() + 13 * new
        // Integer(code).hashCode();
        // & 也可以return Object.hash(name,age,code)
        // - 数组是Arrays.hashCode()
    }

    // @toString
    // & 用的就是java.awt.Point[x=10,y=20]
    public String toString() {
        String sup = super.toString();
        return "Bedog" + sup.substring(3, sup.length() - 1) + ", bonus=" + bonus + "]";
    }

    // @Arraylist
    public static void update(ArrayList<Bedog> list) {
    }

    public static ArrayList<Bedog> find(String query) {
        ArrayList<Bedog> b = new ArrayList<Bedog>();
        return b;
    }

}

@SuppressWarnings("unused")
class Cedog extends Dog {
    private double bones;

    public Cedog(String name, int age, int code, int bones) {
        super(name, age, code);// & 相当于C++ Dog::Dog()
        this.bones = bones;
    }
}