package com.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_store")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookStore {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty
    private String bookId;

    @Column(name= "book_name")
    @Nonnull
    private String bookName;

    @Column(name= "author")
    @Nonnull
    @JsonProperty
    private String author;

    @Column(name= "genre")
    @Nonnull
    @JsonProperty
    private String genre;

    @Column(name= "book_description")
    @JsonProperty
    private String description;
}
