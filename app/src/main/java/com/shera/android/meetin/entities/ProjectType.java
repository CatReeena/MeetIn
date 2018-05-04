package com.shera.android.meetin.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public enum ProjectType implements Serializable {
    @JsonProperty("in_creation")
    IN_CREATION,

    @JsonProperty("active")
    ACTIVE
}
