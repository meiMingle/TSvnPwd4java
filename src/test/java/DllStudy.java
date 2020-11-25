import com.sun.jna.*;
import junit.framework.TestCase;

public class DllStudy extends TestCase {
    public interface CLibrary extends Library {


        DllStudy.CLibrary INSTANCE = Native.load((Platform.isWindows() ? "DllStudy" : "c"), DllStudy.CLibrary.class);

        void SomeFunction();

        int add(int a, int b);

        NativeLong factorial(NativeLong n, UserStruct.ByReference pUserStruct);


    }

    //JNA 模仿C语言结构体
    @Structure.FieldOrder({"id", "name", "age"})
    public static class UserStruct extends Structure {
        public NativeLong id;
        public WString name;
        public int age;

        public static class ByReference extends UserStruct implements Structure.ByReference {
        }

        public static class ByValue extends UserStruct implements Structure.ByValue {
        }
    }


    public  void test1() {
        //1.helloworld
        CLibrary.INSTANCE.SomeFunction();
        //2.
        int addResult = CLibrary.INSTANCE.add(66, 22);
        assertEquals("加法测试不通过",88, addResult);
        //3.
        UserStruct.ByReference pUserStruct = new UserStruct.ByReference();
        pUserStruct.id = new NativeLong(100L);
        pUserStruct.age = 30;
        pUserStruct.name = new WString("奥巴马");
        assertEquals("阶乘方法测试不通过",new Long( 1932053504L),new Long(CLibrary.INSTANCE.factorial(new NativeLong(13L), pUserStruct).longValue()));
    }
}
