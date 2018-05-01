package com.shera.android.meetin;

import com.shera.android.meetin.entities.Category;

/**
 * Created by Shera on 17.04.2018.
 */

public class ProjectsFilters {
    private Long id;
    Position position;
    private Category category;
    private boolean followedFilterOn = false;
    private boolean successfulFilterOn = false;
    private boolean popularFilterOn = false;
    private boolean newestFilterOn = false;
    private boolean mostFundedFilterOn = false;
    private boolean endDateFilterOn = false;

    public ProjectsFilters(Position position){
        this.position = position;
    }

    public ProjectsFilters(Long id,
                           Position position,
                           Category category,
                           boolean followedFilterOn,
                           boolean successfulFilterOn,
                           boolean popularFilterOn,
                           boolean newestFilterOn,
                           boolean mostFundedFilterOn,
                           boolean endDateFilterOn) {
        this.id = id;
        this.position = position;
        this.category = category;
        this.followedFilterOn = followedFilterOn;
        this.successfulFilterOn = successfulFilterOn;
        this.popularFilterOn = popularFilterOn;
        this.newestFilterOn = newestFilterOn;
        this.mostFundedFilterOn = mostFundedFilterOn;
        this.endDateFilterOn = endDateFilterOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


}

