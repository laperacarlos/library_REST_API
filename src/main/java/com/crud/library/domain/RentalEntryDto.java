package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class RentalEntryDto {
    private Long id;
    private Book book;
    private User user;
    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;
}
