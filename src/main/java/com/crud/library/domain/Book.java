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
    @Column
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @OneToMany(
            targetEntity = Rental.class,
            mappedBy = "book",
            fetch = FetchType.EAGER
    )
    private List<Rental> listOfRentals = new ArrayList<>();

    public Book(@NotNull Title title) {
        this.title = title;
        this.status = Status.AVAILABLE;
    }
}
