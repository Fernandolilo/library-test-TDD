package com.systempro.library.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.systempro.library.domain.Book;
import com.systempro.library.repository.BookRepository;
import com.systempro.library.service.imp.BookServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTests {

	private BookService service;

	@MockBean
	BookRepository repository;

	public BookServiceTests(BookService service) {
		this.service = service;
	}

	@BeforeEach
	public void setUp() {
		this.service = new BookServiceImpl(repository);
	}

	@Test
	public void saveBookTests() {

		// cenário
		Book book = Book.builder().autor("Fernando").title("Meu Livro").isbn("123123").build();

		Mockito.when(repository.save(book))
				.thenReturn(Book.builder().id(1L).autor("Fernando").title("Meu Livro").isbn("123123").build());
		// execução
		Book savedBook = service.save(book);

		// verificação
		assertThat(savedBook.getId()).isEqualTo(1L);
		assertThat(savedBook.getAutor()).isEqualTo("Fernando");
		assertThat(savedBook.getTitle()).isEqualTo("Meu Livro");
		assertThat(savedBook.getIsbn()).isEqualTo("123123");

	}
}
