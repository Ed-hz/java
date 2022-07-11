
import java.util.*;//����import��һ�������Ѿ�import��

@SuppressWarnings("unused")
public class chap5_inher {
    public static void main(String[] args) {
        // - Javaû�ж��ؼ̳�
        // @��̬
        Dog dog = new Bedog("Big", 12, 12678, 12);
        Bedog bedog = new Bedog("Big", 12, 12678, 12);

        bedog.setBonus(12);
        // ! dog.setBonus(12);
        // & ��Ȼʵ��Ҳ�ǵ������ã�ֻ���������еĺ���

        // @��İ���
        Bedog[] abedog = new Bedog[10];
        Dog[] adog = abedog;
        adog[0] = dog;// & ����
        // adog[0] = new Dog("Big", 12, 12678); ���Ͽ��ԣ�ʵ�ⱨ��
        // ! ����abedog��adog��ָ��Ŀ�¡�����Ծ�ȻBedog[]����DogԪ��
        // ! abedog[0].setBonus(12);���
        abedog[0].setBonus(12);// & ����

        // @����ת��
        if (adog[1] instanceof Bedog) {
            Bedog dd = (Bedog) adog[1];
        }
        // & ǿ������ת��������dynamic_cast,ת��ʧ�ܲ����null�����쳣

        // - private,static,final������������Ϊ��̬�󶨣���̬Ϊ��̬��
        // & ��̬�󶨲���method table
        // & ����ʱȨ�ޱ����public���������public�����಻��private

        // * ����final���������أ��ֶ�final�൱��const����finalֻfinal��������final�ֶ�
        // & String������final

        // @Object
        // & ������ĳ���
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
        System.out.println(hash);// & ��ʵ�ʵ�ɢ��һ��
        System.out.println(str.hashCode());

        var s = "Ok";
        var t = new String("Ok");
        var sb = new StringBuilder(s);
        var tb = new StringBuilder(t);
        System.out.println(s.hashCode() + " " + sb.hashCode());
        System.out.println(t.hashCode() + " " + tb.hashCode());
        // & s��tɢ��һ������StringBuilderû�����õ�ɢ�У�������λ��

        System.out.println(dog.hashCode());
        System.out.println();

        // @toString
        System.out.println(bedog.toString());
        System.out.println(dog.equals(bedog));
        System.out.println(System.out);// java.io.PrintStream@28a418fc
        System.out.println(bedog);// & �����toString
        System.out.println();

        // & ���鲻��
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
        // -����C++ vector �������ǿ���ָ�룬��û��[]�����
        var ss = new int[s.length()];
        // & ����ʱ�ٹ涨�ڴ��С���������ٸ���
        // - Arraylistȡ����vector
        ArrayList<Bedog> bedogs = new ArrayList<Bedog>();
        ArrayList<Bedog> bedogs2 = new ArrayList<>();// & ʡ�������
        var bedogs3 = new ArrayList<Bedog>();// & var
        var arr = new ArrayList<>();// ! ��Ҫ��ôд��Ĭ��Object
        bedogs.add(bedog);// & �Զ���������
        bedogs.ensureCapacity(10);// & ����10��

        ArrayList<Bedog> bedogs100 = new ArrayList<>(100);
        // & capcity 100 ������100��,ʵ�ʿ�����
        Bedog[] bedogs100_array = new Bedog[100];
        // &size 100 �϶���100��
        System.out.println(bedogs100.size());// 0
        System.out.println(bedogs100_array.length);// 100

        bedogs100.trimToSize();// & ���ʵ�ʳ���

        bedogs.set(0, bedog);// & ȡ��[]
        Bedog bedog_copy = bedogs.get(0);// & ȡ��[]
        // ! ���Arraylist���漰��Arraylist<>��ôget()ֻ�ܷ���Object������ǿ��ת��������setʱ�������������

        /*
         * var list = new ArrayList<Dog>();
         * while(...){
         * x = ...;
         * list.add(x);
         * }
         * var a = new Dog[list.size()];
         * list.toArray(a);
         * ������������
         * int n = list.size()/2;
         * list.add(n,e);
         * & �м����������Ԫ��������
         * Dog d = list.remove();
         * ! ����ɾ��Ч�ʺܵͣ����������
         * 
         * -������for each
         * for(Dog d:list)
         * ...
         */

        Bedog.update(bedogs);// ! ����ȫ��P192
        // & @SuppressWarnings("unchecked")
        ArrayList<Bedog> result = (ArrayList<Bedog>) Bedog.find("bedogs");
        // ! ���¾��������ﲻ��ArrayList<Bedog>����ArrayListʱ ����
        /*
         * ! ����ǿ��ת�� Type safety: The expression of type ArrayList needs unchecked
         * conversion to conform to ArrayList<Bedog>
         * ! ��ǿ��ת�� Type safety: Unchecked cast from ArrayList to ArrayList<Bedog>
         */
        // & ��������Java�������Ʋ��ϸ�ArrayList<Bedog>��ArrayList����ʱ���һ��

        // @��װ��wrapper
        // & �ѻ������ɶ���
        // -�� int long short double float byte char bool
        // -��Ӧ Integer Long Short Double Float Byte Character Boolean
        // & ��װ��Ĭ��final,û������
        // var list=new ArrayList<int>();//! x ������
        var list = new ArrayList<Integer>();
        list.add(2);
        // & ʵ����list.add(Integer.valueOf(2));�Զ�װ�� ����C#
        int li = list.get(0);// & ʵ����list.get(0).intValue();
        Integer li2 = 3;
        li++;
        System.out.println(li == li2);
        // ! �п���ʧ�ܣ�����������ڴ�λ�ã�Ҳ���ܳɹ�
        // -��equals����
    }
}

// @������
abstract class Be {
    public abstract String toBe();
}

final class Bedog extends Dog {
    private double bonus;

    // & Ĭ�����ж���virtual��������final��ǣ��Ͳ���������
    public Bedog(String name, int age, int code, int bonus) {
        super(name, age, code);// & �൱��C++ Dog::Dog()
        this.bonus = bonus;
    }

    protected void setBonus(double b) {
        bonus = b;
    }// & protected�Ա�package����������ɼ�����C++����ȫ
     // & ʲô�����ӶԱ�package�ɼ�

    public Bedog getNextDog() {
        Bedog newdog = getNextDog();
        return newdog;
    }// & ���Ƿ���ʱ���޶��������ͣ�����Bedog����Dog����Ϊ��Э��ķ�������

    @Override // & ����Ҫ������д��public boolean equals(Bedog otherObject)���Ǹ��ǣ���˼����������������
    public boolean equals(Object otherObject) {
        if (this == otherObject)
            return true;
        if (otherObject == null)
            return false;
        if (getClass() != otherObject.getClass())
            return false;
        // & ������û���Լ���equals�����instanceOf

        var other = (Bedog) otherObject;
        return super.equals(other)
                && bonus == other.bonus;

    }

    public int hashCode() {
        return super.hashCode() + 17 * Double.hashCode(bonus);
        // & Ҳ����return 7 * name.hashCode() + 11 * new Integer(age).hashCode() + 13 * new
        // Integer(code).hashCode();
        // & Ҳ����return Object.hash(name,age,code)
        // - ������Arrays.hashCode()
    }

    // @toString
    // & �õľ���java.awt.Point[x=10,y=20]
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
        super(name, age, code);// & �൱��C++ Dog::Dog()
        this.bones = bones;
    }
}