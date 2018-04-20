package com.shera.android.meetin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import com.shera.android.meetin.entities.Project;

import java.util.List;

/**
 * Created by Shera on 20.04.2018.
 */

//@JsonIgnoreProperties({"pageable","last", "totalPages", "totalElements"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Page{


    List<Project> content;
}
