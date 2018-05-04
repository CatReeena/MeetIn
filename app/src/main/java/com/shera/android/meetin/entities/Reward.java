package com.shera.android.meetin.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import org.joda.money.Money;

public class Reward implements Serializable {

    private Long id;
    private Project project;
    private List<Contribution> contributions;
    @JsonProperty("minimal_contribution")
    private Money minimalContribution;
    private Integer limit;
    private String description;
    @JsonProperty("delivery_date")
    private String deliveryDate;
    @JsonProperty("shipping_location")
    private String shippingLocation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }

    public Money getMinimalContribution() {
        return minimalContribution;
    }

    public void setMinimalContribution(Money minimalContribution) {
        this.minimalContribution = minimalContribution;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getShippingLocation() {
        return shippingLocation;
    }

    public void setShippingLocation(String shippingLocation) {
        this.shippingLocation = shippingLocation;
    }
}
