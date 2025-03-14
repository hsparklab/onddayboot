package com.hsparklab.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.hsparklab.book.entity.Book;
import com.hsparklab.book.entity.BookLog;
import com.hsparklab.book.entity.BookLogRepository;
import com.hsparklab.book.entity.BookRepository;
import com.hsparklab.dto.BookLogCreateDTO;
import com.hsparklab.dto.BookLogCreateResponseDTO;

@Service
public class BookLogService {
	private BookRepository bookRepository;
	private BookLogRepository bookLogRepository;
	
	public BookLogService(BookRepository bookRepository, BookLogRepository bookLogRepository) {
		this.bookRepository = bookRepository;
		this.bookLogRepository = bookLogRepository;
	}
	
	public BookLogCreateResponseDTO insert(BookLogCreateDTO bookLogCreateDTO) {
		Book book = this.bookRepository.findById(bookLogCreateDTO.getBookId()).orElseThrow(() -> new NoSuchElementException());
		BookLog bookLog = BookLog.builder()
				.book(book)
				.comment(bookLogCreateDTO.getComment())
				.page(bookLogCreateDTO.getPage())
				.build();
		bookLog = this.bookLogRepository.save(bookLog);
		return BookLogCreateResponseDTO.BookLogFactory(bookLog);
		
	}
	
	
}
