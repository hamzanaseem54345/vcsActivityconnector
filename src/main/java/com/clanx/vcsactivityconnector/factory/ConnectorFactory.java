package com.clanx.vcsactivityconnector.factory;

import com.clanx.vcsactivityconnector.client.GitHubClient;
import com.clanx.vcsactivityconnector.service.impl.GitHubConnector;
import com.clanx.vcsactivityconnector.service.VcsConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ConnectorFactory {

    private final Map<String, VcsConnector> connectorMap;

    @Autowired
    public ConnectorFactory(List<VcsConnector> connectors) {
        this.connectorMap = connectors.stream()
                .collect(Collectors.toMap(
                        c -> c.getClass().getSimpleName().replace("Connector", "").toLowerCase(),
                        Function.identity()
                ));
    }

    public VcsConnector getConnector(String type) {
        return Optional.ofNullable(connectorMap.get(type.toLowerCase()))
                .orElseThrow(() -> new IllegalArgumentException("Unsupported VCS: " + type));
    }
}
