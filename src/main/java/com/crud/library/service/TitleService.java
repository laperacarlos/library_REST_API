package com.crud.library.service;

import com.crud.library.domain.Title;
import com.crud.library.exceptions.TitleNotFoundException;
import com.crud.library.repository.TitleDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TitleService {
    private final TitleDao titleDao;

    public Title saveTitle(final Title title) {
        return titleDao.save(title);
    }

    public Title getTitleById(final Long titleId) throws Exception {
        return titleDao.findById(titleId).orElseThrow(()->new TitleNotFoundException(titleId));
    }
}
