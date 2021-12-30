package com.lucas.ferreira.maburn.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMath {
    public static String math(String pattern, String string) throws Exception {
        Pattern p = Pattern.compile(pattern);
        Matcher match = p.matcher(string);
        if (match.find()) {
            return match.group(0);
        }else{
            throw new Exception("No match found");
        }
    }
}
