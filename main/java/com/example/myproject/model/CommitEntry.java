package com.example.myproject.model;

import java.time.LocalDateTime;

public class CommitEntry {
    private String reponame;
    private long revision;
    private String author;
    private LocalDateTime date;
    private String message;
    private String changedFiles;

    public String getReponame() {
        return reponame;
    }

    public CommitEntry(long revision, String author, LocalDateTime date, String message, String changedFiles) {
        this.revision = revision;
        this.author = author;
        this.date = date;
        this.message = message;
        this.changedFiles = changedFiles;
        this.reponame = changedFiles.split("/")[2];
    }



    public CommitEntry() {
    }

    public long getRevision() {
        return revision;
    }

    public void setRevision(long revision) {
        this.revision = revision;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChangedFiles() {
        return changedFiles;
    }

    public void setChangedFiles(String changedFiles) {
        this.changedFiles = changedFiles;
    }


}