package com.crud.library.controller;

import com.crud.library.domain.Status;
import com.crud.library.domain.Book;
import com.crud.library.domain.BookDto;
import com.crud.library.domain.Title;
import com.crud.library.exceptions.StatusNotFoundException;
import com.crud.library.exceptions.TitleNotFoundException;
import com.crud.library.mapper.BookMapper;
import com.crud.library.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        Title title = createdBook.getTitle();
        title.getBooks().add(createdBook);
        dbService.saveTitle(title);

        return bookMapper.mapToBookDto(createdBook);
    }

    @PutMapping(value = "updateStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto updateStatus(@RequestBody BookDto bookDto) throws Exception {
        Book book = dbService.getBookById(bookDto.getId());
        switch (bookDto.getStatus()) {
            case "available":
                book.setStatus(Status.AVAILABLE);
                break;
            case "rented":
                book.setStatus(Status.RENTED);
                break;
            case "destroyed":
                book.setStatus(Status.DESTROYED);
                break;
            case "lost":
                book.setStatus(Status.LOST);
                break;
            default:
                throw new StatusNotFoundException();
        }
        return bookMapper.mapToBookDto(dbService.saveBook(book));
    }

    @GetMapping(value = "getNumberOfAvailableBooks")
    public int getNumberOfAvailableBooks(@RequestParam Long titleId) throws Exception {
        Optional<Title> titleFromDb = dbService.getTitleById(titleId);
        if (titleFromDb.isPresent()) {
            Title title = titleFromDb.get();
            List<Book> availableBooks = title.getBooks().stream()
                    .filter(n -> n.getStatus().getStatus().equals("available"))
                    .collect(Collectors.toList());
            return availableBooks.size();
        } else throw new TitleNotFoundException();
    }
}
