/* Copyright (c) Daniel Doubrovkine, All Rights Reserved
 *
 * The contents of this file is dual-licensed under 2
 * alternative Open Source/Free licenses: LGPL 2.1 or later and
 * Apache License 2.0. (starting with JNA version 4.0.0).
 *
 * You can freely decide which license you want to apply to
 * the project.
 *
 * You may obtain a copy of the LGPL License at:
 *
 * http://www.gnu.org/licenses/licenses.html
 *
 * A copy is also included in the downloadable source code package
 * containing JNA, in file "LGPL2.1".
 *
 * You may obtain a copy of the Apache License at:
 *
 * http://www.apache.org/licenses/
 *
 * A copy is also included in the downloadable source code package
 * containing JNA, in file "AL2.0".
 */

import com.sun.jna.platform.win32.Crypt32Util;
import com.sun.jna.platform.win32.WinCrypt;
import junit.framework.TestCase;

import com.sun.jna.Native;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author dblock[at]dblock[dot]org
 */
public class Crypt32UtilTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(Crypt32UtilTest.class);
    }

    public void testCryptProtectUnprotectData() {
        byte[] data = new byte[2];
        data[0] = 42;
        data[1] = 12;
        byte[] protectedData = Crypt32Util.cryptProtectData(data);
        byte[] unprotectedData = Crypt32Util.cryptUnprotectData(protectedData);
        assertEquals(data.length, unprotectedData.length);
        assertEquals(data[0], unprotectedData[0]);
        assertEquals(data[1], unprotectedData[1]);
    }

    public void testCryptProtectUnprotectMachineKey() {
        String s = "Hello World";
        byte[] data = Native.toByteArray(s);
        byte[] protectedData = Crypt32Util.cryptProtectData(data,
                WinCrypt.CRYPTPROTECT_LOCAL_MACHINE | WinCrypt.CRYPTPROTECT_UI_FORBIDDEN);
        byte[] unprotectedData = Crypt32Util.cryptUnprotectData(protectedData,
                WinCrypt.CRYPTPROTECT_LOCAL_MACHINE);
        String unprotectedString = Native.toString(unprotectedData);
        assertEquals(s, unprotectedString);
    }

    public void test() {
        String s = "AQAAANCMnd8BFdERjHoAwE/Cl+sBAAAAwkIzHdBkRUWS8ZFsG1S4xQAAAAAyAAAAYQB1AHQAaABfAHMAdgBuAC4AcwBpAG0AcABsAGUALgB3AGkAbgBjAHIAeQBwAHQAAAADZgAAwAAAABAAAADfY/y8vQBPO5LbTM+fjC4eAAAAAASAAACgAAAAEAAAAB1OXr0m7vo2NHl71yx7hZMIAAAAi4PzKo7dxXgUAAAARXlLX2fuuVGARYDoLX0REiBB4AY=";
        // byte[] data = Native.toByteArray(s);
        byte[] data = Base64.getDecoder().decode(s);
        byte[] protectedData = Crypt32Util.cryptProtectData(data,
                WinCrypt.CRYPTPROTECT_LOCAL_MACHINE | WinCrypt.CRYPTPROTECT_UI_FORBIDDEN);
        byte[] unprotectedData = Crypt32Util.cryptUnprotectData(protectedData,
                WinCrypt.CRYPTPROTECT_LOCAL_MACHINE);
        String unprotectedString = Native.toString(unprotectedData);
        byte[] encode = Base64.getEncoder().encode(unprotectedData);
        String ss = Native.toString(unprotectedData);

        assertEquals(data.length, unprotectedData.length);
    }

}