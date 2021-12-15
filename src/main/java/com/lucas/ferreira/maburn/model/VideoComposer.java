package com.lucas.ferreira.maburn.model;

import com.lucas.ferreira.maburn.exceptions.VideoComposerException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class VideoComposer {
    private FfmpegExecutor ffmpegExecutor = new FfmpegExecutor();
    public File compose(List<File> files, String outputFile) throws IOException, VideoComposerException {
        String text = createTxtWithFilesPath(files);

        File filesPathText = createFilesPath();

        File wroteFilesPath = writeFilesPath(filesPathText,text);
        ExecutorResult executorResult = ffmpegExecutor.concatenateFiles(wroteFilesPath.getAbsolutePath(),outputFile);
        if(executorResult.isSuccess()){
            return new File(outputFile);
        }
        throw new VideoComposerException("Error on concatenate files, output: {" +
                executorResult.getErrorOutput() +"}");
    }
    private String createTxtWithFilesPath(List<File> files) throws IOException {
        String text = files.stream().map(file ->  "file '" + file.getAbsolutePath() + "'").collect(Collectors.joining("\n"));

        return text;
    }

    private File createFilesPath() throws IOException {
        File filesPathText = Files.createTempFile("list-files-",".txt").toFile();
        filesPathText.deleteOnExit();
        return filesPathText;
    }

    private File writeFilesPath( File filesPathText,String text) throws IOException {


        PrintWriter writer = new PrintWriter(filesPathText, "UTF-8");
        writer.print(text);
        writer.close();
        return filesPathText;
    }
}
