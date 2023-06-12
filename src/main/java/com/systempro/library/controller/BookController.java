package com.systempro.library.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.systempro.library.domain.dto.BookDTO;

@RestController
@RequestMapping(value = "/books")
public class BookController {
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookDTO create() {
		BookDTO dto =  new BookDTO();
		
		dto.setId(1L);
		dto.setAutor("Fernando");
		dto.setTitle("Meu Livro");
		dto.setIsbn("123123");
		return dto;
	}

}
