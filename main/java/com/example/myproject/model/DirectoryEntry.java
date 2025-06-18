package com.example.myproject.model;

public class DirectoryEntry {
    private String name;
    private String path;
    private boolean isDirectory;
    private long revision;

    public DirectoryEntry(String name, String path, boolean isDirectory, long revision) {
        this.name = name;
        this.path = path;
        this.isDirectory = isDirectory;
        this.revision = revision;
    }

    public DirectoryEntry() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public long getRevision() {
        return revision;
    }

    public void setRevision(long revision) {
        this.revision = revision;
    }

}