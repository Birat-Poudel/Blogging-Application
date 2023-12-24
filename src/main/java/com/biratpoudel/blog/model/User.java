package com.biratpoudel.blog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotEmpty(message = "Username cannot be null or empty!")
    private String username;

    @NotEmpty(message = "Password cannot be null or empty!")
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
               joinColumns = @JoinColumn(name = "user", referencedColumnName = "userId"),
               inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "roleId"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
