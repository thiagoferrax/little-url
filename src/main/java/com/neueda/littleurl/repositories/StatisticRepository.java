package com.neueda.littleurl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neueda.littleurl.domain.Statistic;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long>{

}
