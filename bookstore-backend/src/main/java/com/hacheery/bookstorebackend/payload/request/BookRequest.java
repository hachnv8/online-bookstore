package com.hacheery.bookstorebackend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest extends PaginationRequest {
    private String title;

    private String author;

    private String description;

    private Double price;

    private String imageUrl;

    private Long categoryId;
}
