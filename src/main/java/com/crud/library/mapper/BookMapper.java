package com.crud.library.mapper;

import com.crud.library.Status;
import com.crud.library.domain.Book;
import com.crud.library.domain.BookDto;
import com.crud.library.domain.Title;
import com.crud.library.exceptions.StatusNotFoundException;
import com.crud.library.exceptions.TitleNotFoundException;
import com.crud.library.repository.TitleDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookMapper {

    private final TitleDao titleDao;

    public Book mapToBook(final BookDto bookDto) throws TitleNotFoundException {
        return new Book(
                retrievedTitle(bookDto.getTitleId())
                //mapStatusToBook(bookDto.getStatus())
        );
    }

    public BookDto mapToBookDto(final Book book) throws StatusNotFoundException {
        return new BookDto(
                book.getId(),
                book.getTitle().getId(),
                mapStatusToBookDto(book.getStatus())
        );
    }

   // public List<BookDto> mapToBookDtoList(final List<Book> listOfBooks) throws StatusNotFoundException {
        //return listOfBooks.stream()
               // .map(this::mapToBookDto)
               // .collect(Collectors.toList());
   // }

    //private Status mapStatusToBook(String status) throws StatusNotFoundException {
       // switch (status) {
          //  case "available":
       //         return Status.AVAILABLE;
         //   case "rented":
        //        return Status.RENTED;
        //    case "destroyed":
        //        return Status.DESTROYED;
       //     case "lost":
        //        return Status.LOST;
       //     default:
       //         throw new StatusNotFoundException();
      //  }
   //}

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
