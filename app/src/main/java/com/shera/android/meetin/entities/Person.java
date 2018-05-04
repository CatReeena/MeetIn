package com.shera.android.meetin.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Person implements Serializable {

    private Long id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private String country;
    private String city;
    @JsonProperty("image_link")
    private String personImageLink;

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

    public String getPersonImageLink() {
        return personImageLink;
    }

    public void setPersonImageLink(String personImageLink) {
        this.personImageLink = personImageLink;
    }
}
