package com.systempro.library.service.imp;

import org.springframework.stereotype.Service;

import com.systempro.library.domain.Book;
import com.systempro.library.repository.BookRepository;
import com.systempro.library.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	private BookRepository repository;

	public BookServiceImpl(BookRepository repository) {
		this.repository = repository;
	}

	@Override
	public Book save(Book book) {
		return repository.save(book);
	}

}
