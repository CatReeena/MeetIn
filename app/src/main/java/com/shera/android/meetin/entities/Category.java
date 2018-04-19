package com.shera.android.meetin.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


public class Category {

    private UUID id;
    private Set<Project> projects;
    private Category parent;
    private Set<Category> children = new HashSet<>();
    private String name;
    private String description;
    public Category(String name) {
        this(name, null, null);
    }
    public Category(String name, String description) {
        this(name, description, null);
    }
    public Category(String name, Category parent) {
        this(name, null, parent);
    }

    /**
     * Constructs new category for projects.
     *
     * @param name name of the category
     * @param description short description
     * @param parent parent category
     */
    public Category(String name, String description, Category parent) {
        this.name = name;
        this.description = description;
        this.parent = parent;
        this.id = UUID.randomUUID();
    }

    protected Category() {

    }

    public UUID getId() {
        return id;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public void addChild(Category category) {
        children.add(category);
        category.setParent(this);
    }

    public void removeChild(Category category) {
        children.remove(category);
        category.setParent(null);
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }

        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}