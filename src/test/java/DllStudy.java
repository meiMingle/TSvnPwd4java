import com.sun.jna.*;

public class DllStudy {
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


    public static void main(String[] args) {
        //1.helloworld
        CLibrary.INSTANCE.SomeFunction();
        //2.
        System.out.println(CLibrary.INSTANCE.add(25555, 36));
        //3.
        UserStruct.ByReference pUserStruct = new UserStruct.ByReference();
        pUserStruct.id = new NativeLong(100L);
        pUserStruct.age = 30;
        pUserStruct.name = new WString("奥巴马");
        System.out.println(CLibrary.INSTANCE.factorial(new NativeLong(13L), pUserStruct));
    }
}
