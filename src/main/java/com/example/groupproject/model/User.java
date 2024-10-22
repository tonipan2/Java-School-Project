package com.example.groupproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    long id;

    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be left blank")
    @Size(max = 20, message = "Username has to be with up to 20 characters")
    @Column(name = "username", nullable = false, unique = true)
    String username;

    @JsonIgnore
    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be left blank")
    @Size(min = 8, message = "Password must have at least 8 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()])(?=\\S+$).{8,}$", message = "Password must contain at least one digit and one special character")
    @Column(name = "password", nullable = false)
    String password;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be left blank")
    @Column(name = "email", nullable = false, unique = true)
    String email;

    @JsonIgnore
    @NotNull(message = "EGN cannot be null")
    @NotBlank(message = "EGN cannot be left blank")
    @Size(min = 10, max = 10, message = "EGN must be 10 digits")
    @Pattern(regexp = "\\d{10}", message = "EGN must contain only digits")
    @Column(name = "egn", nullable = false,unique = true)
    String egn;

    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be left blank")
    @Pattern(regexp = "^[\\p{Lu}][\\p{Ll}]*$", message = "Names should start with a capital letter followed by lowercase")
    @Size(max = 20, message = "First name has to be up to 20 characters")
    @Column(name = "first_name", nullable = false)
    String firstName;

    @NotNull(message = "Last name cannot be null")
    @NotBlank(message = "Last name cannot be left blank")
    @Pattern(regexp = "^[\\p{Lu}][\\p{Ll}]*$", message = "Names should start with a capital letter followed by lowercase")
    @Size(max = 20, message = "Last name has to be up to 20 characters")
    @Column(name = "last_name", nullable = false)
    String lastName;

    @NotNull(message = "Phone number cannot be null")
    @NotBlank(message = "Phone number cannot be left blank")
    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    @Pattern(regexp = "\\d{10}", message = "Phone number must contain only digits")
    @Column(name = "phone_number", nullable = false)
    String phoneNumber;

    @Column(name = "birth_date", nullable = false)
    @NotNull(message = "Birth date cannot be null")
    LocalDate birthDate;

    @NotNull(message = "Address cannot be null")
    @NotBlank(message = "Address cannot be left blank")
    @Size(max = 150, message = "Address has to be with up to 150 characters")
    @Column(name = "address", nullable = false)
    String address;

    @JsonIgnore
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role", // The join table name
            joinColumns = @JoinColumn(name = "user_id"), // FK column
            inverseJoinColumns = @JoinColumn(name = "role_id") // FK column
    )
    private Set<Role> authorities = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, egn, firstName, lastName, phoneNumber, birthDate, address, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, authorities);
    }
}