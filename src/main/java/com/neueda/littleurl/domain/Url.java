package com.neueda.littleurl.domain;

import static com.neueda.littleurl.util.Constants.MAX_LONG_URL_SIZE;
import static com.neueda.littleurl.util.Constants.URL_CODE_SIZE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PreUpdate;

@Entity
public class Url implements Serializable {
	private static final long serialVersionUID = 1L;
		
	@Id
	@Column(length = URL_CODE_SIZE)  
	private String code;
	
	@Column(length = MAX_LONG_URL_SIZE)  
	private String longUrl;
	
	private Date created = new Date();
	private Date updated = new Date();
		
	public Url() {
	}
	
	public Url(String code, String longUrl) {
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
	
	public Date getCreated() {
		return created;
	}

	public Date getUpdated() {
		return updated;
	}

	@PreUpdate
	public void setLastUpdate() {  this.updated = new Date(); }

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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Url other = (Url) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (longUrl == null) {
			if (other.longUrl != null)
				return false;
		} else if (!longUrl.equals(other.longUrl))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Url [code=" + code + ", longUrl=" + longUrl + ", created=" + created + ", updated=" + updated + "]";
	}

}
