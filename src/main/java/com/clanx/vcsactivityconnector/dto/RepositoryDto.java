package com.clanx.vcsactivityconnector.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public record RepositoryDto(String name, Owner owner) {}

