package com.neueda.littleurl.dto;

import static com.neueda.littleurl.util.Constants.URL_CODE_SIZE;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class UrlUpdateDTO extends UrlDTO {
	
	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	@Length(max=URL_CODE_SIZE)	
	private String code;
	
	public UrlUpdateDTO() {

	}
}
