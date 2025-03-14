package com.hsparklab.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.hsparklab.book.entity.Book;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.NonFinal;

@Getter
@Setter
@NonNull
public class BookEditDTO {
	@NonNull
	@Positive
	private Integer bookId;
	
	@NonNull
	@NotBlank
	private String title;
	
	@NonNull
	@Min(1000)
	private Integer price;
	
	public Book fill(Book book) {
		book.setTitle(this.title);
		book.setPrice(this.price);
		return book;
	}

}
