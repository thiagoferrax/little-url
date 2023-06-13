package com.thiagoferraz.littleurl.dto;

import static com.thiagoferraz.littleurl.util.Constants.MAX_LONG_URL_SIZE;

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
	@Length(max = MAX_LONG_URL_SIZE)
	private String longUrl;

	public UrlDTO() {
		this.longUrl = "";
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((longUrl == null) ? 0 : longUrl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UrlDTO other = (UrlDTO) obj;
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		if (longUrl == null) {
			if (other.longUrl != null) {
				return false;
			}
		} else if (!longUrl.equals(other.longUrl)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "UrlDTO [code=" + code + ", longUrl=" + longUrl + "]";
	}

}
