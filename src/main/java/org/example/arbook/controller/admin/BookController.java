package org.example.arbook.controller.admin;

import lombok.RequiredArgsConstructor;
import org.example.arbook.model.entity.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.example.arbook.util.ApiConstants.*;

@RestController(API + V1 + ADMIN + BOOK)
@RequiredArgsConstructor
public class BookController {
    @GetMapping("/")
    public ResponseEntity<List<Book>> getAllBooks(){
return null;
    }
}
