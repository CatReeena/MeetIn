package com.shera.android.meetin;

import android.net.Uri;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.shera.android.meetin.entities.Project;

import org.joda.money.Money;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by Shera on 17.04.2018.
 */

public class ProjectFetch {

    private static final Uri ENDPOINT = Uri.parse("http://192.168.1.93:8080/");
    private static final String STARTER_PATH = "v0/projects/";
    private static final String TAG = "ProjectFetch";

    private String buildUrl(TaskFilters taskFilters) {
        Uri.Builder uriBuilder = ENDPOINT.buildUpon();
        if (taskFilters.getCategory() != null) {
            uriBuilder.path(String.valueOf(taskFilters.getCategory()));
        } else {
            uriBuilder.path(String.valueOf(STARTER_PATH));
        }
        if (taskFilters.getId() != null) {
            if (taskFilters.getProjectPosition() == TaskFilters.ProjectPosition.FIRST_PROJECT) {
                uriBuilder.appendQueryParameter("firstProjectId",
                        String.valueOf(taskFilters.getId()));
            } else {
                uriBuilder.appendQueryParameter("lastProjectId",
                        String.valueOf(taskFilters.getId()));
            }
        }
        if (taskFilters.isEndDateFilterOn()) {
            uriBuilder.appendQueryParameter("isEndDateFilterOn", "true");
        }
        if (taskFilters.isFollowedFilterOn()) {
            uriBuilder.appendQueryParameter("isFollowedFilterOn", "true");
        }
        if (taskFilters.isMostFundedFilterOn()) {
            uriBuilder.appendQueryParameter("isMostFundedFilterOn", "true");
        }
        if (taskFilters.isNewestFilterOn()) {
            uriBuilder.appendQueryParameter("isNewestFilterOn", "true");
        }
        if (taskFilters.isPopularFilterOn()) {
            uriBuilder.appendQueryParameter("isPopularFilterOn", "true");
        }
        if (taskFilters.isSuccessfulFilterOn()) {
            uriBuilder.appendQueryParameter("isSuccessfulFilterOn", "true");
        }
        return uriBuilder.build().toString();
    }

    public List<Project> downloadProjects(TaskFilters taskFilters) {
        String url = buildUrl(taskFilters);
        try {
            Page projects = createTemplate().getForObject(url, Page.class);
            return projects.content;
        } catch (RestClientException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return null;
    }

    public RestTemplate createTemplate()
    {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JodaModule());

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Money.class, new JodaMoneyDeserializer());
        objectMapper.registerModule(module);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(converter);
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }

    public Project downloadProjectById(Long id)
    {
        String url = ENDPOINT.buildUpon()
                .path(STARTER_PATH)
                .appendPath(String.valueOf(id))
                .build()
                .toString();
        try {
            Project project = createTemplate().getForObject(url, Project.class);
            return project;
        } catch (RestClientException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return null;
    }
}
