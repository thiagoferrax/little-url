package com.neueda.littleurl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.neueda.littleurl.domain.Statistic;
import com.neueda.littleurl.dto.StatisticsDTO;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    @Query("select count(s.id) from Statistic s")
	Long getNumberOfHits();
	
    @Query("select count(s.id) from Statistic s where s.url.code = :code")
	Long getNumberOfHitsByCode(@Param("code") String code);
	
    @Query("select new com.neueda.littleurl.dto.StatisticsDTO(s.browser, count(s)) from Statistic s group by s.browser")
    List<StatisticsDTO> getBrowsers();

    @Query("select new com.neueda.littleurl.dto.StatisticsDTO(s.deviceType, count(s)) from Statistic s group by s.deviceType")
    List<StatisticsDTO> getDevicesTypes();

    @Query("select new com.neueda.littleurl.dto.StatisticsDTO(s.operatingSystem, count(s)) from Statistic s group by s.operatingSystem")
    List<StatisticsDTO> getOperatingSystems();

    @Query("select new com.neueda.littleurl.dto.StatisticsDTO(s.browser, count(s)) from Statistic s where s.url.code = :code group by s.browser")
    List<StatisticsDTO> getBrowsersByCode(@Param("code") String code);

    @Query("select new com.neueda.littleurl.dto.StatisticsDTO(s.deviceType, count(s)) from Statistic s where s.url.code = :code group by s.deviceType")
    List<StatisticsDTO> getDevicesTypesByCode(@Param("code") String code);

    @Query("select new com.neueda.littleurl.dto.StatisticsDTO(s.operatingSystem, count(s)) from Statistic s where s.url.code = :code group by s.operatingSystem")
	List<StatisticsDTO> getOperatingSystemsByCode(@Param("code") String code);
}
