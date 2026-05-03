package com.springarena.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileUtils {

    public static void writeFile(Path path, String content) throws IOException {
        if (Files.notExists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }
        Files.writeString(path, content, StandardCharsets.UTF_8);
    }

    public static void copyDirectory(Path source, Path target) throws IOException {
        if (Files.notExists(source)) {
            return;
        }
        try (Stream<Path> stream = Files.walk(source)) {
            stream.forEach(sourcePath -> {
                try {
                    Path targetPath = target.resolve(source.relativize(sourcePath).toString());
                    if (Files.isDirectory(sourcePath)) {
                        if (Files.notExists(targetPath)) {
                            Files.createDirectories(targetPath);
                        }
                    } else {
                        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public static List<String> runProcess(Path directory, List<String> command) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(directory.toFile());
        builder.redirectErrorStream(true);
        Process process = builder.start();

        List<String> output = new ArrayList<>();
        try (Reader reader = process.inputReader(StandardCharsets.UTF_8)) {
            String line;
            BufferedReader buffered = new BufferedReader(reader);
            while ((line = buffered.readLine()) != null) {
                output.add(line);
            }
        }

        int exitCode = process.waitFor();
        output.add("Process exited with code " + exitCode);
        return output;
    }
}
