package com.demo.cicd.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class VideoService {
    private static String getBasePath() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "c:/openvidu/";
        } else {
            return "/openvidu/";
        }
    }

    private static final String INPUT_PATH_1 = getBasePath() + "recordings/1/1.mp4";
    private static final String INPUT_PATH_2 = getBasePath() + "recordings/2/2.mp4";
    private static final String INPUT_PATH_3 = getBasePath() + "recordings/3/3.mp4";
    private static final String TEMP_OUTPUT_PATH_1 = getBasePath() + "temp/1_converted.mp4";
    private static final String TEMP_OUTPUT_PATH_2 = getBasePath() + "temp/2_converted.mp4";
    private static final String TEMP_OUTPUT_PATH_3 = getBasePath() + "temp/3_converted.mp4";
    private static final String OUTPUT_PATH = getBasePath() + "output/";
    private static final String FILE_LIST_PATH = getBasePath() + "fileList.txt";

    private static String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    public String mergeVideos() throws IOException, InterruptedException {
        // Ensure temp and output directories exist
        File tempDir = new File(getBasePath() + "temp");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        File outputDir = new File(OUTPUT_PATH);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        // Convert videos to a common format
        convertVideo(INPUT_PATH_1, TEMP_OUTPUT_PATH_1);
        convertVideo(INPUT_PATH_2, TEMP_OUTPUT_PATH_2);
        convertVideo(INPUT_PATH_3, TEMP_OUTPUT_PATH_3);

        // Create a text file with the list of video files
        File listFile = new File(FILE_LIST_PATH);

        // Generate a unique output file path
        String uniqueOutputPath = OUTPUT_PATH + "merged_" + getTimestamp() + ".mp4";

        // Merge videos using FFmpeg
        Process process = getProcess(listFile, uniqueOutputPath);

        // Print the output and error of the FFmpeg command
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }
        }

        int exitCode = process.waitFor();

        // Clean up only if FFmpeg completed successfully
        if (exitCode == 0) {
            // Delete the file list
            listFile.delete();

            // Delete temporary folders and mp4 files
            deleteTemporaryFiles(tempDir);

            return "Video merge successful and temporary files cleaned up.";
        } else {
            return "FFmpeg process failed with exit code " + exitCode;
        }
    }

    private static Process getProcess(File listFile, String outputPath) throws IOException {
        try (PrintWriter writer = new PrintWriter(listFile)) {
            writer.println("file '" + TEMP_OUTPUT_PATH_1 + "'");
            writer.println("file '" + TEMP_OUTPUT_PATH_2 + "'");
            writer.println("file '" + TEMP_OUTPUT_PATH_3 + "'");
        }

        // Build the FFmpeg command for merging videos
        String[] command = {
                "ffmpeg",
                "-f", "concat",
                "-safe", "0",
                "-i", FILE_LIST_PATH,
                "-c", "copy",
                outputPath
        };

        // Execute the command
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        return processBuilder.start();
    }

    private void convertVideo(String inputPath, String outputPath) throws IOException, InterruptedException {
        // FFmpeg command to convert video to a common format
        String[] command = {
                "ffmpeg",
                "-i", inputPath,
                "-vf", "scale=1280:720",
                "-r", "30",
                "-c:v", "libx264",
                "-crf", "23",
                "-preset", "fast",
                "-c:a", "aac",
                "-b:a", "128k",
                outputPath
        };

        // Execute the conversion command
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        // Print the output and error of the FFmpeg command
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("FFmpeg process for conversion failed with exit code " + exitCode);
        }
    }

    private void deleteTemporaryFiles(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    } else if (file.isDirectory()) {
                        deleteTemporaryFiles(file);
                    }
                }
            }
            directory.delete();
        }
    }
}