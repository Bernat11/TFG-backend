package com.upf.etic.documentwithqr.util;

import com.upf.etic.documentwithqr.exceptions.JSONReaderException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static JSONObject getJSONfromFile(String resource) throws JSONReaderException {
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(new FileReader(resource));
        } catch (IOException | ParseException e) {
            throw new JSONReaderException("An error occurred reading JSON");
        }
    }
}
