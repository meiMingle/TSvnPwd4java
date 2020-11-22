import com.sun.jna.Library;

import com.sun.jna.Native;

import com.sun.jna.Platform;


/**
 * Simple example of native library declaration and usage.
 */

public class HelloWorld {


    public interface CLibrary extends Library {

        CLibrary INSTANCE = Native.load((Platform.isWindows() ? "msvcrt" : "c"), CLibrary.class);

        void printf(String format, Object... args);

    }


    public static void main(String[] args) {

        CLibrary.INSTANCE.printf("Hello, World\n");

        for (int i = 0; i < args.length; i++) {

            CLibrary.INSTANCE.printf("Argument %d: %s/n", i, args[i]);

        }

    }

}