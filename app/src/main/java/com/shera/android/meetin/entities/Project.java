package com.shera.android.meetin.entities;






import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.threeten.bp.LocalDateTime;


public class Project {


    private UUID id;
    private String name ="";
    private String description ="";
    private Location location;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Money fundingGoal;
    private List<String> videoLinks = new ArrayList<>();
    private List<String> imageLinks = new ArrayList<>();
    private Set<Person> owners = new HashSet<>();
    private Set<Person> subscribers = new HashSet<>();
    private Set<Contribution> contributions = new HashSet<>();
    private Set<Comment> comments = new HashSet<>();
    private Set<Faq> faqs = new HashSet<>();
    private Set<Category> categories = new HashSet<>();
    private Set<Update> updates = new HashSet<>();
    private Set<Reward> rewards = new HashSet<>();

    /**
     * Constructs new project.
     *
     * @param name name of the project
     * @param description description
     * @param fundingGoal funding goal of the project
     * @param owners owners of the project
     */
    public Project(String name, String description, Money fundingGoal, Person... owners) {
        this.name = name;
        this.description = description;
        this.fundingGoal = fundingGoal;
        for(Person owner : owners) {
            addOwner(owner);
        }
        this.id = UUID.randomUUID();
    }

    protected Project() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public List<String> getVideoLinks() {
        return videoLinks;
    }

    public void addVideoLink(String link) {
        videoLinks.add(link);
    }

    public void removeVideoLink(String link) {
        videoLinks.remove(link);
    }

    public void setVideoLinks(List<String> videoLinks) {
        this.videoLinks = videoLinks;
    }

    public List<String> getImageLinks() {
        return imageLinks;
    }

    public void addImageLink(String link) {
        imageLinks.add(link);
    }

    public void removeImageLink(String link) {
        imageLinks.remove(link);
    }

    public void setImageLinks(List<String> imageLinks) {
        this.imageLinks = imageLinks;
    }

    public Set<Person> getOwners() {
        return owners;
    }

    public void addOwner(Person owner) {
        owners.add(owner);
        owner.getCreatedProjects().add(this);
    }

    public void removeOwner(Person owner) {
        owners.remove(owner);
        owner.getCreatedProjects().remove(this);
    }

    public void setOwners(Set<Person> owners) {
        this.owners = owners;
    }

    public Set<Person> getSubscribers() {
        return subscribers;
    }

    public void addSubscriber(Person subscriber) {
        subscribers.add(subscriber);
        subscriber.getSubscribedProjects().add(this);
    }

    public void removeSubscriber(Person subscriber) {
        subscribers.remove(subscriber);
        subscriber.getSubscribedProjects().remove(this);
    }

    public void setSubscribers(Set<Person> subscribers) {
        this.subscribers = subscribers;
    }

    public Set<Contribution> getContributions() {
        return contributions;
    }

    public void addContribution(Contribution contribution) {
        contributions.add(contribution);
        contribution.setProject(this);
    }

    public void removeContribution(Contribution contribution) {
        contributions.remove(contribution);
        contribution.setProject(null);
    }

    public void setContributions(Set<Contribution> contributions) {
        this.contributions = contributions;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setProject(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setProject(null);
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Faq> getFaqs() {
        return faqs;
    }

    public void addFaq(Faq faq) {
        faqs.add(faq);
        faq.setProject(this);
    }

    public void removeFaq(Faq faq) {
        faqs.remove(faq);
        faq.setProject(null);
    }

    public void setFaqs(Set<Faq> faqs) {
        this.faqs = faqs;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        categories.add(category);
        category.getProjects().add(this);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
        category.getProjects().remove(this);
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Update> getUpdates() {
        return updates;
    }

    public void addUpdate(Update update) {
        updates.add(update);
        update.setProject(this);
    }

    public void removeUpdate(Update update) {
        updates.remove(update);
        update.setProject(null);
    }

    public void setUpdates(Set<Update> updates) {
        this.updates = updates;
    }

    public Set<Reward> getRewards() {
        return rewards;
    }

    public void addReward(Reward reward) {
        rewards.add(reward);
        reward.setProject(this);
    }

    public void removeReward(Reward reward) {
        rewards.remove(reward);
        reward.setProject(null);
    }

    public void setRewards(Set<Reward> rewards) {
        this.rewards = rewards;
    }



    public void countProgress()
    {
        CurrencyUnit currency = fundingGoal.getCurrencyUnit();
        Money contributedSum = Money.zero(currency);
        for (Contribution contribution:
             contributions) {
            if(contribution != null) {
                if (contribution.getMoney() != null) {
                    contributedSum = contributedSum.plus(contribution.getMoney());
                }
            }
        }
        int progress = contributedSum
                .dividedBy(fundingGoal.getAmount(), RoundingMode.HALF_UP)
                .getAmountMinorInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }

        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
