package com.crud.library.mapper;

import com.crud.library.domain.RentalEntry;
import com.crud.library.domain.RentalEntryDto;
import org.springframework.stereotype.Service;

@Service
public class RentalEntryMapper {

    public RentalEntry rentalEntry(final RentalEntryDto rentalEntryDto) {
        return new RentalEntry(
                rentalEntryDto.getBook(),
                rentalEntryDto.getUser()
        );
    }

    public RentalEntryDto mapToRentalEntryDto(final RentalEntry rentalEntry) {
        return new RentalEntryDto(
                rentalEntry.getId(),
                rentalEntry.getBook(),
                rentalEntry.getUser(),
                rentalEntry.getRentalDate(),
                rentalEntry.getReturnDate()
        );
    }
}
