package com.clanx.vcsactivityconnector.client;


import com.clanx.vcsactivityconnector.dto.CommitDto;
import com.clanx.vcsactivityconnector.dto.RepositoryDto;
import com.clanx.vcsactivityconnector.feignConfig.GitHubFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "githubClient",
        url = "https://api.github.com",
        configuration = GitHubFeignConfig.class
)
public interface GitHubClient {

    @GetMapping("/user/repos")
    List<RepositoryDto> getUserRepositories(@RequestHeader("Authorization") String token);

    @GetMapping("/repos/{owner}/{repo}/commits")
    List<CommitDto> getRecentCommits(
            @RequestHeader("Authorization") String token,
            @PathVariable("owner") String owner,
            @PathVariable("repo") String repo,
            @RequestParam(value = "per_page", defaultValue = "20") int limit
    );
}