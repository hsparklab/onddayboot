package com.hsparklab.book.entity;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BookLogRepository extends JpaRepository<BookLog, Integer>{
	
}
