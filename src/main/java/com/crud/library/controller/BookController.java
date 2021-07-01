package com.crud.library.controller;

import com.crud.library.domain.Status;
import com.crud.library.domain.Book;
import com.crud.library.domain.BookDto;
import com.crud.library.domain.Title;
import com.crud.library.mapper.BookMapper;
import com.crud.library.service.BookService;
import com.crud.library.service.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/books")
public class BookController {
    private final BookMapper bookMapper;
    private final TitleService titleService;
    private final BookService bookService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto createBook(@RequestBody BookDto bookDto) throws Exception {
        return bookMapper.mapToBookDto(bookService.saveBook(bookMapper.mapToBook(bookDto)));
    }

    @PutMapping(value = "updateStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto updateStatus(@RequestBody BookDto bookDto) throws Exception {
        Book book = bookService.getBookById(bookDto.getId());
        book.setStatus(bookDto.getStatus());
        return bookMapper.mapToBookDto(bookService.saveBook(book));
    }

    @GetMapping(value = "available")
    public Long getNumberOfAvailableBooks(@RequestParam Long titleId) throws Exception {
        Title title = titleService.getTitleById(titleId);
        return title.getBooks().stream()
                .filter(book -> book.getStatus().equals(Status.AVAILABLE))
                .count();
    }
}
