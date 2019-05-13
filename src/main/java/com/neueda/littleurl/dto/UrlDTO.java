package com.neueda.littleurl.dto;

import static com.neueda.littleurl.util.Constants.MAX_LONG_URL_SIZE;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public class UrlDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String code;
	
	@NotNull
	@NotEmpty
	@URL
	@Length(max=MAX_LONG_URL_SIZE)
	private String longUrl;
	
	public UrlDTO() {
	}

	public UrlDTO(String code, @NotNull @NotEmpty @URL @Length(max = 2048) String longUrl) {
		super();
		this.code = code;
		this.longUrl = longUrl;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}	
	
}
