package com.clanx.vcsactivityconnector.service.impl;

import com.clanx.vcsactivityconnector.client.GitHubClient;
import com.clanx.vcsactivityconnector.dto.CommitDto;
import com.clanx.vcsactivityconnector.dto.RepositoryDto;
import com.clanx.vcsactivityconnector.service.VcsConnector;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("github")
public class GitHubConnector implements VcsConnector {
    private final GitHubClient gitHubClient;

    public GitHubConnector(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    @Override
    public List<String> listRepositories(String token) {
        return gitHubClient.getUserRepositories("Bearer " + token)
                .stream()
                .map(RepositoryDto::name)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommitDto> getRecentCommits(String token, String repoName, int limit) {
        List<RepositoryDto> repos = gitHubClient.getUserRepositories("Bearer " + token);

        RepositoryDto repo = repos.stream()
                .filter(r -> r.name().equalsIgnoreCase(repoName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Repository not found: " + repoName));

        String owner = repo.owner().login();

        return gitHubClient.getRecentCommits("Bearer " + token, owner, repoName, limit);
    }
}
