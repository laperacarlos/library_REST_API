package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class RentalEntryDto {
    private Long id;
    private Long bookId;
    private Long userId;
    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;
}
