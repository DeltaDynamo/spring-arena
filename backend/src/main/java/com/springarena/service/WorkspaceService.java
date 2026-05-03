package com.springarena.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class WorkspaceService {

    private Path getRunsRoot() {
        Path backendDir = Paths.get("").toAbsolutePath().resolve("backend").normalize();
        return backendDir.getParent().resolve("runs").normalize();
    }

    public Path createRunDirectory(String challengeId) {
        try {
            Path runsRoot = getRunsRoot();
            if (Files.notExists(runsRoot)) {
                Files.createDirectories(runsRoot);
            }
            Path runDir = runsRoot.resolve(challengeId + "-" + UUID.randomUUID().toString());
            Files.createDirectories(runDir);
            return runDir;
        } catch (IOException e) {
            throw new RuntimeException("Unable to create run directory.", e);
        }
    }
}
