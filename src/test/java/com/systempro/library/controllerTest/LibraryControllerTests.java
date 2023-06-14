package com.systempro.library.controllerTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systempro.library.domain.Book;
import com.systempro.library.domain.dto.BookDTO;
import com.systempro.library.service.BookService;


//@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class LibraryControllerTests {

	static String BOOL_API = "/books";

	@Autowired
	MockMvc mvc;

	
	// esta anotation é um mock especializado, ele é utilziado pelo spring para
	// criar esta instacia mockada
	// e coloca dentro do contexto de injeção de dependencias
	@MockBean
	private BookService service;

	@Test
	@DisplayName("Post new book")
	void createBookTest() throws Exception {

		BookDTO bookDTO = BookDTO
				.builder()
				.autor("Fernando")
				.title("Meu Livro")
				.isbn("123123")
				.build();

		Book saveBook = Book.builder()
				.id(1L)				
				.autor("Fernando")
				.title("Meu Livro")
				.isbn("123123")
				.build();
		
		
		BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(saveBook);
		
		// metodo para gerar um json
		String json = new ObjectMapper().writeValueAsString(bookDTO);

		// scopo de post new book
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(BOOL_API)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("title").value(bookDTO.getTitle()))
				.andExpect(MockMvcResultMatchers.jsonPath("autor").value(bookDTO.getAutor()))
				.andExpect(MockMvcResultMatchers.jsonPath("isbn").value(bookDTO.getIsbn()));
	}

	@Test
	@DisplayName("Error new book")
	void createInvalidBookTest() {

	}

}
