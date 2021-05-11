package com.tomxin.tool;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Crypt32Util;
import com.tomxin.tool.tangible.RefObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;


/**
 * This program was cobbled together by Richard Kagerer at Leapbeyond Solutions Inc.
 * It is Copyright (c) 2011 Leapbeyond Solutions Inc.
 * <p>
 * Feel free to use the code however you wish.  If modifying the code or
 * using it in your own project, it would be appreciated if you include a
 * reference to the original author in your source code.
 */
public class Program {
    /**
     * Error value returned by console program if failed
     */
    private static final int GENERAL_ERROR = -1;

    /**
     * Error value returned if only partial output displayed
     */
    private static final int ERROR_TOOMANY = -2;

    /**
     * Relative path to password files (from %APPDATA%)
     */
    private static final String AUTHFILE_SUBPATH = "Subversion\\auth\\svn.simple";
    /**
     * After this many password files processed, abort
     */
    private static final int MAX_FILES_COUNT = 200;

    public static void main(String[] args) {

        boolean interactive = true;
        if (args.length > 0 && "-S".equals(args[1].toUpperCase())) {
            // Silent (no prompt) switch
            interactive = false;
        }

        try {
            Run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            if (interactive) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Press any key.");
                String key = scanner.next();
            }
        }
    }



    private static void Run() {

        // Show version and introductory info
        System.out.println("TortoiseSVN Password Decrypter v" + Version());
        System.out.println("The original version of this program was created by Leapbeyond Solutions use C#.The java version created by meiMingle on github.");

        // Look for password files
        String folder = java.nio.file.Paths.get(System.getenv("APPDATA")).resolve(AUTHFILE_SUBPATH).toString();
        if (!(new java.io.File(folder)).isDirectory()) {
            ExitWithError("Path not found: " + folder);
        }

        //String[] files = Directory.GetFiles(folder, new String('?', 32)); // Password filenames appear to be 32 characters in length
        File file = new File(folder);
        File[] files = file.listFiles((path, name) -> name.length() == 32);
        if (files.length < 1) {
            ExitWithError("No files with exactly 32 characters in the filename found in " + folder);
        }

        System.out.println();
        System.out.printf("Found %1$s cached credentials files in %2$s%n", files.length, folder);

        // Iterate each
        String username = "", repository = "", encryptedPassword = "", decryptedPassword = "";
        for (int i = 0; i < files.length; i++) {

            if (i > MAX_FILES_COUNT) {
                ExitWithError("Listing aborted.  Too many files in " + folder, ERROR_TOOMANY);
            }

            System.out.println();
            System.out.println("Parsing " + (files[i].getName()));

            RefObject<String> tempRefUsername = new RefObject<String>(username);
            RefObject<String> tempRefRepository = new RefObject<String>(repository);
            RefObject<String> tempRefEncryptedPassword = new RefObject<String>(encryptedPassword);
            if (TryParseAuthFile(files[i].getAbsolutePath(), tempRefUsername, tempRefRepository, tempRefEncryptedPassword)) {
                encryptedPassword = tempRefEncryptedPassword.argValue;
                repository = tempRefRepository.argValue;
                username = tempRefUsername.argValue;
                System.out.println("Repository: " + repository);
                System.out.println("Username: " + username);
                RefObject<String> tempRefDecryptedPassword = new RefObject<String>(decryptedPassword);
                if (tryDecryptPassword(encryptedPassword, tempRefDecryptedPassword)) {
                    decryptedPassword = tempRefDecryptedPassword.argValue;
                    System.out.println("Password: " + decryptedPassword);
                } else {
                    decryptedPassword = tempRefDecryptedPassword.argValue;
                }
            } else {
                encryptedPassword = tempRefEncryptedPassword.argValue;
                repository = tempRefRepository.argValue;
                username = tempRefUsername.argValue;
            }

        } // end for

    }

    private static String Version() {
        //System.Version ver = Assembly.GetExecutingAssembly().GetName().Version;
        //return String.format("%1$s.%2$s.%3$s", ver.Major, ver.Minor, ver.Build);
        Properties props = System.getProperties(); //获得系统属性集
        String osName = props.getProperty("os.name"); //操作系统名称
        String osArch = props.getProperty("os.arch"); //操作系统构架
        String osVersion = props.getProperty("os.version"); //操作系统版本
        return String.format("%1$s.%2$s.%3$s", osName, osArch, osVersion);
    }

    private static void ExitWithError(String error) {
        ExitWithError(error, GENERAL_ERROR);
    }

    private static void ExitWithError(String error, int errorCode) {
        System.out.println();
        System.out.println(error);
        System.exit(errorCode);
    }

    private static boolean TryParseAuthFile(String path, RefObject<String> username, RefObject<String> repository, RefObject<String> encryptedPassword) {

        username.argValue = "";
        repository.argValue = "";
        encryptedPassword.argValue = "";

        // Read file and parse key/value pairs
        HashMap<String, String> results = null;
        try {
            results = AuthFileParser.ReadFile(path);
            if (results.get("username") != null && !"".equals(results.get("username"))) {
                username.argValue = results.get("username");
            } else {
                return false;
            }
            if (results.get("svn:realmstring") != null && !"".equals(results.get("svn:realmstring"))) {
                repository.argValue = results.get("svn:realmstring");
            } else {
                return false;
            }
            if (results.get("password") != null && !"".equals(results.get("password"))) {
                encryptedPassword.argValue = results.get("password");
            } else {
                return false;
            }
            return true;
        } catch (AuthParseException | IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    private static boolean tryDecryptPassword(String encrypted, RefObject<String> decrypted) {
        decrypted.argValue = "";
        try {
            //Native.toByteArray(encrypted, StandardCharsets.UTF_8);
            byte[] data = Base64.getDecoder().decode(encrypted);
            byte[] unprotectedData = Crypt32Util.cryptUnprotectData(data);
            decrypted.argValue = Native.toString(unprotectedData);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("Unable to decrypt the password");
            return false;
        }
    }


}
