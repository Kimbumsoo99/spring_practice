package com.ssafy.grading.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;

@Service
public class GradeService {
    private static final String SOLUTION_FILE_PATH  = "Solution.java";
    private static final String INPUT_FILE_PATH = "src/main/resources/static/input.txt";
    private static final String OUTPUT_FILE_PATH = "src/main/resources/static/output.txt";

    public void saveSolution(String user, String answerCode) throws IOException {
        try (FileWriter fileWriter = new FileWriter(new File(SOLUTION_FILE_PATH))) {
            fileWriter.write("// User: " + user + "\n");
            fileWriter.write(answerCode);
        }
    }

    public void generateTestData() throws IOException {
        // Generate input.txt
        generateInputFile();

        // Generate output.txt
        generateOutputFile();
    }

    private void generateInputFile() throws IOException {
        try (FileWriter fileWriter = new FileWriter(new File(INPUT_FILE_PATH))) {
            SecureRandom random = new SecureRandom();
            int testCaseCount = 5; // Example number of test cases

            fileWriter.write(testCaseCount + "\n");
            for (int i = 0; i < testCaseCount; i++) {
                int a = random.nextInt(100); // Random number between 0 and 99
                int b = random.nextInt(100);
                fileWriter.write(a + " " + b + "\n");
            }
        }
    }

    private void generateOutputFile() throws IOException {
        // Generate output based on the input.txt values
        // This example assumes a simple addition problem
        try (FileWriter inputFileReader = new FileWriter(new File(INPUT_FILE_PATH));
             FileWriter fileWriter = new FileWriter(new File(OUTPUT_FILE_PATH))) {
            SecureRandom random = new SecureRandom();
            int testCaseCount = 5; // Example number of test cases

            inputFileReader.write(testCaseCount + "\n");
            for (int i = 0; i < testCaseCount; i++) {
                int a = random.nextInt(100); // Random number between 0 and 99
                int b = random.nextInt(100);
                inputFileReader.write(a + " " + b + "\n");
                fileWriter.write((a + b) + "\n"); // Expected output for the addition problem
            }
        }
    }
}
