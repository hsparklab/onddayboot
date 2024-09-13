package com.hsparklab.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.hsparklab.book.entity.Book;
import com.hsparklab.book.entity.BookRepository;
import com.hsparklab.dto.BookCreateDTO;
import com.hsparklab.dto.BookEditDTO;
import com.hsparklab.dto.BookEditResponseDTO;
import com.hsparklab.dto.BookListResponseDTO;
import com.hsparklab.dto.BookReadResponseDTO;

@Service
public class BookService {
	private BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public List<BookListResponseDTO> bookList(String title, Integer page) {
		final int pageSize = 3;
		List<Book> books;
		if (page == null) {
			page = 0;
		} else {
			page -= 1;
		}

		if (title == null) {
			Pageable pageable = PageRequest.of(page, pageSize, Direction.DESC, "insertDateTime");
			books = this.bookRepository.findAll(pageable).toList();
		} else {
			Pageable pageable = PageRequest.of(page, pageSize);
			Sort sort = Sort.by(Order.desc("insertDateTime"));
			pageable.getSort().and(sort);
			books = this.bookRepository.findByTitleContains(title, pageable);
		}
		return books.stream().map(book -> new BookListResponseDTO(book.getBookId(), book.getTitle()))
				.collect(Collectors.toList());

	}

	public Integer insert(BookCreateDTO bookCreateDTO) {
		Book book = Book.builder().title(bookCreateDTO.getTitle()).price(bookCreateDTO.getPrice()).build();
		this.bookRepository.save(book);
		return book.getBookId();
	}

	public BookReadResponseDTO read(Integer bookId) throws NoSuchElementException {
		Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new NoSuchElementException());
		BookReadResponseDTO bookReadResponseDTO = new BookReadResponseDTO();
		bookReadResponseDTO.fromBook(book);
		return bookReadResponseDTO;

	}

	public BookEditResponseDTO edit(Integer bookId) throws NoSuchElementException {
		Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new NoSuchElementException());
		return BookEditResponseDTO.BookFactory(book);

	}

	public void update(BookEditDTO bookEditDTO) throws NoSuchElementException {
		Book book = this.bookRepository.findById(bookEditDTO.getBookId())
				.orElseThrow(() -> new NoSuchElementException());
		book = bookEditDTO.fill(book);
		bookRepository.save(book);

	}

	public void delete(Integer bookId) {
		Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new NoSuchElementException());
		this.bookRepository.delete(book);
	}
}
