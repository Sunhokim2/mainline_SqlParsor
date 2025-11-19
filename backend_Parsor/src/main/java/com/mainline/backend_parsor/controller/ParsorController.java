package com.mainline.backend_parsor.controller;

import com.mainline.backend_parsor.dto.SqlRequest;
import com.mainline.backend_parsor.dto.SqlResponse;
import com.mainline.backend_parsor.service.ParserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ParsorController {

    private final ParserService parserService;

    public ParsorController(ParserService parserService) {
        this.parserService = parserService;
    }

    @PostMapping("/parse")
    public List<SqlResponse> parseSql(@RequestBody SqlRequest request) {
        return parserService.parse(request.getSql());
    }
}