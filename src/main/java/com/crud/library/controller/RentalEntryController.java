package com.crud.library.controller;

import com.crud.library.domain.Status;
import com.crud.library.domain.Book;
import com.crud.library.domain.RentalEntry;
import com.crud.library.domain.RentalEntryDto;
import com.crud.library.domain.User;
import com.crud.library.exceptions.BookNotAvailableException;
import com.crud.library.exceptions.BookNotFoundException;
import com.crud.library.mapper.RentalEntryMapper;
import com.crud.library.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/rentalEntries")
public class RentalEntryController {
    private final RentalEntryMapper rentalEntryMapper;
    private final DbService dbService;

    @PostMapping(value = "rentBook", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RentalEntryDto rentBook(@RequestBody RentalEntryDto rentalEntryDto) throws Exception {
        RentalEntry rentalEntry = rentalEntryMapper.mapToRentalEntry(rentalEntryDto);
        String status = rentalEntry.getBook().getStatus().getStatus();
        if (status.equals("available")) {
            RentalEntry createdRentalEntry = dbService.saveRentalEntry(new RentalEntry(
                    rentalEntry.getBook(),
                    rentalEntry.getUser(),
                    LocalDateTime.now()
            ));

            Book book = createdRentalEntry.getBook();
            book.setStatus(Status.RENTED);
            book.getListOfRentals().add(createdRentalEntry);
            dbService.saveBook(book);

            User user = createdRentalEntry.getUser();
            user.getListOfRentals().add(createdRentalEntry);
            dbService.saveUser(user);

            return rentalEntryMapper.mapToRentalEntryDto(createdRentalEntry);
        } else throw new BookNotAvailableException("Book status: " + status);
    }

    @PutMapping(value = "returnBook")
    public RentalEntryDto returnBook (@RequestParam Long bookId) throws BookNotFoundException {
        Book book = dbService.getBookById(bookId);
        book.setStatus(Status.AVAILABLE);
        dbService.saveBook(book);
        RentalEntry savedRentalEntry = dbService.saveRentalEntry(setReturnDate(book));

        return rentalEntryMapper.mapToRentalEntryDto(savedRentalEntry);
    }

    @PutMapping(value = "lostBook")
    public RentalEntryDto lostBook(@RequestParam Long bookId) throws BookNotFoundException {
        Book book = dbService.getBookById(bookId);
        book.setStatus(Status.LOST);
        dbService.saveBook(book);
        RentalEntry savedRentalEntry = dbService.saveRentalEntry(setReturnDate(book));
        return rentalEntryMapper.mapToRentalEntryDto(savedRentalEntry);
    }

    private RentalEntry setReturnDate(Book book) {
        List<RentalEntry> listOfRentals = book.getListOfRentals().stream()
                .filter(n -> n.getReturnDate() == null)
                .collect(Collectors.toList());
        RentalEntry rentalEntry = listOfRentals.get(0);
        rentalEntry.setReturnDate(LocalDateTime.now());
        return rentalEntry;
    }
}
