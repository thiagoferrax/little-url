package com.neueda.littleurl.dto;

import java.io.Serializable;
import java.util.List;

public class StatisticsSummaryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long numberOfHits;
	private List<StatisticsDTO> browsers;
	private List<StatisticsDTO> devicesTypes;
	private List<StatisticsDTO> operatingSystems;

	public StatisticsSummaryDTO() {
	}

	public List<StatisticsDTO> getBrowsers() {
		return browsers;
	}

	public void setBrowsers(List<StatisticsDTO> browsers) {
		this.browsers = browsers;
	}

	public List<StatisticsDTO> getDevicesTypes() {
		return devicesTypes;
	}

	public void setDevicesTypes(List<StatisticsDTO> devicesTypes) {
		this.devicesTypes = devicesTypes;
	}

	public List<StatisticsDTO> getOperatingSystems() {
		return operatingSystems;
	}

	public void setOperatingSystems(List<StatisticsDTO> operatingSystems) {
		this.operatingSystems = operatingSystems;
	}

	public Long getNumberOfHits() {
		return numberOfHits;
	}

	public void setNumberOfHits(Long numberOfHits) {
		this.numberOfHits = numberOfHits;
	}
}
