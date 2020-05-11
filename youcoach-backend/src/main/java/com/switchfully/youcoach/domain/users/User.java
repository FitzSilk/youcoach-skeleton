package com.switchfully.youcoach.domain.users;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class User {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;


    public User() {
    }

    public User(String firstName, String lastName, String email) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(UUID id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
