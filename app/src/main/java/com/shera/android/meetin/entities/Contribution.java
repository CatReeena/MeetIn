package com.shera.android.meetin.entities;


import org.joda.money.Money;
import org.threeten.bp.LocalDateTime;

import java.util.Objects;
import java.util.UUID;


public class Contribution {

    private UUID id;
    private Person contributor;
    private Project project;
    private LocalDateTime dateTime;
    private Money money;
    private Reward reward;

    /**
     * Constructs new contribution to the project.
     *
     * @param contributor person who made the contribution
     * @param project project to which the contribution was made
     * @param dateTime creation date and time
     * @param money contributed currency and amount
     * @param reward reward for this contribution
     */
    public Contribution(Person contributor, Project project, LocalDateTime dateTime,
                        Money money, Reward reward) {
        this.contributor = contributor;
        this.project = project;
        this.dateTime = dateTime;
        this.money = money;
        this.reward = reward;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contribution)) {
            return false;
        }

        Contribution that = (Contribution) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
