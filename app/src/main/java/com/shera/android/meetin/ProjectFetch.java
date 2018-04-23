package com.shera.android.meetin;

import android.net.Uri;
import android.util.Log;

import com.shera.android.meetin.entities.Project;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by Shera on 17.04.2018.
 */

public class ProjectFetch {

    private static final Uri ENDPOINT = Uri.parse("http://192.168.1.93:8080/");
    private static final String STARTER_PATH = "projects";
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
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            Page projects = restTemplate.getForObject(url, Page.class);
            return projects.content;
        } catch (RestClientException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return null;
    }

    public Project downloadProjectById(UUID id)
    {
       // ---------------------------------------------------IMPLEMENT--------------------------------
        return new Project();
    }
}
