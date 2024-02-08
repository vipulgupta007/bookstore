package com.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookStoreDto {
    private String bookName;

    private String author;

    private String genre;

    private String description;
}
