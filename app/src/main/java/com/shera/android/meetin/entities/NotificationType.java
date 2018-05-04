package com.shera.android.meetin.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public enum NotificationType implements Serializable {
    @JsonProperty("base")
    BASE,

    @JsonProperty("contribution")
    CONTRIBUTION,

    @JsonProperty("person")
    PERSON,

    @JsonProperty("update")
    UPDATE
}
