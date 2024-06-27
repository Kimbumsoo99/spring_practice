package com.ssafy.grading.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Slf4j
@Service
public class GradeService {
    private static final String BASE_PATH = "src/main/resources/static/";
    private static final String SOLUTION_FILE_PATH = BASE_PATH + "Solution.java";
    private static final String INPUT_FILE_PATH = BASE_PATH + "input.txt";
    private static final String OUTPUT_FILE_PATH = BASE_PATH + "output.txt";
    private static final String OUTPUT_USER_FILE_PATH = BASE_PATH + "output_user.txt";
    private static final String COMPILE_COMMAND = "javac " + SOLUTION_FILE_PATH;
    private static final String EXECUTE_COMMAND = "java -cp " + BASE_PATH + " Solution";

    public void saveSolution(String user, String answerCode) throws IOException {
        try (FileWriter fileWriter = new FileWriter(new File(SOLUTION_FILE_PATH))) {
            fileWriter.write("// User: " + user + "\n");
            fileWriter.write(answerCode);
        }
    }

    public void generateTestData(int testCaseCount) throws IOException {
        generateInputFile(testCaseCount);
        generateOutputFile();
    }

    public String getUserResult() throws IOException, InterruptedException {
        return compileAndRun();
    }

    private void generateInputFile(int testCaseCount) throws IOException {
        try (FileWriter fileWriter = new FileWriter(new File(INPUT_FILE_PATH))) {
            SecureRandom random = new SecureRandom();

            fileWriter.write(testCaseCount + "\n");
            for (int i = 0; i < testCaseCount; i++) {
                int a = random.nextInt(100); // Random number between 0 and 99
                int b = random.nextInt(100);
                fileWriter.write(a + " " + b + "\n");
            }
        }
    }
    private void generateOutputFile() throws IOException {
        try (FileWriter fileWriter = new FileWriter(new File(OUTPUT_FILE_PATH))) {
            File inputFile = new File(INPUT_FILE_PATH);

            if (!inputFile.exists()) {
                throw new FileNotFoundException("Input file not found: " + INPUT_FILE_PATH);
            }
            System.out.println("input 확인");
            System.out.println(inputFile);

            try (Scanner scanner = new Scanner(inputFile)) {
                int testCaseCount = scanner.nextInt();
                System.out.println(testCaseCount);
                for (int i = 0; i < testCaseCount; i++) {
                    int a = scanner.nextInt();
                    int b = scanner.nextInt();
                    int sum = a + b;
                    fileWriter.write(sum + "\n");
                }
            }
            System.out.println("Output File Create");
        }
    }

    public String compileAndRun() throws IOException, InterruptedException {
        // Compile the solution
        Process compileProcess = Runtime.getRuntime().exec(COMPILE_COMMAND); // 컴파일 명령어를 실행하는 프로세스를 생성합니다.
        int compileResult = compileProcess.waitFor(); // 컴파일 프로세스가 종료될 때까지 기다립니다.
        log.debug("compileResult - {}", compileResult);
        if (compileResult != 0) { // 만약 컴파일 결과가 정상적이지 않다면 (0은 성공을 의미)
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()))) {
                StringBuilder errorMsg = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) { // 컴파일 오류 메시지를 읽어와서 errorMsg에 저장합니다.
                    errorMsg.append(line).append("\n");
                }
                return "Compilation Error: " + errorMsg.toString(); // 컴파일 오류 메시지를 반환합니다.
            }
        }
        System.out.println("compile Success");

        // Execute the solution with input from input.txt
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", BASE_PATH, "Solution");
        processBuilder.redirectErrorStream(true);
        Process executeProcess = processBuilder.start();

        // Provide input to the process
        try (BufferedWriter processInputWriter = new BufferedWriter(new OutputStreamWriter(executeProcess.getOutputStream()));
             Scanner scanner = new Scanner(new File(INPUT_FILE_PATH));
             BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(executeProcess.getInputStream()));
             BufferedWriter outputWriter = new BufferedWriter(new FileWriter(OUTPUT_USER_FILE_PATH))) {
            // Write input.txt content to process input
            while (scanner.hasNextLine()) {
                processInputWriter.write(scanner.nextLine() + "\n");
            }
            processInputWriter.flush(); // Ensure all input is written

            // Read process output and write to output_user.txt
            String line;
            while ((line = processOutputReader.readLine()) != null) {
                System.out.println(line); // For debugging purposes
                outputWriter.write(line + "\n");
            }
            outputWriter.flush();
        }

        int executeResult = executeProcess.waitFor(); // Wait for the process to finish
        if (executeResult != 0) {
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(executeProcess.getErrorStream()))) {
                StringBuilder errorMsg = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorMsg.append(line).append("\n");
                }
                return "Runtime Error: " + errorMsg.toString();
            }
        }

        System.out.println("런타임 실행");

        return grade(); // 실행이 정상적으로 완료되었으면 결과를 평가하기 위해 grade() 메서드를 호출합니다.
    }

    private String grade() throws IOException {
        List<String> expectedOutput = Files.readAllLines(Paths.get(OUTPUT_FILE_PATH));
        List<String> userOutput = Files.readAllLines(Paths.get(OUTPUT_USER_FILE_PATH));

        log.info("expectedOutput - {}", expectedOutput);
        log.info("userOutput - {}", userOutput);

        if (expectedOutput.equals(userOutput)) {
            return "Correct";
        } else {
            return "Wrong Answer";
        }
    }
}
