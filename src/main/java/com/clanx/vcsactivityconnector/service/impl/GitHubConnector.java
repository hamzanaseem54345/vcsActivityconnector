package com.clanx.vcsactivityconnector.service.impl;

import com.clanx.vcsactivityconnector.client.GitHubClient;
import com.clanx.vcsactivityconnector.dto.CommitDto;
import com.clanx.vcsactivityconnector.dto.RepositoryDto;
import com.clanx.vcsactivityconnector.service.VcsConnector;
import feign.FeignException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("github")
public class GitHubConnector implements VcsConnector {
    private final GitHubClient gitHubClient;
    private static final Logger log = LoggerFactory.getLogger(GitHubConnector.class);

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
    public List<CommitDto> getRecentCommits(String token, String repoName, int limit,int pageNo) {
        List<RepositoryDto> repos = gitHubClient.getUserRepositories("Bearer " + token);

        RepositoryDto repo = repos.stream()
                .filter(r -> r.name().equalsIgnoreCase(repoName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Repository not found: " + repoName));

        String owner = repo.owner().login();

        return gitHubClient.getRecentCommits("Bearer " + token, owner, repoName, limit,pageNo);
    }

    public List<RepositoryDto> getAllRepositories(String token, String username) {
        List<RepositoryDto> allRepos = new ArrayList<>();
        int page = 1;

        while (true) {
            List<RepositoryDto> pageRepos = gitHubClient.getReposByUsername("Bearer " + token, username, 100, page);
            if (pageRepos == null || pageRepos.isEmpty()) break;
            allRepos.addAll(pageRepos);
            page++;
        }

        return allRepos;
    }

    public List<CommitDto> getTop20Commits(String token, String owner, String repoName) {
        try {
            return gitHubClient.getRecentCommits("Bearer " + token, owner, repoName, 20, 1);
        } catch (FeignException.TooManyRequests e) {
            throw new RuntimeException("GitHub API rate limit exceeded");
        } catch (FeignException e) {
            throw new RuntimeException("Error fetching commits for " + repoName + ": " + e.getMessage());
        }
    }

    public List<CommitDto> getCommitsByPage(String token, String owner, String repo, int limit, int page) {
        try {
            return gitHubClient.getCommits("Bearer " + token, owner, repo, limit, page);
        } catch (FeignException.TooManyRequests e) {
            log.warn("Rate limit hit: {}", e.getMessage());
            throw new RuntimeException("GitHub API rate limit exceeded");
        } catch (FeignException e) {
            log.error("GitHub API error: {}", e.getMessage());
            throw new RuntimeException("Error while fetching commits");
        }
    }

    @Override
    public List<CommitDto> getCommits(String token, String repo, String owner, int limit, int pageNumber) {
        if (owner == null || owner.isBlank()) {
            List<RepositoryDto> repos = gitHubClient.getUserRepositories("Bearer " + token);
            owner = repos.stream()
                    .filter(r -> r.name().equalsIgnoreCase(repo))
                    .map(r -> r.owner().login())
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Owner not found for repo: " + repo));
        }

        return gitHubClient.getCommits("Bearer " + token, owner, repo, limit, pageNumber);
    }

}
