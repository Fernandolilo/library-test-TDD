package com.systempro.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systempro.library.domain.Book;


public interface BookRepository extends JpaRepository<Book, Long>{

}
