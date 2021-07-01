package com.crud.library.controller;

import com.crud.library.domain.*;
import com.crud.library.exceptions.BookNotAvailableException;
import com.crud.library.exceptions.RentalNotFoundException;
import com.crud.library.mapper.RentalMapper;
import com.crud.library.service.BookService;
import com.crud.library.service.RentalService;
import com.crud.library.utility.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/rentals")
public class RentalController {
    private final RentalMapper rentalMapper;
    private final RentalService rentalService;
    private final BookService bookService;
    private final TimeProvider timeProvider;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public RentalDto createRental(@RequestBody RentalDto rentalDto) throws Exception {
        Book book = bookService.getBookById(rentalDto.getBookId());
        if (book.getStatus().equals(Status.AVAILABLE)) {
            book.setStatus(Status.RENTED);
            bookService.saveBook(book);
            return rentalMapper.mapToRentalEntryDto(rentalService.saveRental(rentalMapper.mapToRentalEntry(rentalDto)));
        } else throw new BookNotAvailableException("Book status: " + book.getStatus().toString());
    }

    @PutMapping(value = "returnBook")
    public RentalDto returnBook (@RequestParam Long bookId) throws Exception {
        Book book = bookService.getBookById(bookId);
        book.setStatus(Status.AVAILABLE);
        return rentalMapper.mapToRentalEntryDto(rentalService.saveRental(closeRental(book)));
    }

    @PutMapping(value = "lostBook")
    public RentalDto lostBook(@RequestParam Long bookId) throws Exception {
        Book book = bookService.getBookById(bookId);
        book.setStatus(Status.LOST);
        return rentalMapper.mapToRentalEntryDto(rentalService.saveRental(closeRental(book)));
    }

    private Rental closeRental(Book book) throws Exception{
        Optional<Rental> rentalOpt = book.getListOfRentals().stream()
                .filter(r -> r.getReturnDate() == null)
                .findFirst();
        if(rentalOpt.isPresent()) {
            Rental rental = rentalOpt.get();
            rental.setReturnDate(timeProvider.getTime());
            return rental;
        } else throw new RentalNotFoundException(book.getId());
    }
}
