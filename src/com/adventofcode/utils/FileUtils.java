package com.adventofcode.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileUtils {
    public static String[] readFileAsLines(String pathString) {
        Path path = Path.of(pathString);
        List<String> result;
        try {
            result = Files.readAllLines(path);
            return result.toArray(new String[0]);
        } catch (FileNotFoundException fnf) {
            System.out.println("File \"" + path + "\" not found.");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }
}
