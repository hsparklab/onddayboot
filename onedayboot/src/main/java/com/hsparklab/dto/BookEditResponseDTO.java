package com.hsparklab.dto;

import java.time.LocalDateTime;

import com.hsparklab.book.entity.Book;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BookEditResponseDTO {
	private Integer bookId;
	private String title;
	private Integer price;
	private LocalDateTime insertDateTime;
	
	public BookEditResponseDTO fromBook(Book book) {
		this.bookId = book.getBookId();
		this.title = book.getTitle();
		this.price = book.getPrice();
		this.insertDateTime = book.getInsertDateTime();
		return this;
	}
	
	public static BookEditResponseDTO BookFactory(Book book) {
		BookEditResponseDTO bookEditResponseDTO = new BookEditResponseDTO();
		return bookEditResponseDTO.fromBook(book);
	}

}
