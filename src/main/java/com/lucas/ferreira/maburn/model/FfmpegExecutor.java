package com.lucas.ferreira.maburn.model;

import com.lucas.ferreira.maburn.model.documents.Documents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FfmpegExecutor {
    private static final String NEWLINE = System.getProperty("line.separator");
    private static final String FFMPEG_EXE = Documents.FFMPEG_LOCAL;

    public ExecutorResult concatenateFiles(String inputFilesPath, String outputFile) throws IOException {
        String cmd = FFMPEG_EXE + " -y -f concat -safe 0 -i " + inputFilesPath + " -c copy " + outputFile;
        return execute(cmd);
    }

    private ExecutorResult execute(String cmd) throws IOException {
        String[] splitCmd = cmd.split(" ");
        ProcessBuilder pb = new ProcessBuilder(splitCmd).redirectErrorStream(true);
        ;
        Process process = pb.start();
        return getProcessOutput(process);
    }

    private ExecutorResult getProcessOutput(final Process process) throws IOException {
        ExecutorResult executorResult = new ExecutorResult();

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(process.getInputStream()));
        String outputString = getStringFromBufferedReader(stdInput);
        if (process.exitValue() != 0) {
            executorResult.setErrorOutput(outputString);
            return executorResult;
        }
        executorResult.setSuccess(true);
        return executorResult;
    }

    private String getStringFromBufferedReader(BufferedReader in) throws IOException {
        StringBuilder result = new StringBuilder(80);
        while (true) {
            String line = in.readLine();
            if (line == null)
                break;
            result.append(line).append(NEWLINE);
        }
        return result.toString();
    }

}
