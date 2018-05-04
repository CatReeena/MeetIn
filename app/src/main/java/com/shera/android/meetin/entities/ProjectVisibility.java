package com.shera.android.meetin.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public enum ProjectVisibility implements Serializable {
    @JsonProperty("private")
    PRIVATE,

    @JsonProperty("link_only")
    LINK_ONLY,

    @JsonProperty("public")
    PUBLIC
}
