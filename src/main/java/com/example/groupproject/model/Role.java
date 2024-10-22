package com.example.groupproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
//@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "role")
@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    long id;

    @NotNull(message = "Role cannot be null")
    @NotBlank(message = "Role cannot be left blank")
    @Size(max = 20, message = "Role has to be with up to 20 characters")
    @Column(name = "authority", nullable = false)
    String authority;

    @ManyToMany(mappedBy = "authorities")
    @JsonIgnore
    private Set<User> users = new HashSet<>();


    @Override
    public String getAuthority() {
        return this.authority;
    }

    public long getId() {
        return id;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}