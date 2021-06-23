package com.crud.library.mapper;

import com.crud.library.domain.Status;
import com.crud.library.domain.Book;
import com.crud.library.domain.BookDto;
import com.crud.library.domain.Title;
import com.crud.library.exceptions.StatusNotFoundException;
import com.crud.library.exceptions.TitleNotFoundException;
import com.crud.library.repository.TitleDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookMapper {

    private final TitleDao titleDao;

    public Book mapToBook(final BookDto bookDto) throws TitleNotFoundException {
        return new Book(
                retrievedTitle(bookDto.getTitleId())
        );
    }

    public BookDto mapToBookDto(final Book book) throws StatusNotFoundException {
        return new BookDto(
                book.getId(),
                book.getTitle().getId(),
                mapStatusToBookDto(book.getStatus())
        );
    }

    private String mapStatusToBookDto(Status status) throws StatusNotFoundException {
        switch (status.getStatus()) {
            case "available":
                return "available";
            case "rented":
                return "rented";
            case "destroyed":
                return "destroyed";
            case "lost":
                return "lost";
            default:
                throw new StatusNotFoundException();
        }
    }

    private Title retrievedTitle(Long titleId) throws TitleNotFoundException{
        Optional<Title> retrievedOptTitle = titleDao.findById(titleId);
        if (retrievedOptTitle.isPresent()) {
            return retrievedOptTitle.get();
        } else throw new TitleNotFoundException();
    }
}
