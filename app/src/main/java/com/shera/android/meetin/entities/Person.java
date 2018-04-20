package com.shera.android.meetin.entities;



import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Person {

    private UUID id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private String country;
    private String city;
    private Set<Project> createdProjects = new HashSet<>();
    private Set<Project> subscribedProjects = new HashSet<>();
    private Set<Contribution> contributions = new HashSet<>();
    private Set<BaseNotification> notifications = new HashSet<>();
    private Set<Comment> comments = new HashSet<>();
    private Set<Person> followers = new HashSet<>();
    private Set<Person> followed = new HashSet<>();
    private Set<Message> createdMessages = new HashSet<>();
    private Set<Message> receivedMessages = new HashSet<>();

    /**
     * Constructs new person that represents user of the system.
     *
     * @param email email address
     * @param password password
     * @param name username
     */
    public Person(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.id = UUID.randomUUID();
    }

    protected Person() {

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Project> getCreatedProjects() {
        return createdProjects;
    }

    public void setCreatedProjects(Set<Project> createdProjects) {
        this.createdProjects = createdProjects;
    }

    public Set<Project> getSubscribedProjects() {
        return subscribedProjects;
    }

    public void setSubscribedProjects(Set<Project> subscribedProjects) {
        this.subscribedProjects = subscribedProjects;
    }

    public Set<Contribution> getContributions() {
        return contributions;
    }

    public void addContribution(Contribution contribution) {
        contributions.add(contribution);
        contribution.setContributor(this);
    }

    public void removeContribution(Contribution contribution) {
        contributions.remove(contribution);
        contribution.setContributor(null);
    }

    public void setContributions(Set<Contribution> contributions) {
        this.contributions = contributions;
    }

    public Set<BaseNotification> getNotifications() {
        return notifications;
    }

    public void addNotification(BaseNotification notification) {
        notifications.add(notification);
        notification.setReceiver(this);
    }

    public void removeNotification(BaseNotification notification) {
        notifications.remove(notification);
        notification.setReceiver(null);
    }

    public void setNotifications(Set<BaseNotification> notifications) {
        this.notifications = notifications;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setAuthor(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setAuthor(null);
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Person> getFollowers() {
        return followers;
    }

    public void addFollower(Person follower) {
        followers.add(follower);
        follower.addFollowed(this);
    }

    public void removeFollower(Person follower) {
        followers.remove(follower);
        follower.removeFollowed(this);
    }

    public void setFollowers(Set<Person> followers) {
        this.followers = followers;
    }

    public Set<Person> getFollowed() {
        return followed;
    }

    public void addFollowed(Person follow) {
        followed.add(follow);
        follow.addFollower(this);
    }

    public void removeFollowed(Person follow) {
        followed.add(follow);
        follow.removeFollower(this);
    }

    public void setFollowed(Set<Person> followed) {
        this.followed = followed;
    }

    public Set<Message> getCreatedMessages() {
        return createdMessages;
    }

    public void addCreatedMessage(Message message) {
        createdMessages.add(message);
        message.setSender(this);
    }

    public void removeCreatedMessage(Message message) {
        createdMessages.remove(message);
        message.setSender(null);
    }

    public void setCreatedMessages(Set<Message> createdMessages) {
        this.createdMessages = createdMessages;
    }

    public Set<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void addReceivedMessage(Message message) {
        receivedMessages.add(message);
        message.setReceiver(this);
    }

    public void removeReceivedMessage(Message message) {
        receivedMessages.remove(message);
        message.setReceiver(null);
    }

    public void setReceivedMessages(Set<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }

        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
