package com.systempro.library.controllerTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class LibraryControllerTests {

	static String BOOL_API = "/books";

	@Autowired
	MockMvc mvc;

	@Test
	@DisplayName("Post new book")
	void createBookTest() throws Exception {

		
		//metodo para gerar um json
		String json = new ObjectMapper().writeValueAsString(null);
		
		//scopo de post new book
		MockHttpServletRequestBuilder requestBuilder =
		MockMvcRequestBuilders
				.post(BOOL_API)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);

		mvc
			.perform(requestBuilder)
			.andExpect(MockMvcResultMatchers.status().isCreated() )
			.andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty() )
			.andExpect(MockMvcResultMatchers.jsonPath("title").value("Meu livro") )
			.andExpect(MockMvcResultMatchers.jsonPath("autor").value("Fernando") )
			.andExpect(MockMvcResultMatchers.jsonPath("isbn").value("123123") )
			;
	}

	@Test
	@DisplayName("Error new book")
	void createInvalidBookTest() {

	}

}
