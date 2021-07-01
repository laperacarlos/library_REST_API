package com.crud.library.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity(name = "RENTAL_REGISTER")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "USER_ID")
    private User user;

    @NotNull
    @Column(name = "RENTAL_DATE")
    private LocalDateTime rentalDate;

    @Column(name = "RETURN_DATE")
    private LocalDateTime returnDate;

    public Rental(@NotNull Book book, @NotNull User user, @NotNull LocalDateTime rentalDate) {
        this.book = book;
        this.user = user;
        this.rentalDate = rentalDate;
    }
}
