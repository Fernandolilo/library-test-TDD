package com.systempro.library.controller;

import org.modelmapper.ModelMapper;
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
	private final ModelMapper modelMapper;
	
	public BookController(BookService service,  ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookDTO create(@RequestBody BookDTO request) {
		Book entity = modelMapper.map(request, Book.class);
				
				
		entity = service.save(entity);
	  

		return modelMapper.map(entity, BookDTO.class);
	  
	}

}
