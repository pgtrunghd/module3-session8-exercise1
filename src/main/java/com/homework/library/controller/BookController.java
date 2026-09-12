package com.homework.library.controller;

import com.homework.library.dto.BookCreateDTO;
import com.homework.library.entity.Book;
import com.homework.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Book> createBook(@ModelAttribute BookCreateDTO dto) {
        Book createdBook = bookService.createBook(dto);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }
}
