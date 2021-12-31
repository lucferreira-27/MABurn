package com.lucas.ferreira.maburn.model;

import com.lucas.ferreira.maburn.exceptions.VideoComposerException;
import com.lucas.ferreira.maburn.util.RegexMatch;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class VideoComposer {
    private FfmpegExecutor ffmpegExecutor = new FfmpegExecutor();

    public File compose(List<File> files, String outputFile) throws IOException, VideoComposerException {
        List<File> sortFiles = sortFiles(files);
        String text = createTxtWithFilesPath(sortFiles);
        File filesPathText = createFilesPath();

        File wroteFilesPath = writeFilesPath(filesPathText, text);
        ExecutorResult executorResult = ffmpegExecutor.concatenateFiles(wroteFilesPath.getAbsolutePath(), outputFile);

        if (executorResult.isSuccess()) {
            String output = outputFile.replace("\"","");
            return new File(output);
        }
        throw new VideoComposerException("Error on concatenate files, output: {" +
                executorResult.getOutput() + "}");
    }
    private List<File> sortFiles(List<File> files){
        return files.stream().sorted((File f1, File f2) -> {
            try {
                String fileName1 = RegexMatch.match("(?<=-)\\d+", f1.getAbsolutePath());
                String fileName2 = RegexMatch.match("(?<=-)\\d+", f2.getAbsolutePath());
                int n1 = Integer.parseInt(fileName1);
                int n2 = Integer.parseInt(fileName2);
                return Integer.compare(n1,n2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }).collect(Collectors.toList());
    }

    private String createTxtWithFilesPath(List<File> files) throws IOException {
        String text = files.stream().map(file -> "file '" + file.getAbsolutePath() + "'").collect(Collectors.joining("\n"));

        return text;
    }

    private File createFilesPath() throws IOException {
        File filesPathText = Files.createTempFile("list-files-", ".txt").toFile();
        filesPathText.deleteOnExit();
        return filesPathText;
    }

    private File writeFilesPath(File filesPathText, String text) throws IOException {


        PrintWriter writer = new PrintWriter(filesPathText, "UTF-8");
        writer.print(text);
        writer.close();
        return filesPathText;
    }
}
