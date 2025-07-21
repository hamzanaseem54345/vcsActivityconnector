package com.clanx.vcsactivityconnector.service;

import com.clanx.vcsactivityconnector.dto.CommitDto;

import java.util.List;

public interface VcsConnector {
    List<String> listRepositories(String token);
    List<CommitDto> getRecentCommits(String token,String repoName, int limit);
}
