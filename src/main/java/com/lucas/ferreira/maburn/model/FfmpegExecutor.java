package com.lucas.ferreira.maburn.model;

import com.lucas.ferreira.maburn.model.documents.Documents;
import com.lucas.ferreira.maburn.model.metadata.video.VideoMetadata;
import com.lucas.ferreira.maburn.util.RegexMatch;
import com.microsoft.playwright.Video;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FfmpegExecutor {
    private static final String NEWLINE = System.getProperty("line.separator");
    private static final String FFMPEG_EXE = Documents.FFMPEG_LOCAL;
    public ExecutorResult concatenateFiles(String inputFilesPath, String outputFile) throws IOException {
        String cmd = FFMPEG_EXE + " -y -f concat -safe 0 -i " + inputFilesPath + " -c copy " + outputFile;
        System.out.println(cmd);
        return execute(cmd);
    }


    public ExecutorResult convertTsToMp4(String inputPath, String outputPath) throws IOException {
        String cmd =  FFMPEG_EXE +" -i "+inputPath+" -acodec copy -vcodec copy " + outputPath;
        System.out.println(cmd);
        return execute(cmd);
    }
    public long getVideoDuration(String videoPath) throws IOException {
        String cmd = FFMPEG_EXE + " -i \""+videoPath+"\" -f null -";
        System.out.println(cmd);
        ExecutorResult executorResult = execute(cmd);
        if(executorResult.isSuccess()){
            String std = executorResult.getOutput();
            System.out.println("Duration: " + std.substring(std.indexOf("Duration: "),std.indexOf(", start") + ", start".length()));
        }
        return 0L;
    }
    public VideoMetadata getVideoInfo(String videoPath) throws Exception {
        String cmd = FFMPEG_EXE + " -i \""+videoPath+"\" -f null -";
        System.out.println(cmd);
        ExecutorResult executorResult = execute(cmd);

        if(executorResult.isSuccess()){
            String std = executorResult.getOutput();
            System.out.println(std);
            VideoMetadata videoMetadata = getVideoInfoFromString(std);
            return videoMetadata;
        }
        return null;
    }

    private VideoMetadata getVideoInfoFromString(String std) throws Exception {
        VideoMetadata videoMetadata = new VideoMetadata();

        String strResolution = RegexMatch.match("\\d{3,4}x\\d{3,4}",std);
        String [] dimensions = strResolution.split("x");

        String width = dimensions[0];
        String height = dimensions[1];

        videoMetadata.setHeight(height);
        videoMetadata.setWidth(width);

        String duration = RegexMatch.match("\\d{2}:\\d{2}:\\d{2}",std);
        videoMetadata.setDuration(duration);

        return videoMetadata;
    }

    private ExecutorResult execute(String cmd) throws IOException {
        String[] splitCmd = cmd.split(" ");
        ProcessBuilder pb = new ProcessBuilder(splitCmd).redirectErrorStream(true);

        Process process = pb.start();
        return getProcessOutput(process);
    }


    private ExecutorResult getProcessOutput(final Process process) throws IOException {
        ExecutorResult executorResult = new ExecutorResult();

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(process.getInputStream()));
        String outputString = getStringFromBufferedReader(stdInput);
        executorResult.setOutput(outputString);
        if (process.exitValue() != 0) {
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
