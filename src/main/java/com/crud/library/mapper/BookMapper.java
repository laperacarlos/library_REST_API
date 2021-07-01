package com.crud.library.mapper;

import com.crud.library.domain.Book;
import com.crud.library.domain.BookDto;
import com.crud.library.domain.Title;
import com.crud.library.exceptions.TitleNotFoundException;
import com.crud.library.repository.TitleDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookMapper {

    private final TitleDao titleDao;

    public Book mapToBook(final BookDto bookDto) throws Exception {
        return new Book(
                retrievedTitle(bookDto.getTitleId())
        );
    }

    public BookDto mapToBookDto(final Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle().getId(),
                book.getStatus()
        );
    }

    private Title retrievedTitle(Long titleId) throws Exception {
            return titleDao.findById(titleId).orElseThrow(() -> new TitleNotFoundException(titleId));
    }
}
