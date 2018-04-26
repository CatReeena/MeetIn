package com.shera.android.meetin.entities;


import org.joda.money.Money;
import org.joda.time.LocalDateTime;


public class Contribution {

    private Long id;
    private Person contributor;
    private Project project;
    private LocalDateTime dateTime;
    private Money money;
    private Reward reward;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getContributor() {
        return contributor;
    }

    public void setContributor(Person contributor) {
        this.contributor = contributor;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }
}
