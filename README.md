		
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
