package com.springarena.service;

import com.springarena.dto.ChallengeDto;
import com.springarena.dto.FilePayload;
import com.springarena.dto.RunRequest;
import com.springarena.dto.RunResponse;
import com.springarena.util.FileUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class RunnerService {

    private final ChallengeService challengeService;
    private final WorkspaceService workspaceService;

    public RunnerService(ChallengeService challengeService, WorkspaceService workspaceService) {
        this.challengeService = challengeService;
        this.workspaceService = workspaceService;
    }

    private List<String> getMavenCommand(Path runDir) {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            Path mvnwCmd = runDir.resolve("mvnw.cmd");
            List<String> result = List.of("cmd", "/c", mvnwCmd.toString(), "-q", "test");
            return result;
        } else {
            return List.of("./mvnw", "-q", "test");
        }
    }

    public RunResponse runChallenge(String challengeId, RunRequest request) {
        Optional<ChallengeDto> challenge = challengeService.getChallenge(challengeId);
        if (challenge.isEmpty()) {
            return new RunResponse(false, "Challenge not found: " + challengeId);
        }

        Path runDir = workspaceService.createRunDirectory(challengeId);
        Path backendDir = Paths.get("").toAbsolutePath().resolve("backend").normalize();
        Path projectRoot = backendDir.getParent();
        Path templatePath = projectRoot.resolve("templates").resolve("rest-template").normalize();
        Path challengePath = projectRoot.resolve("challenges").resolve(challengeId).normalize();

        try {
            FileUtils.copyDirectory(templatePath, runDir);
            FileUtils.copyDirectory(challengePath.resolve("starter-files"), runDir);

            // Set random port to avoid conflict with main application
            Path appYml = runDir.resolve("src/main/resources/application.yml");
            if (Files.exists(appYml)) {
                String content = Files.readString(appYml);
                content = content.replace("server:\n  port: 8080", "server:\n  port: 0");
                Files.writeString(appYml, content);
            }

            if (request.getFiles() != null) {
                for (FilePayload payload : request.getFiles()) {
                    Path target = runDir.resolve(payload.getPath());
                    FileUtils.writeFile(target, payload.getContent());
                }
            }

            Path testRoot = runDir.resolve("src/test/java");
            Path challengeTests = challengePath.resolve("tests");
            if (java.nio.file.Files.exists(challengeTests)) {
                FileUtils.copyDirectory(challengeTests, testRoot);
            }

            List<String> output = FileUtils.runProcess(runDir, getMavenCommand(runDir));
            boolean success = output.stream().anyMatch(line -> line.contains("Process exited with code 0"));
            return new RunResponse(success, String.join("\n", output));
        } catch (IOException e) {
            return new RunResponse(false, "Execution failed: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new RunResponse(false, "Execution interrupted.");
        }
    }
}
