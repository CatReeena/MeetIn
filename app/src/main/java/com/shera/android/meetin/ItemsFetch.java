package com.shera.android.meetin;

import android.net.Uri;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.shera.android.meetin.entities.Comment;
import com.shera.android.meetin.entities.Project;

import org.joda.money.Money;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shera on 17.04.2018.
 */

public class ItemsFetch {

    private static final Uri ENDPOINT = Uri.parse("http://192.168.1.93:8080/");
    private static final String STARTER_PATH = "v0/projects/";
    private static final String COMMENTS_PATH = "comments";
    private static final String TAG = "ItemsFetch";

    private String buildProjectUrl(ProjectsFilters projectsFilters) {
        Uri.Builder uriBuilder = ENDPOINT.buildUpon();
        if (projectsFilters.getCategory() != null) {
            uriBuilder.path(String.valueOf(projectsFilters.getCategory()));
        } else {
            uriBuilder.path(String.valueOf(STARTER_PATH));
        }
        if (projectsFilters.getId() != null) {
            if (projectsFilters.getPosition() == Position.FIRST) {
                uriBuilder.appendQueryParameter("firstProjectId",
                        String.valueOf(projectsFilters.getId()));
            } else {
                uriBuilder.appendQueryParameter("lastProjectId",
                        String.valueOf(projectsFilters.getId()));
            }
        }
        if (projectsFilters.isEndDateFilterOn()) {
            uriBuilder.appendQueryParameter("isEndDateFilterOn", "true");
        }
        if (projectsFilters.isFollowedFilterOn()) {
            uriBuilder.appendQueryParameter("isFollowedFilterOn", "true");
        }
        if (projectsFilters.isMostFundedFilterOn()) {
            uriBuilder.appendQueryParameter("isMostFundedFilterOn", "true");
        }
        if (projectsFilters.isNewestFilterOn()) {
            uriBuilder.appendQueryParameter("isNewestFilterOn", "true");
        }
        if (projectsFilters.isPopularFilterOn()) {
            uriBuilder.appendQueryParameter("isPopularFilterOn", "true");
        }
        if (projectsFilters.isSuccessfulFilterOn()) {
            uriBuilder.appendQueryParameter("isSuccessfulFilterOn", "true");
        }
        return uriBuilder.build().toString();
    }

    public List<Project> downloadProjects(ProjectsFilters projectsFilters) {
        String url = buildProjectUrl(projectsFilters);
        try {
            PageProjects projects = createTemplate().getForObject(url, PageProjects.class);
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

    public List<Comment> downloadComments(CommentsFilters commentsFilters)
    {
        Uri.Builder uriBuilder = ENDPOINT.buildUpon()
                .path(STARTER_PATH)
                .appendPath(String.valueOf(commentsFilters.getProjectId()))
                .appendPath(COMMENTS_PATH);
        try {
            switch (commentsFilters.getPosition())
            {
                case FIRST:
                uriBuilder.appendQueryParameter("firstCommentId",
                        commentsFilters.getCommentId().toString());
                break;
                case LAST:
                    uriBuilder.appendQueryParameter("lastCommentId",
                            commentsFilters.getCommentId().toString());
                    break;
                case FRESH:
                    break;
            }
            String url = uriBuilder.build().toString();
            PageComments comments = createTemplate().getForObject(url, PageComments.class);
            return comments.content;
        } catch (RestClientException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return null;
    }
}
