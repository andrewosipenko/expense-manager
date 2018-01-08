package com.es.jointexpensetracker.web.util;

/**
 * Created by Koshelek on 08.01.2018.
 */
public class Validator {

    private static final String VALID_STRING_REGEX = "^[\\w\\s-]+$";

    public static boolean validateString(String str){
        return (str != null) && (str.matches(VALID_STRING_REGEX));
    }
}
