package com.biratpoudel.blog.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "user_username_unique", columnNames = "username"),
        @UniqueConstraint(name = "user_email_unique", columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotEmpty(message = "Username cannot be null or empty!")
    @Column(unique = true)
    private String username;

    @Column(unique = true)
    @NotEmpty(message = "Email cannot be null or empty!")
    @Email(message = "Invalid email address")
    private String email;

    @NotEmpty(message = "Password cannot be null or empty!")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character"
    )
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
               joinColumns = @JoinColumn(name = "user", referencedColumnName = "userId"),
               inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "roleId"))
    //@Valid
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //@Valid
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //@Valid
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //@Valid
    private List<Reply> replies;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
