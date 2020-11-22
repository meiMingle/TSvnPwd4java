package com.tomxin.tool;

import com.sun.deploy.util.StringUtils;
import com.tomxin.tool.tangible.RefObject;

import java.io.File;
import java.io.IOException;
import java.util.*;

// This program was cobbled together by Richard Kagerer at Leapbeyond Solutions Inc.
// It is Copyright (c) 2011 Leapbeyond Solutions Inc.
//
// Feel free to use the code however you wish.  If modifying the code or
// using it in your own project, it would be appreciated if you include a
// reference to the original author in your source code.


public class Program{}
/*
{

*/
/*
  private static final int GENERAL_ERROR = -1; // Error value returned by console program if failed
  private static final int ERROR_TOOMANY = -2; // Error value returned if only partial output displayed
  private static final String AUTHFILE_SUBPATH = "Subversion\\auth\\svn.simple"; // Relative path to password files (from %APPDATA%)
  private static final int MAX_FILES_COUNT = 200; // After this many password files processed, abort

  static void main(String[] args)
  {

	boolean interactive = true;
	if (args.length > 0 && args[1].toUpperCase().equals("-S"))
	{
		interactive = false; // Silent (no prompt) switch
	}

	try
	{
	  Run();
	}
	catch (RuntimeException e)
	{
	  System.out.println();
	  System.out.println(e.getMessage());
	  System.out.println(e.getStackTrace());
	}
	finally
	{
	  if (interactive)
	  {
	  	Scanner scanner=new Scanner(System.in);
		System.out.println();
		System.out.println("Press any key.");
		//Console.ReadKey(true);
		  String key=scanner.next();
	  }
	}
  }

  private static void Run()
  {

	// Show version and introductory info
	System.out.println("TortoiseSVN Password Decrypter v" + Version());
	System.out.println("The original version of this program was created by Leapbeyond Solutions.");

	// Look for password files
	//String folder = java.nio.file.Paths.get(Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData)).resolve(AUTHFILE_SUBPATH).toString();
	String folder = java.nio.file.Paths.get(System.getProperty(System.getenv("APPDATA"))).resolve(AUTHFILE_SUBPATH).toString();
	if (!(new java.io.File(folder)).isDirectory())
	{
		ExitWithError("Path not found: " + folder);
	}

	// TODO Read secrt file name
	//String[] files = Directory.GetFiles(folder, new String('?', 32)); // Password filenames appear to be 32 characters in length
	  File file = new File(folder);
	  File[] files = file.listFiles((path, name) -> name.length() == 32);
	  if (files.length < 1)
	{
		ExitWithError("No files with exactly 32 characters in the filename found in " + folder);
	}

	System.out.println();
	System.out.println(String.format("Found %1$s cached credentials files in %2$s", files.length, folder));

	// Iterate each
	String username = "", repository = "", encryptedPassword = "", decryptedPassword = "";
	for (int i = 0; i < files.length; i++)
	{

	  if (i > MAX_FILES_COUNT)
	  {
		  ExitWithError("Listing aborted.  Too many files in " + folder, ERROR_TOOMANY);
	  }

	  System.out.println();
	  System.out.println("Parsing " + (files[i].getName()));

	  RefObject<String> tempRef_username = new RefObject<String>(username);
	  RefObject<String> tempRef_repository = new RefObject<String>(repository);
	  RefObject<String> tempRef_encryptedPassword = new RefObject<String>(encryptedPassword);
	  if (TryParseAuthFile(files[i].getAbsolutePath(), tempRef_username, tempRef_repository, tempRef_encryptedPassword))
	  {
		  encryptedPassword = tempRef_encryptedPassword.argValue;
		  repository = tempRef_repository.argValue;
		  username = tempRef_username.argValue;
		System.out.println("Repository: " + repository);
		System.out.println("Username: " + username);
		RefObject<String> tempRef_decryptedPassword = new RefObject<String>(decryptedPassword);
		if (TryDecryptPassword(encryptedPassword, tempRef_decryptedPassword))
		{
			decryptedPassword = tempRef_decryptedPassword.argValue;
		  System.out.println("Password: " + decryptedPassword);
		}
		else
		{
			decryptedPassword = tempRef_decryptedPassword.argValue;
		}
	  }
	  else
	  {
		  encryptedPassword = tempRef_encryptedPassword.argValue;
		  repository = tempRef_repository.argValue;
		  username = tempRef_username.argValue;
	  }

	} // end for

  }

  private static String Version()
  {
	//System.Version ver = Assembly.GetExecutingAssembly().GetName().Version;
	//return String.format("%1$s.%2$s.%3$s", ver.Major, ver.Minor, ver.Build);
	  Properties props=System.getProperties(); //获得系统属性集
	  String osName = props.getProperty("os.name"); //操作系统名称
	  String osArch = props.getProperty("os.arch"); //操作系统构架
	  String osVersion = props.getProperty("os.version"); //操作系统版本
	  return String.format("%1$s.%2$s.%3$s", osName, osArch, osVersion);
  }

  private static void ExitWithError(String error)
  {
	ExitWithError(error, GENERAL_ERROR);
  }

  private static void ExitWithError(String error, int errorCode)
  {
	System.out.println();
	System.out.println(error);
	System.exit(errorCode);
  }

  private static boolean TryParseAuthFile(String path, RefObject<String> username, RefObject<String> repository, RefObject<String> encryptedPassword)
  {

	username.argValue = "";
	repository.argValue = "";
	encryptedPassword.argValue = "";

	// Read file and parse key/value pairs
	HashMap <String, String> results = null;
	try
	{
	  results = AuthFileParser.ReadFile(path);
*//*

*/
/*	  if (!results.get("username", username))
	  {
		  return false;
	  }
	  if (!results.TryGetValue("svn:realmstring", repository))
	  {
		  return false;
	  }
	  if (!results.TryGetValue("password", encryptedPassword))
	  {
		  return false;
	  }*//*
*/
/*

		username.argValue = results.get("username")!=null&&!"".equals(results.get("username"))?results.get("username"):"";
		repository.argValue = results.get("svn:realmstring")!=null&&!"".equals(results.get("svn:realmstring"))?results.get("svn:realmstring"):"";
		encryptedPassword.argValue = results.get("password")!=null&&!"".equals(results.get("password"))?results.get("password"):"";
	  return true;
	}
	catch (AuthParseException | IOException e)
	{
	  System.out.println(e.getMessage());
	  return false;
	}

  }

  private static boolean TryDecryptPassword(String encrypted, RefObject<String> decrypted)
  {
	decrypted.argValue = "";
	try
	{
	  decrypted.argValue = DPAPI.Decrypt(encrypted);
	  return true;
	}
	catch (RuntimeException e)
	{
	  System.out.println("Unable to decrypt the password");
	  return false;
	}
  }

*//*

}*/
