package com.crud.library.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity(name = "RENTAL_REGISTER")
public class RentalEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@OneToOne???
    @Column(name = "BOOK_ID")
    @NotNull
    private Long bookId;

    //@OneToOne???
    @Column(name = "USER_ID")
    @NotNull
    private Long userId;

    @Column(name = "RENTAL_DATE")
    private LocalDateTime rentalDate;

    @Column(name = "RETURN_DATE")
    private LocalDateTime returnDate;
}
