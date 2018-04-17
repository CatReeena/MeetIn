package com.shera.android.meetin;

import android.net.Uri;

/**
 * Created by Shera on 17.04.2018.
 */

public class ProjectFetch {

    private static final Uri ENDPOINT = Uri
            .parse("https://.....")
            .buildUpon()
            .appendQueryParameter("format", "json")
            .build();

    private String buildUrl() {
        Uri.Builder uriBuilder = ENDPOINT.buildUpon()
                .appendQueryParameter("method", method)
                .appendQueryParameter("page",String.valueOf(page));
        if (method.equals(SEARCH_METHOD)) {
            uriBuilder.appendQueryParameter("text", query);
        }
        return uriBuilder.build().toString();
    }



}
