package com.systempro.library.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.systempro.library.domain.Book;
import com.systempro.library.domain.dto.BookDTO;
import com.systempro.library.service.BookService;

@RestController
@RequestMapping(value = "/books")
public class BookController {

	private final BookService service;

	public BookController(BookService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookDTO create(@RequestBody BookDTO request) {
		Book entity = 
				Book.builder()				
				.autor(request.getAutor())
				.title(request.getTitle())
				.isbn(request.getIsbn())
				.build();
				
		entity = service.save(entity);
	  

		return BookDTO.builder()	
				.id(entity.getId())
				.autor(entity.getAutor())
				.title(entity.getTitle())
				.isbn(entity.getIsbn())
				.build();
	  
	}

}
