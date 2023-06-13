		
		//mapeamento do arq.json no caso ainda esta null devido não existir ainda a class book
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



Criado o escopo de test, ele não passou devido não existir uma rota ou seja um path,
proximo passo temos que criar um controller.Book com o mapeamento para books
desta forma esta test irá passar com sucesso!

 library-test-TDDMockHttpServletRequest:
      HTTP Method = POST
      Request URI = /books
       Parameters = {}
          Headers = [Content-Type:"application/json;charset=UTF-8", Accept:"application/json", Content-Length:"4"]
             Body = null
    Session Attrs = {}

Handler:
             Type = org.springframework.web.servlet.resource.ResourceHttpRequestHandler

Async:
    Async started = false
     Async result = null

Resolved Exception:
             Type = null

ModelAndView:
        View name = null
             View = null
            Model = null

FlashMap:
       Attributes = null

MockHttpServletResponse:
           Status = 404
    Error message = null
          Headers = [Vary:"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"]
     Content type = null
             Body = 
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
          
          
Criamos o path /books em controller, agora execultando os tests passa com um 201 created...
   
import com.systempro.library.domain.dto.BookDTO;
@RestController
@RequestMapping(value = "/books")
public class BookController {
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookDTO create() {
		return null;
	}

}

package com.systempro.library.domain.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BookDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String title;
	private String autor;
	private String isbn;

}

apos criarmos estas novas class podemos execultar o test e neos resultará em um test ok retornado um 201 CREATED.

veja o test execultado a baixo.
   
   
   
   MockHttpServletRequest:
      HTTP Method = POST
      Request URI = /books
       Parameters = {}
          Headers = [Content-Type:"application/json;charset=UTF-8", Accept:"application/json", Content-Length:"4"]
             Body = null
    Session Attrs = {}

Handler:
             Type = com.systempro.library.controller.BookController
           Method = com.systempro.library.controller.BookController#create()

Async:
    Async started = false
     Async result = null

Resolved Exception:
             Type = null

ModelAndView:
        View name = null
             View = null
            Model = null

FlashMap:
       Attributes = null

MockHttpServletResponse:
           Status = 201
    Error message = null
          Headers = []
     Content type = null
             Body = 
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
          
          
          
    após add dados mock no controller veja o retorno como ficou test execultado com sucesso!
    
    no body temos os dados retornando mesmo mockados
    
    MockHttpServletRequest:
      HTTP Method = POST
      Request URI = /books
       Parameters = {}
          Headers = [Content-Type:"application/json;charset=UTF-8", Accept:"application/json", Content-Length:"4"]
             Body = null
    Session Attrs = {}

Handler:
             Type = com.systempro.library.controller.BookController
           Method = com.systempro.library.controller.BookController#create()

Async:
    Async started = false
     Async result = null

Resolved Exception:
             Type = null

ModelAndView:
        View name = null
             View = null
            Model = null

FlashMap:
       Attributes = null

MockHttpServletResponse:
           Status = 201
    Error message = null
          Headers = [Content-Type:"application/json"]
     Content type = application/json
             Body = {"id":1,"title":"Meu Livro","autor":"Fernando","isbn":"123123"}
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
    
    
 agora efetuamos a criação do Book em domain 

 @NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String title;
	private String autor;
	private String isbn;

}
 
 
 criamos uma interface de serviço 
 
 public interface BookService {

	Book save(Book any);

}
 
 refatoramos o nosso controller para trazer um BookDTO e não uma entidade;
 
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
 
 
 refatoramos o nosso test para savar um BOOK atraves do json passado pelo BookDTO
 
 
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
				.id(1l)				
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
				.andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("title").value(bookDTO.getTitle()))
				.andExpect(MockMvcResultMatchers.jsonPath("autor").value(bookDTO.getAutor()))
				.andExpect(MockMvcResultMatchers.jsonPath("isbn").value(bookDTO.getIsbn()));
	}