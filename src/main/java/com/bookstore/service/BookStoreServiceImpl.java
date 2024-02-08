package com.bookstore.service;

import com.bookstore.domain.BookStoreDto;
import com.bookstore.domain.BookStoreRto;
import com.bookstore.model.BookStore;
import com.bookstore.repository.BookStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookStoreServiceImpl implements BookStoreService{
    @Autowired
    private BookStoreRepository bookStoreRepository;

    @Override
    public BookStore addNewBook(BookStoreDto bookStoreDto) {
        BookStore book = BookStore.builder()
                .description(bookStoreDto.getDescription())
                .bookName(bookStoreDto.getBookName())
                .author(bookStoreDto.getAuthor())
                .genre(bookStoreDto.getGenre())
                .build();
        return bookStoreRepository.save(book);
    }

    @Override
    public BookStoreRto getBookDetails(String bookId) {
        BookStore bookStore = bookStoreRepository.getReferenceById(bookId);

        return BookStoreRto.builder().bookId(bookId)
                .bookName(bookStore.getBookName()).author(bookStore.getAuthor())
                .genre(bookStore.getGenre())
                .description(bookStore.getDescription()).build();
    }

    @Override
    public void deleteBook(String bookId) {
         bookStoreRepository.deleteById(bookId);
    }

    @Override
    public List<BookStoreRto> getAllBooksInStore() {
        List<BookStore> books = bookStoreRepository.findAll();
        return new ArrayList<>();
    }
}
