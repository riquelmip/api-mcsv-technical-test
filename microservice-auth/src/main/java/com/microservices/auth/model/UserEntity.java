package com.microservices.auth.model;

import jakarta.persistence.*;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private ProviderEnum provider;

    @Column(name = "is_oauth2")
    private boolean isOAuth2;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "is_enable")
    private boolean isEnable;

    @Column(name = "account_non_expired")
    private boolean accountNonExpired;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;

    // Con esto se crearía una tabla intermedia para la relación muchos a muchos
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "fk_user_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();


}
