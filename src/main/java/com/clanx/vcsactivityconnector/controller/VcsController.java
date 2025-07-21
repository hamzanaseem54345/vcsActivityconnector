package com.clanx.vcsactivityconnector.controller;


import com.clanx.vcsactivityconnector.dto.CommitDto;
import com.clanx.vcsactivityconnector.dto.RepositoryDto;
import com.clanx.vcsactivityconnector.factory.ConnectorFactory;
import com.clanx.vcsactivityconnector.service.VcsConnector;
import com.clanx.vcsactivityconnector.service.impl.GitHubConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vcs")
public class VcsController {

    private final ConnectorFactory connectorFactory;

    @Autowired
    public VcsController(ConnectorFactory connectorFactory) {
        this.connectorFactory = connectorFactory;
    }

    @GetMapping("/repos")
    public List<String> listRepos(
            @RequestParam String provider,
            @RequestHeader("Authorization") String token
    ) {
        String cleanToken = extractToken(token);
        VcsConnector connector = connectorFactory.getConnector(provider);
        return connector.listRepositories(cleanToken);
    }

    @GetMapping("/commits")
    public List<CommitDto> getCommits(
            @RequestParam String provider,
            @RequestHeader("Authorization") String token,
            @RequestParam String repo,
            @RequestParam(required = false) String owner,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "1") int pageNumber
    ) {
        String cleanToken = extractToken(token);
        VcsConnector connector = connectorFactory.getConnector(provider);
        return connector.getCommits(cleanToken, repo, owner, limit, pageNumber);
    }


    private String extractToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }
        return header.substring(7);
    }
}