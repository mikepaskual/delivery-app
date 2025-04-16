package com.mikepaskual.delivery.user.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user_entity")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String email;
    private String password;
    private UserRole role;

    public User() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder<S extends Builder> {

        private Long id;
        private String username;
        private String email;
        private String password;
        private UserRole role;

        Builder() {
            super();
        }

        public S setId(Long id) {
            this.id = id;
            return (S) this;
        }

        public S setUsername(String username) {
            this.username = username;
            return (S) this;
        }

        public S setEmail(String email) {
            this.email = email;
            return (S) this;
        }

        public S setPassword(String password) {
            this.password = password;
            return (S) this;
        }

        public S setRole(UserRole role) {
            this.role = role;
            return (S) this;
        }

        public User build() {
            User user = new User();
            user.setEmail(this.email);
            user.setId(this.id);
            user.setPassword(this.password);
            user.setRole(this.role);
            user.setUsername(this.username);
            return user;
        }
    }
}
