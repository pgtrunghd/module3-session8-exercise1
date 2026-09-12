package com.homework.library.service;

import com.homework.library.dto.BookCreateDTO;
import com.homework.library.entity.Book;
import com.homework.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    private final String UPLOAD_DIR = "uploads/";

    public Book createBook(BookCreateDTO dto) {
        MultipartFile file = dto.getCoverImage();

        String fileName = "";
        if (file != null && !file.isEmpty()) {
            try {
                File dir = new File(UPLOAD_DIR);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String originalFilename = file.getOriginalFilename();
                String fileExtension = "";

                if (originalFilename != null && originalFilename.contains(".")) {
                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                fileName = UUID.randomUUID().toString() + fileExtension;
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.copy(file.getInputStream(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Book book = Book.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .stock(dto.getStock())
                .coverUrl(fileName)
                .build();
        return bookRepository.save(book);
    }

}
