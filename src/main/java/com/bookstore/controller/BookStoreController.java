package com.bookstore.controller;

import com.bookstore.domain.BookStoreDto;
import com.bookstore.domain.BookStoreRto;
import com.bookstore.model.BookStore;
import com.bookstore.service.BookStoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-store")
@Component
public class BookStoreController {

    @Autowired
    private BookStoreService bookStoreService;

    @PostMapping(consumes = "application/json")
    public BookStore addNewBook(@RequestBody BookStoreDto bookStoreDto){
        return bookStoreService.addNewBook(bookStoreDto);
    }


    @GetMapping("/{bookId}")
    public BookStoreRto getBookDetails(@PathVariable("bookId") String bookId){
        return bookStoreService.getBookDetails(bookId);
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookFromBookStore(@PathVariable("bookId") String bookId){
        bookStoreService.deleteBook(bookId);
    }

    @GetMapping("/")
    public List<BookStoreRto> getAllBooksInStore(){
        return bookStoreService.getAllBooksInStore();
    }
}
