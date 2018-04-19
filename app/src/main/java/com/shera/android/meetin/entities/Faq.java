package com.shera.android.meetin.entities;

import java.util.Objects;
import java.util.UUID;

public class Faq {

    private UUID id;
    private Project project;
    private String question;
    private String answer;

    /**
     * Constructs new frequently asked question with answer.
     *
     * @param project project to which faq belongs
     * @param question frequently asked question
     * @param answer answer to the question
     */
    public Faq(Project project, String question, String answer) {
        this.project = project;
        this.question = question;
        this.answer = answer;
        this.id = UUID.randomUUID();
    }

    protected Faq() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Faq)) {
            return false;
        }

        Faq faq = (Faq) o;
        return Objects.equals(id, faq.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
