package com.shera.android.meetin;

import java.util.UUID;

/**
 * Created by Shera on 17.04.2018.
 */

public class TaskFilters {
    private UUID id;
    private Category category;
    private boolean followedFilterOn = false;
    private boolean successfulFilterOn = false;
    private boolean popularFilterOn = false;
    private boolean newestFilterOn = false;
    private boolean mostFundedFilterOn = false;
    private boolean endDateFilterOn = false;


    TaskFilters(Category category){
        this.category = category;
    }

    public TaskFilters(UUID id,
                       Category category,
                       boolean followedFilterOn,
                       boolean successfulFilterOn,
                       boolean popularFilterOn,
                       boolean newestFilterOn,
                       boolean mostFundedFilterOn,
                       boolean endDateFilterOn) {
        this.id = id;
        this.category = category;
        this.followedFilterOn = followedFilterOn;
        this.successfulFilterOn = successfulFilterOn;
        this.popularFilterOn = popularFilterOn;
        this.newestFilterOn = newestFilterOn;
        this.mostFundedFilterOn = mostFundedFilterOn;
        this.endDateFilterOn = endDateFilterOn;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isFollowedFilterOn() {
        return followedFilterOn;
    }

    public void setFollowedFilterOn(boolean followedFilterOn) {
        this.followedFilterOn = followedFilterOn;
    }

    public boolean isSuccessfulFilterOn() {
        return successfulFilterOn;
    }

    public void setSuccessfulFilterOn(boolean successfulFilterOn) {
        this.successfulFilterOn = successfulFilterOn;
    }

    public boolean isPopularFilterOn() {
        return popularFilterOn;
    }

    public void setPopularFilterOn(boolean popularFilterOn) {
        this.popularFilterOn = popularFilterOn;
    }

    public boolean isNewestFilterOn() {
        return newestFilterOn;
    }

    public void setNewestFilterOn(boolean newestFilterOn) {
        this.newestFilterOn = newestFilterOn;
    }

    public boolean isMostFundedFilterOn() {
        return mostFundedFilterOn;
    }

    public void setMostFundedFilterOn(boolean mostFundedFilterOn) {
        this.mostFundedFilterOn = mostFundedFilterOn;
    }

    public boolean isEndDateFilterOn() {
        return endDateFilterOn;
    }

    public void setEndDateFilterOn(boolean endDateFilterOn) {
        this.endDateFilterOn = endDateFilterOn;
    }
}

