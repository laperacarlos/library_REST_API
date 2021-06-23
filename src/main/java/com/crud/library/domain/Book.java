package com.crud.library.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "TITLE_ID")
    private Title title;

    @NotNull
    private Status status;

    @OneToMany(
            targetEntity = RentalEntry.class,
            mappedBy = "book",
            fetch = FetchType.LAZY
    )
    private List<RentalEntry> listOfRentals = new ArrayList<>();

    public Book(@NotNull Title title, @NotNull Status status) {
        this.title = title;
        this.status = status;
    }

    public Book (Title title) {
        this.title = title;
    }
}
