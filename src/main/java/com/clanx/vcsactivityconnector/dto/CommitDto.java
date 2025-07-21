package com.clanx.vcsactivityconnector.dto;

public class CommitDto {
    private String sha;
    private CommitAuthor author;
    private String message;
    private String timestamp;

    public String getSha() { return sha; }
    public void setSha(String sha) { this.sha = sha; }

    public CommitAuthor getAuthor() { return author; }
    public void setAuthor(CommitAuthor author) { this.author = author; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

}
