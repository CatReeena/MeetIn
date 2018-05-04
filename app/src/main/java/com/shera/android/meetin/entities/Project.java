
package com.shera.android.meetin.entities;


import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.LocalDateTime;

import java.io.Serializable;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


public class Project implements Serializable {

    private Long id;
    private String name;
    @JsonProperty("short_description")
    private String shortDescription;
    private String description;
    private Location location;
    @JsonProperty("starts")
    private LocalDateTime startDateTime;
    @JsonProperty("ends")
    private LocalDateTime endDateTime;
    @JsonProperty("funding_goal")
    private Money fundingGoal;
    @JsonProperty("raised")
    private Money raisedMoney;
    @JsonProperty("project_image")
    private String projectImageLink;
    private ProjectType type;
    private ProjectVisibility visibility;
    @JsonProperty("gallery_videos")
    private List<String> videoLinks = new ArrayList<>();
    @JsonProperty("gallery_images")
    private List<String> imageLinks = new ArrayList<>();
    private List<Person> owners = new ArrayList<>();
    private List<Person> subscribers = new ArrayList<>();
    private List<Contribution> contributions = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<Faq> faqs = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private List<Update> updates = new ArrayList<>();
    private List<Reward> rewards = new ArrayList<>();

    public Project() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Money getFundingGoal() {
        return fundingGoal;
    }

    public void setFundingGoal(Money fundingGoal) {
        this.fundingGoal = fundingGoal;
    }

    public Money getRaisedMoney() {
        return raisedMoney;
    }

    public void setRaisedMoney(Money raisedMoney) {
        this.raisedMoney = raisedMoney;
    }

    public String getProjectImageLink() {
        return projectImageLink;
    }

    public void setProjectImageLink(String projectImageLink) {
        this.projectImageLink = projectImageLink;
    }

    public List<String> getVideoLinks() {
        return videoLinks;
    }

    public void setVideoLinks(List<String> videoLinks) {
        this.videoLinks = videoLinks;
    }

    public List<String> getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(List<String> imageLinks) {
        this.imageLinks = imageLinks;
    }

    public List<Person> getOwners() {
        return owners;
    }

    public void setOwners(List<Person> owners) {
        this.owners = owners;
    }

    public List<Person> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Person> subscribers) {
        this.subscribers = subscribers;
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Faq> getFaqs() {
        return faqs;
    }

    public void setFaqs(List<Faq> faqs) {
        this.faqs = faqs;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Update> getUpdates() {
        return updates;
    }

    public void setUpdates(List<Update> updates) {
        this.updates = updates;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public ProjectType getType() {
        return type;
    }

    public void setType(ProjectType type) {
        this.type = type;
    }

    public ProjectVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(ProjectVisibility visibility) {
        this.visibility = visibility;
    }

    public int getProgressPercent() {
        int progress = 0;
        final int percent = 100;
        if (fundingGoal != null && raisedMoney != null) {
            CurrencyUnit currency = fundingGoal.getCurrencyUnit();
            if (fundingGoal.isGreaterThan(Money.zero(currency))) {
                progress = raisedMoney
                        .dividedBy(fundingGoal.getAmount(), RoundingMode.HALF_UP)
                        .getAmountMinorInt();
            }
        }
        return progress * percent;
    }
}

