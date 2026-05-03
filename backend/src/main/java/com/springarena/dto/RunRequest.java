package com.springarena.dto;

import java.util.List;

public class RunRequest {
    private List<FilePayload> files;

    public List<FilePayload> getFiles() {
        return files;
    }

    public void setFiles(List<FilePayload> files) {
        this.files = files;
    }
}
