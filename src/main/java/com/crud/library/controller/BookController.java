package com.crud.library.controller;

import com.crud.library.Status;
import com.crud.library.domain.Book;
import com.crud.library.domain.BookDto;
import com.crud.library.exceptions.BookNotFoundException;
import com.crud.library.exceptions.StatusNotFoundException;
import com.crud.library.mapper.BookMapper;
import com.crud.library.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/books")
public class BookController {
    private final BookMapper bookMapper;
    private final DbService dbService;

    @PostMapping(value = "createBook", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto createBook(@RequestBody BookDto bookDto) throws Exception {
        Book book = bookMapper.mapToBook(bookDto);
        Book createdBook = dbService.saveBook(new Book(
                book.getTitle(),
                Status.AVAILABLE
        ));
        return bookMapper.mapToBookDto(createdBook);
    }

    @PutMapping(value = "updateStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto updateStatus(@RequestParam Long bookId, @RequestParam String status) throws Exception {
        Optional<Book> bookFromDb = dbService.getBookById(bookId);
        if (bookFromDb.isPresent()) {
            Book retrievedBook = bookFromDb.get();
            switch (status) {
                case "available":
                    retrievedBook.setStatus(Status.AVAILABLE);
                    break;
                case "rented":
                    retrievedBook.setStatus(Status.RENTED);
                    break;
                case "destroyed":
                    retrievedBook.setStatus(Status.DESTROYED);
                    break;
                case "lost":
                    retrievedBook.setStatus(Status.LOST);
                    break;
                default:
                    throw new StatusNotFoundException();
            }
            return bookMapper.mapToBookDto(dbService.saveBook(retrievedBook));
        } else throw new BookNotFoundException();
    }
}
