package com.thiagoferraz.littleurl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagoferraz.littleurl.domain.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, String>{

}
