package com.springarena.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springarena.dto.ChallengeDto;
import com.springarena.dto.ChallengeDto.ChallengeFile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChallengeService {

    @JsonIgnoreProperties(ignoreUnknown = true)
    private final ObjectMapper mapper = new ObjectMapper();
    
    private Path getChallengeRoot() {
        // Get the location of this class file, then navigate to the challenges directory
        // This works regardless of working directory
        try {
            Path classPath = Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
            // classPath is backend/target/classes
            Path backendDir = classPath.getParent().getParent().getParent(); // go up to backend
            return backendDir.resolve("challenges").normalize(); // go up to root, then to challenges
        } catch (Exception e) {
            // Fallback to the old method
            Path backendDir = Paths.get("").toAbsolutePath();
            return backendDir.getParent().resolve("challenges").normalize();
        }
    }

    public List<ChallengeDto> listChallenges() {
        Path challengeRoot = getChallengeRoot();
        if (Files.notExists(challengeRoot)) {
            return List.of();
        }

        try {
            List<ChallengeDto> output = Files.list(challengeRoot)
                    .filter(path -> Files.isDirectory(path))
                    .map(this::loadMetadata)
                    .flatMap(Optional::stream)
                    .collect(Collectors.toList());
            return output;
        } catch (IOException e) {
            throw new RuntimeException("Unable to list challenges.", e);
        }
    }

    public Optional<ChallengeDto> getChallenge(String challengeId) {
        Path challengeDir = getChallengeRoot().resolve(challengeId);
        if (Files.notExists(challengeDir) || !Files.isDirectory(challengeDir)) {
            return Optional.empty();
        }

        Optional<ChallengeDto> metadata = loadMetadata(challengeDir);
        if (metadata.isEmpty()) {
            return Optional.empty();
        }

        ChallengeDto dto = metadata.get();
        dto.setPrompt(readPrompt(challengeDir));
        dto.setFiles(loadStarterFiles(challengeDir));
        return Optional.of(dto);
    }

    private Optional<ChallengeDto> loadMetadata(Path challengeDir) {
        Path metadataPath = challengeDir.resolve("metadata.json");
        if (Files.notExists(metadataPath)) {
            return Optional.empty();
        }

        try {
            ChallengeDto dto = mapper.readValue(metadataPath.toFile(), ChallengeDto.class);
            dto.setStatus(dto.getStatus() == null ? "open" : dto.getStatus());
            return Optional.of(dto);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read metadata: " + metadataPath, e);
        }
    }

    private String readPrompt(Path challengeDir) {
        try {
            Path promptPath = challengeDir.resolve("prompt.md");
            if (Files.exists(promptPath)) {
                return Files.readString(promptPath);
            }
        } catch (IOException ignored) {
        }
        return "No prompt available.";
    }

    private List<ChallengeFile> loadStarterFiles(Path challengeDir) {
        Path starterRoot = challengeDir.resolve("starter-files");
        if (Files.notExists(starterRoot)) {
            return List.of();
        }

        try {
            List<Path> paths = Files.walk(starterRoot)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());

            List<ChallengeFile> files = new ArrayList<>();
            for (Path path : paths) {
                String relative = starterRoot.relativize(path).toString().replace('\\', '/');
                ChallengeFile file = new ChallengeFile();
                file.setPath(relative);
                file.setContent(Files.readString(path));
                files.add(file);
            }
            return files;
        } catch (IOException e) {
            return List.of();
        }
    }
}
