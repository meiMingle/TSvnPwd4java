package com.tomxin.tool;


//----------------------------------------------------------------------------------------
//	Copyright © 2007 - 2017 Tangible Software Solutions Inc.
//	This class can be used by anyone provided that the copyright notice remains intact.
//
//	This class is used to hand Exception message
//----------------------------------------------------------------------------------------
public class AuthParseException extends RuntimeException {

    private final String path;
    private final int lineNum;

    public AuthParseException(String path, int lineNum) {
        this.path = path;
        this.lineNum = lineNum;
    }

    public final String getPath() {
        return path;
    }

    public final int getLineNum() {
        return lineNum;
    }

    @Override
    public String getMessage() {
        if (lineNum != -1) {
            return String.format("Error parsing line %1$s of %2$s", lineNum, path);
        } else {
            return String.format("Error parsing %1$s", path);
        }
    }

}