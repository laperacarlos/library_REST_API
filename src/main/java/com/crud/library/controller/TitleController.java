package com.crud.library.controller;

import com.crud.library.domain.Title;
import com.crud.library.domain.TitleDto;
import com.crud.library.mapper.TitleMapper;
import com.crud.library.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/titles")
public class TitleController {
    private final TitleMapper titleMapper;
    private final DbService dbService;

    @PostMapping(value = "createTitle")
    public TitleDto createTitle(@RequestBody TitleDto titleDto) {
        Title title = titleMapper.mapToTitle(titleDto);
        Title createdTitle = dbService.saveTitle(new Title(
                title.getTitle(),
                title.getAuthor(),
                title.getPublicationYear()
        ));
        return titleMapper.mapToTitleDto(createdTitle);
    }
}
