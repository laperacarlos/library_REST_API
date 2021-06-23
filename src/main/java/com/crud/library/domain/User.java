package com.crud.library.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME")
    @NotNull
    private String firstName;

    @Column(name = "LAST_NAME")
    @NotNull
    private String lastName;

    @Column(name = "CREATION_DATE")
    @NotNull
    private LocalDateTime creationDate;

    @OneToMany(
            targetEntity = RentalEntry.class,
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    private List<RentalEntry> listOfRentals = new ArrayList<>();

    private boolean costToBePaid;

    public User(@NotNull String firstName, @NotNull String lastName, @NotNull LocalDateTime creationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.creationDate = creationDate;
    }

    public User (String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
