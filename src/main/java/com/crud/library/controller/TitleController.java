package com.crud.library.controller;

import com.crud.library.domain.TitleDto;
import com.crud.library.mapper.TitleMapper;
import com.crud.library.service.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/titles")
public class TitleController {
    private final TitleMapper titleMapper;
    private final TitleService titleService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public TitleDto createTitle(@RequestBody TitleDto titleDto) {
        return titleMapper.mapToTitleDto(titleService.saveTitle(titleMapper.mapToTitle(titleDto)));
    }
}
