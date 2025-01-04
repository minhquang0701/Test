package testSupport;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class CodeSaver {
    static private final String filepath = "src/testSupport/";
    static private final String csvFilename = "CodeSaver.csv";

    public static void saveCodeToCSV(String methodName) {
        try {
            // Ensure the directory exists
            File directory = new File(filepath);
            if (!directory.exists()) {
                directory.mkdirs();  // Create directory if it doesn't exist
            }

            // Get the path of the class file (assuming standard directory structure)
            String classPath = Paths.get("src", "recursion", "power_of_2.java").toString();

            // Read the entire file
            String code = Files.lines(Paths.get(classPath)).collect(Collectors.joining("\n"));

            // Write to CSV (FileOutputStream style)
            File myObj = new File(filepath + csvFilename);
            myObj.createNewFile();  // Create if not exists

            try (FileOutputStream fos = new FileOutputStream(filepath + csvFilename, true)) {
                StringBuilder output = new StringBuilder();
                output.append("Test Name: ")
                      .append(methodName)
                      .append("\n")
                      .append(code)
                      .append("\n=================\n");

                String outputString = output.toString();
                fos.write(outputString.getBytes());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
