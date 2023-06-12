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
