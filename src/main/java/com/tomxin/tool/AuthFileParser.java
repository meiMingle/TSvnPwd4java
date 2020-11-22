package com.tomxin.tool;

import com.tomxin.tool.tangible.RefObject;
import com.tomxin.tool.tangible.TryParseHelper;

import java.io.*;
import java.util.*;

// A simple, naive parser to read a limited subset of Subversion configuration files.
// This class does not understand the full range of syntax for subversion files and
// will fail on files that are in an unexpected - yet legitimate - format.
//
// The files containing cached credentials, as created by TortoiseSVN on Windows, appear
// to contain key value pairs that are broken up into multiple lines.  The logic below is
// based on a brief examination of a single "svn.simple" file.


public class AuthFileParser
{

  // Set a limit on maximum lines parsed to avoid stalling out on big files
  private static final int MAX_LINES = 1000;

  // Parser states
  private enum States
  {
	ExpectingKeyDef,
	ExpectingKeyName,
	ExpectingValueDef,
	ExpectingValue;

	  public static final int SIZE = Integer.SIZE;

	  public int getValue()
	  {
		  return this.ordinal();
	  }

	  public static States forValue(int value)
	  {
		  return values()[value];
	  }
  }

  // Current state
  private States state = States.ExpectingKeyDef;

  // Data persisted between states
  private String keyName = "";
  private int nextLength = -1;

  // Values read so far
  private HashMap<String, String> props = new HashMap<String, String>();

  // Only allow access through static ReadFile() method
  private AuthFileParser()
  {
  }

  private boolean tryParseNextLine(String line)
  {

	switch (state)
	{
	  case ExpectingKeyDef:
		  return parseKeyDef(line);
	  case ExpectingKeyName:
		  return parseKeyName(line);
	  case ExpectingValueDef:
		  return parseValueDef(line);
	  case ExpectingValue:
		  return parseValue(line);
	  default:
		  return false;
	}

  }

  private boolean parseKeyDef(String line)
  {
	if (!parseDefLine("K", line))
	{
		return false;
	}
	state = States.ExpectingKeyName;
	return true;
  }

  private boolean parseKeyName(String line)
  {
	if (!parseValLine(line))
	{
		return false;
	}
	state = States.ExpectingValueDef;
	return true;
  }

  private boolean parseValueDef(String line)
  {
	if (!parseDefLine("V", line))
	{
		return false;
	}
	state = States.ExpectingValue;
	return true;
  }

  private boolean parseValue(String line)
  {
	if (!parseValLine(line))
	{
		return false;
	}
	state = States.ExpectingKeyDef;
	return true;
  }

  // Do some rudimentary validation to ensure the current line looks like a definition
  // line, then parse it.  A definition line looks something like "K #" or "V #",
  // where # is the length of the next line.  K means the next line will be a key name,
  // while V means it will be a value.  # will be stored in nextLength.
  private boolean parseDefLine(String prefix, String line)
  {
	line = line.trim();
	if (!line.toUpperCase().startsWith(prefix + " "))
	{
		return false;
	}
	String[] parts = line.split("[ ]", -1);
	if (parts.length != 2)
	{
		return false;
	}
	RefObject<Integer> tempRef_nextLength = new RefObject<Integer>(nextLength);
	if (!TryParseHelper.tryParseInt(parts[1], tempRef_nextLength))
	{
		nextLength = tempRef_nextLength.argValue;
		return false;
	}
	else
	{
		nextLength = tempRef_nextLength.argValue;
	}
	return true;
  }

  // Read a key name or value line.  If this is a value line, then save the key/value
  // pair that has just been read.
  private boolean parseValLine(String line)
  {

	if (line.length() < nextLength)
	{
		return false;
	}
	String val = line.substring(0, nextLength);
	nextLength = -1;

	if (state == States.ExpectingKeyName)
	{
	  keyName = val.trim();
	  if (keyName.equals(""))
	  {
		  return false;
	  }
	  if (keyName.contains(" "))
	  {
		  return false;
	  }
	}
	else
	{
	  props.put(keyName, val);
	  keyName = "";
	}

	return true;
  }

  public static HashMap<String, String> ReadFile(String path) throws IOException {
	AuthFileParser parser = new AuthFileParser();
	try (BufferedReader brd = new BufferedReader(new InputStreamReader(new FileInputStream("E:\\gbk.txt"),"UTF-8"));)
	{


	  String line=null;
	  for (int lineNum = 1;(line=brd.readLine()) != null;lineNum++)
	  {

		if (lineNum > MAX_LINES)
		{
			break;
		}

		// Skip comment lines
		if (!line.trim().startsWith("#"))
		{

		  // Check for end of file marker
		  if (parser.state == States.ExpectingKeyDef && line.trim().toUpperCase().equals("END"))
		  {
			return parser.props; // Return results
		  }

		  // Attempt to parse the line
		  if (!parser.tryParseNextLine(line))
		  {
			  throw new AuthParseException(path, lineNum);
		  }

		}

	  }

	  // If reached this point, we either encountered too many lines or the file
	  // ended prematurely.
	  throw new AuthParseException(path, -1);
	}

  }
}