package com.mikepaskual.delivery.user.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    private String firstName;
    private String lastName;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate birthday;
    private LocalDateTime createdAt;

    public User() {
        roles = new HashSet<>();
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        if (roles == null) {
            this.roles = new HashSet<>();
        } else {
            this.roles = new HashSet<>(roles);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    @SuppressWarnings("rawtypes")
    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("rawtypes")
    public static class Builder<S extends Builder> {

        private Long id;
        private String username;
        private String email;
        private String password;
        private UserRole role;
        private Set<Role> roles;
        private String firstName;
        private String lastName;
        private String phone;
        private Gender gender;
        private LocalDate birthday;
        private LocalDateTime createdAt;

        Builder() {
            super();
        }

        @SuppressWarnings("unchecked")
        public S setRoles(Set<Role> roles) {
            this.roles = roles;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setFirstName(String firstName) {
            this.firstName = firstName;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setLastName(String lastName) {
            this.lastName = lastName;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setPhone(String phone) {
            this.phone = phone;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setGender(Gender gender) {
            this.gender = gender;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setBirthday(LocalDate birthday) {
            this.birthday = birthday;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setId(Long id) {
            this.id = id;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setUsername(String username) {
            this.username = username;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setEmail(String email) {
            this.email = email;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setPassword(String password) {
            this.password = password;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setRole(UserRole role) {
            this.role = role;
            return (S) this;
        }

        public User build() {
            User user = new User();
            user.setBirthday(this.birthday);
            user.setCreatedAt(this.createdAt);
            user.setEmail(this.email);
            user.setFirstName(this.firstName);
            user.setGender(this.gender);
            user.setId(this.id);
            user.setLastName(this.lastName);
            user.setPassword(this.password);
            user.setPhone(this.phone);
            user.setRole(this.role);
            user.setRoles(this.roles);
            user.setUsername(this.username);
            return user;
        }
    }
}
