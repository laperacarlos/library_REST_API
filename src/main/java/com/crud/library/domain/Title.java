package com.crud.library.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "TITLES")
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String title;

    @Column
    @NotNull
    private String author;

    @Column(name = "PUBLICATION_YEAR")
    @NotNull
    private int publicationYear;

    @OneToMany(
            targetEntity = Book.class,
            mappedBy = "title",
            fetch = FetchType.LAZY
    )
    private List<Book> books = new ArrayList<>();

    public Title(@NotNull String title, @NotNull String author, @NotNull int publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }
}
