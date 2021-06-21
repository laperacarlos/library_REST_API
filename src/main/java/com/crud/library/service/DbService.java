package com.crud.library.service;

import com.crud.library.repository.BookDao;
import com.crud.library.repository.RentalEntryDao;
import com.crud.library.repository.TitleDao;
import com.crud.library.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbService {
    private final UserDao userDao;
    private final TitleDao titleDao;
    private final BookDao bookDao;
    private final RentalEntryDao rentalEntryDao;

}
