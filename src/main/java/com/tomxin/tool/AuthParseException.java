package com.tomxin.tool;

import java.util.*;

public class AuthParseException extends RuntimeException {

    private String path;
    private int lineNum;

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