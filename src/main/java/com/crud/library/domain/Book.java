package com.crud.library.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   //tu raczej jakiś ENUM powinien być
    @Column
    @NotNull
    private String status;

    @ManyToOne
    @JoinColumn(name = "TITLE_ID")
    private Title title;
}
