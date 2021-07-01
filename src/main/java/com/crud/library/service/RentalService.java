package com.crud.library.service;

import com.crud.library.domain.Rental;
import com.crud.library.repository.RentalDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalDao rentalDao;

    public Rental saveRental(final Rental rental) {
        return rentalDao.save(rental);
    }
}
