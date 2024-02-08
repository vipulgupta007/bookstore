package com.bookstore.service;

import com.bookstore.domain.BookStoreDto;
import com.bookstore.domain.BookStoreRto;
import com.bookstore.model.BookStore;

import java.util.List;

public interface BookStoreService {
    BookStore addNewBook(final BookStoreDto bookStoreDto);
    BookStoreRto getBookDetails(final String bookId);
    void deleteBook(final String bookId);
    List<BookStoreRto> getAllBooksInStore();
}
