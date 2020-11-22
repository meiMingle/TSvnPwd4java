package com.tomxin.tool;// THIS MODULE IS ADAPTED FROM A THIRD PARTY.  BELOW ARE LICENSE NOTES. -----------------------
//
// The code in this file comes from the following website:
//
// http://www.obviex.com/samples/Code.aspx?Source=DpapiCS&Title=Using%20DPAPI&Lang=C%23
// It was retrieved January 25, 2011.
//
// At the bottom of the page there is a link called Legal.  Upon clicking it, the following
// license terms are displayed at http://www.obviex.com/Legal.aspx:
//
//   Code Samples
//   This Site contains code samples written by Obviex™, as well as links to code samples
//   available from third parties. All code samples that are provided or referenced on this
//   Site are property of their respective owners. You may use code samples copyrighted by
//   Obviex™ without asking for explicit permission, provided that You leave the original
//   copyright notice in the source code. Obviex™ is not responsible, accountable, or liable
//   for any damages, problems, or difficulties resulting from use of the code samples
//   available or referenced on this Site.
//
// The code sample in this module has been included based on these terms.
//
// Additionally, the following is the text of an email dated Jan 25, 2011 from the author
// of the sample code:
//
//   Hi Richard,
//
//   Please feel free to use the samples in any way you want. If you click the copyright
//   message, you can find the use terms, but there are no restrictions to speak of.
//
//   Best regards,
//
//   Alex Bogdanov
//   Obviex
//
// --------------------------------------------------------------------------------------------


///////////////////////////////////////////////////////////////////////////////
// SAMPLE: Encryption and decryption using DPAPI functions.
//
// To run this sample, create a new Visual C# project using the Console
// Application template and replace the contents of the Class1.cs file
// with the code below.
//
// THIS CODE AND INFORMATION IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR
// PURPOSE.
//
// Copyright (C) 2003 Obviex(TM). All rights reserved.
//

import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.tomxin.tool.tangible.RefObject;

/**
 Encrypts and decrypts data using DPAPI functions.
*/
public class DPAPI{}
/*
{
  // Wrapper for DPAPI CryptProtectData function.
  private static native boolean CryptProtectData(RefObject<DATA_BLOB> pPlainText, String szDescription, RefObject<DATA_BLOB> pEntropy, IntByReference pReserved, RefObject<CRYPTPROTECT_PROMPTSTRUCT> pPrompt, int dwFlags, RefObject<DATA_BLOB> pCipherText);
  static
  {
	  System.loadLibrary("crypt32.dll");
  }

  // Wrapper for DPAPI CryptUnprotectData function.
  private static native boolean CryptUnprotectData(RefObject<DATA_BLOB> pCipherText, RefObject<String> pszDescription, RefObject<DATA_BLOB> pEntropy, IntByReference pReserved, RefObject<CRYPTPROTECT_PROMPTSTRUCT> pPrompt, int dwFlags, RefObject<DATA_BLOB> pPlainText);

  // BLOB structure used to pass data to DPAPI functions.
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential, CharSet = CharSet.Unicode)] internal struct DATA_BLOB
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
  @Structure.FieldOrder({"cbData","pbData"})
	public  static class DATA_BLOB
  {
	public int cbData;
	//public IntByReference pbData = System.IntByReference.Zero;
	public IntByReference pbData = new IntByReference();

	  */
/*public DATA_BLOB clone()
	  {
		  DATA_BLOB varCopy = new DATA_BLOB();

		  varCopy.cbData = this.cbData;
		  varCopy.pbData = this.pbData;

		  return varCopy;
	  }*//*

	  public static class ByReference extends DATA_BLOB implements Structure.ByReference {}
	  public static class ByValue extends DATA_BLOB implements Structure.ByValue {}
  }

  // Prompt structure to be used for required parameters.
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential, CharSet = CharSet.Unicode)] internal struct CRYPTPROTECT_PROMPTSTRUCT
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
  public final static class CRYPTPROTECT_PROMPTSTRUCT
  {
	public int cbSize;
	public int dwPromptFlags;
	//public IntByReference pbData = System.IntByReference.Zero;
	public IntByReference hwndApp = new IntByReference();
	public String szPrompt;

	  public CRYPTPROTECT_PROMPTSTRUCT clone()
	  {
		  CRYPTPROTECT_PROMPTSTRUCT varCopy = new CRYPTPROTECT_PROMPTSTRUCT();

		  varCopy.cbSize = this.cbSize;
		  varCopy.dwPromptFlags = this.dwPromptFlags;
		  varCopy.hwndApp = this.hwndApp;
		  varCopy.szPrompt = this.szPrompt;

		  return varCopy;
	  }
  }

  // Wrapper for the NULL handle or pointer.
  //private static IntByReference NullPtr = ((IntByReference)((int)(0)));
  private static IntByReference NullPtr = new IntByReference(0);

  // DPAPI key initialization flags.
  private static final int CRYPTPROTECT_UI_FORBIDDEN = 0x1;
  private static final int CRYPTPROTECT_LOCAL_MACHINE = 0x4;

  */
/**
   Initializes empty prompt structure.
   
   @param ps
   Prompt parameter (which we do not actually need).
   
  *//*

  private static void InitPrompt(RefObject<CRYPTPROTECT_PROMPTSTRUCT> ps)
  {
	ps.argValue.cbSize = System.Runtime.InteropServices.Marshal.SizeOf(CRYPTPROTECT_PROMPTSTRUCT.class);
	ps.argValue.dwPromptFlags = 0;
	ps.argValue.hwndApp = NullPtr;
	ps.argValue.szPrompt = null;
  }

  */
/**
   Initializes a BLOB structure from a byte array.
   
   @param data
   Original data in a byte array format.
   
   @param blob
   Returned blob structure.
   
  *//*

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static void InitBLOB(byte[] data, ref DATA_BLOB blob)
  private static void InitBLOB(byte[] data, RefObject<DATA_BLOB> blob)
  {
	// Use empty array for null parameter.
	if (data == null)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data = new byte[0];
	  data = new byte[0];
	}

	// Allocate memory for the BLOB data.
	blob.argValue.pbData = Marshal.AllocHGlobal(data.length);

	// Make sure that memory allocation was successful.
	if (System.IntByReference.OpEquality(blob.argValue.pbData, IntByReference.Zero))
	{
	  throw new RuntimeException("Unable to allocate data buffer for BLOB structure.");
	}

	// Specify number of bytes in the BLOB.
	blob.argValue.cbData = data.length;

	// Copy data from original source to the BLOB structure.
	Marshal.Copy(data, 0, blob.argValue.pbData, data.length);
  }

  // Flag indicating the type of key. DPAPI terminology refers to
  // key types as user store or machine store.
  public enum KeyType
  {
	  UserKey(1),
	  MachineKey(2);

	  public static final int SIZE = Integer.SIZE;

	  private int intValue;
	  private static java.util.HashMap<Integer, KeyType> mappings;
	  private static java.util.HashMap<Integer, KeyType> getMappings()
	  {
		  if (mappings == null)
		  {
			  synchronized (KeyType.class)
			  {
				  if (mappings == null)
				  {
					  mappings = new java.util.HashMap<Integer, KeyType>();
				  }
			  }
		  }
		  return mappings;
	  }

	  private KeyType(int value)
	  {
		  intValue = value;
		  getMappings().put(value, this);
	  }

	  public int getValue()
	  {
		  return intValue;
	  }

	  public static KeyType forValue(int value)
	  {
		  return getMappings().get(value);
	  }
  }

  // It is reasonable to set default key type to user key.
  private static KeyType defaultKeyType = KeyType.UserKey;

  */
/**
   Calls DPAPI CryptProtectData function to encrypt a plaintext
   string value with a user-specific key. This function does not
   specify data description and additional entropy.
   
   @param plainText
   Plaintext data to be encrypted.
   
   @return 
   Encrypted value in a base64-encoded format.
   
  *//*

  public static String Encrypt(String plainText)
  {
	return Encrypt(defaultKeyType, plainText, "", "");
  }

  */
/**
   Calls DPAPI CryptProtectData function to encrypt a plaintext
   string value. This function does not specify data description
   and additional entropy.
   
   @param keyType
   Defines type of encryption key to use. When user key is
   specified, any application running under the same user account
   as the one making this call, will be able to decrypt data.
   Machine key will allow any application running on the same
   computer where data were encrypted to perform decryption.
   Note: If optional entropy is specifed, it will be required
   for decryption.
   
   @param plainText
   Plaintext data to be encrypted.
   
   @return 
   Encrypted value in a base64-encoded format.
   
  *//*

  public static String Encrypt(KeyType keyType, String plainText)
  {
	return Encrypt(keyType, plainText, "", "");
  }

  */
/**
   Calls DPAPI CryptProtectData function to encrypt a plaintext
   string value. This function does not specify data description.
   
   @param keyType
   Defines type of encryption key to use. When user key is
   specified, any application running under the same user account
   as the one making this call, will be able to decrypt data.
   Machine key will allow any application running on the same
   computer where data were encrypted to perform decryption.
   Note: If optional entropy is specifed, it will be required
   for decryption.
   
   @param plainText
   Plaintext data to be encrypted.
   
   @param entropy
   Optional entropy which - if specified - will be required to
   perform decryption.
   
   @return 
   Encrypted value in a base64-encoded format.
   
  *//*

  public static String Encrypt(KeyType keyType, String plainText, String entropy)
  {
	return Encrypt(keyType, plainText, entropy, "");
  }

  */
/**
   Calls DPAPI CryptProtectData function to encrypt a plaintext
   string value.
   
   @param keyType
   Defines type of encryption key to use. When user key is
   specified, any application running under the same user account
   as the one making this call, will be able to decrypt data.
   Machine key will allow any application running on the same
   computer where data were encrypted to perform decryption.
   Note: If optional entropy is specifed, it will be required
   for decryption.
   
   @param plainText
   Plaintext data to be encrypted.
   
   @param entropy
   Optional entropy which - if specified - will be required to
   perform decryption.
   
   @param description
   Optional description of data to be encrypted. If this value is
   specified, it will be stored along with encrypted data and
   returned as a separate value during decryption.
   
   @return 
   Encrypted value in a base64-encoded format.
   
  *//*

  public static String Encrypt(KeyType keyType, String plainText, String entropy, String description)
  {
	// Make sure that parameters are valid.
	if (plainText == null)
	{
		plainText = "";
	}
	if (entropy == null)
	{
		entropy = "";
	}

	// Call encryption routine and convert returned bytes into
	// a base64-encoded value.
	return Convert.ToBase64String(Encrypt(keyType, plainText.getBytes(java.nio.charset.StandardCharsets.UTF_8), entropy.getBytes(java.nio.charset.StandardCharsets.UTF_8), description));
  }

  */
/**
   Calls DPAPI CryptProtectData function to encrypt an array of
   plaintext bytes.
   
   @param keyType
   Defines type of encryption key to use. When user key is
   specified, any application running under the same user account
   as the one making this call, will be able to decrypt data.
   Machine key will allow any application running on the same
   computer where data were encrypted to perform decryption.
   Note: If optional entropy is specifed, it will be required
   for decryption.
   
   @param plainTextBytes
   Plaintext data to be encrypted.
   
   @param entropyBytes
   Optional entropy which - if specified - will be required to
   perform decryption.
   
   @param description
   Optional description of data to be encrypted. If this value is
   specified, it will be stored along with encrypted data and
   returned as a separate value during decryption.
   
   @return 
   Encrypted value.
   
  *//*

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] Encrypt(KeyType keyType, byte[] plainTextBytes, byte[] entropyBytes, string description)
  public static byte[] Encrypt(KeyType keyType, byte[] plainTextBytes, byte[] entropyBytes, String description)
  {
	// Make sure that parameters are valid.
	if (plainTextBytes == null)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: plainTextBytes = new byte[0];
		plainTextBytes = new byte[0];
	}
	if (entropyBytes == null)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: entropyBytes = new byte[0];
		entropyBytes = new byte[0];
	}
	if (description == null)
	{
		description = "";
	}

	// Create BLOBs to hold data.
	DATA_BLOB plainTextBlob = new DATA_BLOB();
	DATA_BLOB cipherTextBlob = new DATA_BLOB();
	DATA_BLOB entropyBlob = new DATA_BLOB();

	// We only need prompt structure because it is a required
	// parameter.
	CRYPTPROTECT_PROMPTSTRUCT prompt = new CRYPTPROTECT_PROMPTSTRUCT();
	RefObject<CRYPTPROTECT_PROMPTSTRUCT> tempRef_prompt = new RefObject<CRYPTPROTECT_PROMPTSTRUCT>(prompt);
	InitPrompt(tempRef_prompt);
	prompt = tempRef_prompt.argValue;

	try
	{
	  // Convert plaintext bytes into a BLOB structure.
	  try
	  {
		RefObject<DATA_BLOB> tempRef_plainTextBlob = new RefObject<DATA_BLOB>(plainTextBlob);
		InitBLOB(plainTextBytes, tempRef_plainTextBlob);
		plainTextBlob = tempRef_plainTextBlob.argValue;
	  }
	  catch (RuntimeException ex)
	  {
		throw new RuntimeException("Cannot initialize plaintext BLOB.", ex);
	  }

	  // Convert entropy bytes into a BLOB structure.
	  try
	  {
		RefObject<DATA_BLOB> tempRef_entropyBlob = new RefObject<DATA_BLOB>(entropyBlob);
		InitBLOB(entropyBytes, tempRef_entropyBlob);
		entropyBlob = tempRef_entropyBlob.argValue;
	  }
	  catch (RuntimeException ex)
	  {
		throw new RuntimeException("Cannot initialize entropy BLOB.", ex);
	  }

	  // Disable any types of UI.
	  int flags = CRYPTPROTECT_UI_FORBIDDEN;

	  // When using machine-specific key, set up machine flag.
	  if (keyType == KeyType.MachineKey)
	  {
		flags |= CRYPTPROTECT_LOCAL_MACHINE;
	  }

	  // Call DPAPI to encrypt data.
	  RefObject<DATA_BLOB> tempRef_plainTextBlob2 = new RefObject<DATA_BLOB>(plainTextBlob);
	  RefObject<DATA_BLOB> tempRef_entropyBlob2 = new RefObject<DATA_BLOB>(entropyBlob);
	  RefObject<CRYPTPROTECT_PROMPTSTRUCT> tempRef_prompt2 = new RefObject<CRYPTPROTECT_PROMPTSTRUCT>(prompt);
	  RefObject<DATA_BLOB> tempRef_cipherTextBlob = new RefObject<DATA_BLOB>(cipherTextBlob);
	  boolean success = CryptProtectData(tempRef_plainTextBlob2, description, tempRef_entropyBlob2, IntByReference.Zero, tempRef_prompt2, flags, tempRef_cipherTextBlob);
	  cipherTextBlob = tempRef_cipherTextBlob.argValue;
	  prompt = tempRef_prompt2.argValue;
	  entropyBlob = tempRef_entropyBlob2.argValue;
	  plainTextBlob = tempRef_plainTextBlob2.argValue;
	  // Check the result.
	  if (!success)
	  {
		// If operation failed, retrieve last Win32 error.
		int errCode = Marshal.GetLastWin32Error();

		// Win32Exception will contain error message corresponding
		// to the Windows error code.
		throw new RuntimeException("CryptProtectData failed.", new Win32Exception(errCode));
	  }

	  // Allocate memory to hold ciphertext.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] cipherTextBytes = new byte[cipherTextBlob.cbData];
	  byte[] cipherTextBytes = new byte[cipherTextBlob.cbData];

	  // Copy ciphertext from the BLOB to a byte array.
	  Marshal.Copy(cipherTextBlob.pbData, cipherTextBytes, 0, cipherTextBlob.cbData);

	  // Return the result.
	  return cipherTextBytes;
	}
	catch (RuntimeException ex)
	{
	  throw new RuntimeException("DPAPI was unable to encrypt data.", ex);
	}
	  // Free all memory allocated for BLOBs.
	finally
	{
	  if (System.IntByReference.OpInequality(plainTextBlob.pbData, IntByReference.Zero))
	  {
		Marshal.FreeHGlobal(plainTextBlob.pbData);
	  }

	  if (System.IntByReference.OpInequality(cipherTextBlob.pbData, IntByReference.Zero))
	  {
		Marshal.FreeHGlobal(cipherTextBlob.pbData);
	  }

	  if (System.IntByReference.OpInequality(entropyBlob.pbData, IntByReference.Zero))
	  {
		Marshal.FreeHGlobal(entropyBlob.pbData);
	  }
	}
  }

  */
/**
   Calls DPAPI CryptUnprotectData to decrypt ciphertext bytes.
   This function does not use additional entropy and does not
   return data description.
   
   @param cipherText
   Encrypted data formatted as a base64-encoded string.
   
   @return 
   Decrypted data returned as a UTF-8 string.
   
   
   When decrypting data, it is not necessary to specify which
   type of encryption key to use: user-specific or
   machine-specific; DPAPI will figure it out by looking at
   the signature of encrypted data.
   
  *//*

  public static String Decrypt(String cipherText)
  {
	String description = null;

	RefObject<String> tempRef_description = new RefObject<String>(description);
	String tempVar = Decrypt(cipherText, "", tempRef_description);
	description = tempRef_description.argValue;
	return tempVar;
  }

  */
/**
   Calls DPAPI CryptUnprotectData to decrypt ciphertext bytes.
   This function does not use additional entropy.
   
   @param cipherText
   Encrypted data formatted as a base64-encoded string.
   
   @param description
   Returned description of data specified during encryption.
   
   @return 
   Decrypted data returned as a UTF-8 string.
   
   
   When decrypting data, it is not necessary to specify which
   type of encryption key to use: user-specific or
   machine-specific; DPAPI will figure it out by looking at
   the signature of encrypted data.
   
  *//*

  public static String Decrypt(String cipherText, RefObject<String> description)
  {
	return Decrypt(cipherText, "", description);
  }

  */
/**
   Calls DPAPI CryptUnprotectData to decrypt ciphertext bytes.
   
   @param cipherText
   Encrypted data formatted as a base64-encoded string.
   
   @param entropy
   Optional entropy, which is required if it was specified during
   encryption.
   
   @param description
   Returned description of data specified during encryption.
   
   @return 
   Decrypted data returned as a UTF-8 string.
   
   
   When decrypting data, it is not necessary to specify which
   type of encryption key to use: user-specific or
   machine-specific; DPAPI will figure it out by looking at
   the signature of encrypted data.
   
  *//*

  public static String Decrypt(String cipherText, String entropy, RefObject<String> description)
  {
	// Make sure that parameters are valid.
	if (entropy == null)
	{
		entropy = "";
	}

	return Encoding.UTF8.GetString(Decrypt(Convert.FromBase64String(cipherText), entropy.getBytes(java.nio.charset.StandardCharsets.UTF_8), description));
  }

  */
/**
   Calls DPAPI CryptUnprotectData to decrypt ciphertext bytes.
   
   @param cipherTextBytes
   Encrypted data.
   
   @param entropyBytes
   Optional entropy, which is required if it was specified during
   encryption.
   
   @param description
   Returned description of data specified during encryption.
   
   @return 
   Decrypted data bytes.
   
   
   When decrypting data, it is not necessary to specify which
   type of encryption key to use: user-specific or
   machine-specific; DPAPI will figure it out by looking at
   the signature of encrypted data.
   
  *//*

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] Decrypt(byte[] cipherTextBytes, byte[] entropyBytes, out string description)
  public static byte[] Decrypt(byte[] cipherTextBytes, byte[] entropyBytes, RefObject<String> description)
  {
	// Create BLOBs to hold data.
	DATA_BLOB plainTextBlob = new DATA_BLOB();
	DATA_BLOB cipherTextBlob = new DATA_BLOB();
	DATA_BLOB entropyBlob = new DATA_BLOB();

	// We only need prompt structure because it is a required
	// parameter.
	CRYPTPROTECT_PROMPTSTRUCT prompt = new CRYPTPROTECT_PROMPTSTRUCT();
	RefObject<CRYPTPROTECT_PROMPTSTRUCT> tempRef_prompt = new RefObject<CRYPTPROTECT_PROMPTSTRUCT>(prompt);
	InitPrompt(tempRef_prompt);
	prompt = tempRef_prompt.argValue;

	// Initialize description string.
	description.argValue = "";

	try
	{
	  // Convert ciphertext bytes into a BLOB structure.
	  try
	  {
		RefObject<DATA_BLOB> tempRef_cipherTextBlob = new RefObject<DATA_BLOB>(cipherTextBlob);
		InitBLOB(cipherTextBytes, tempRef_cipherTextBlob);
		cipherTextBlob = tempRef_cipherTextBlob.argValue;
	  }
	  catch (RuntimeException ex)
	  {
		throw new RuntimeException("Cannot initialize ciphertext BLOB.", ex);
	  }

	  // Convert entropy bytes into a BLOB structure.
	  try
	  {
		RefObject<DATA_BLOB> tempRef_entropyBlob = new RefObject<DATA_BLOB>(entropyBlob);
		InitBLOB(entropyBytes, tempRef_entropyBlob);
		entropyBlob = tempRef_entropyBlob.argValue;
	  }
	  catch (RuntimeException ex)
	  {
		throw new RuntimeException("Cannot initialize entropy BLOB.", ex);
	  }

	  // Disable any types of UI. CryptUnprotectData does not
	  // mention CRYPTPROTECT_LOCAL_MACHINE flag in the list of
	  // supported flags so we will not set it up.
	  int flags = CRYPTPROTECT_UI_FORBIDDEN;

	  // Call DPAPI to decrypt data.
	  RefObject<DATA_BLOB> tempRef_cipherTextBlob2 = new RefObject<DATA_BLOB>(cipherTextBlob);
	  RefObject<DATA_BLOB> tempRef_entropyBlob2 = new RefObject<DATA_BLOB>(entropyBlob);
	  RefObject<CRYPTPROTECT_PROMPTSTRUCT> tempRef_prompt2 = new RefObject<CRYPTPROTECT_PROMPTSTRUCT>(prompt);
	  RefObject<DATA_BLOB> tempRef_plainTextBlob = new RefObject<DATA_BLOB>(plainTextBlob);
	  boolean success = CryptUnprotectData(tempRef_cipherTextBlob2, description, tempRef_entropyBlob2, IntByReference.Zero, tempRef_prompt2, flags, tempRef_plainTextBlob);
	  plainTextBlob = tempRef_plainTextBlob.argValue;
	  prompt = tempRef_prompt2.argValue;
	  entropyBlob = tempRef_entropyBlob2.argValue;
	  cipherTextBlob = tempRef_cipherTextBlob2.argValue;

	  // Check the result.
	  if (!success)
	  {
		// If operation failed, retrieve last Win32 error.
		int errCode = Marshal.GetLastWin32Error();

		// Win32Exception will contain error message corresponding
		// to the Windows error code.
		throw new RuntimeException("CryptUnprotectData failed.", new Win32Exception(errCode));
	  }

	  // Allocate memory to hold plaintext.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] plainTextBytes = new byte[plainTextBlob.cbData];
	  byte[] plainTextBytes = new byte[plainTextBlob.cbData];

	  // Copy ciphertext from the BLOB to a byte array.
	  Marshal.Copy(plainTextBlob.pbData, plainTextBytes, 0, plainTextBlob.cbData);

	  // Return the result.
	  return plainTextBytes;
	}
	catch (RuntimeException ex)
	{
	  throw new RuntimeException("DPAPI was unable to decrypt data.", ex);
	}
	  // Free all memory allocated for BLOBs.
	finally
	{
	  if (System.IntByReference.OpInequality(plainTextBlob.pbData, IntByReference.Zero))
	  {
		Marshal.FreeHGlobal(plainTextBlob.pbData);
	  }

	  if (System.IntByReference.OpInequality(cipherTextBlob.pbData, IntByReference.Zero))
	  {
		Marshal.FreeHGlobal(cipherTextBlob.pbData);
	  }

	  if (System.IntByReference.OpInequality(entropyBlob.pbData, IntByReference.Zero))
	  {
		Marshal.FreeHGlobal(entropyBlob.pbData);
	  }
	}
  }
}*/
