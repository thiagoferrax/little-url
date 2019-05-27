package com.neueda.littleurl.resources;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.neueda.littleurl.dto.StatisticsDTO;
import com.neueda.littleurl.dto.StatisticsSummaryDTO;
import com.neueda.littleurl.services.StatisticService;
import com.neueda.littleurl.services.UrlService;

@RunWith(SpringRunner.class)
@WebMvcTest
public class StatisticResourcesTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StatisticService statisticService;

	@MockBean
	private UrlService urlService;

	@Test
	public void whenGettingStatisticsReturnsTheExistingOnes() throws Exception {
		// Given
		Long numberOfHits = 3L;

		StatisticsDTO firefox = new StatisticsDTO("Firefox", 1L);
		StatisticsDTO chrome = new StatisticsDTO("Chrome", 2L);
		List<StatisticsDTO> browsers = Arrays.asList(new StatisticsDTO[] { firefox, chrome });

		StatisticsDTO computer = new StatisticsDTO("Computer", 3L);
		List<StatisticsDTO> devicesTypes = Arrays.asList(new StatisticsDTO[] { computer });

		StatisticsDTO linux = new StatisticsDTO("Linux", 3L);
		List<StatisticsDTO> operatingSystems = Arrays.asList(new StatisticsDTO[] { linux });

		StatisticsSummaryDTO statisticsSummary = new StatisticsSummaryDTO();
		statisticsSummary.setNumberOfHits(numberOfHits);
		statisticsSummary.setBrowsers(browsers);
		statisticsSummary.setDevicesTypes(devicesTypes);
		statisticsSummary.setOperatingSystems(operatingSystems);
		
		given(statisticService.getStatisticsSummary()).willReturn(statisticsSummary);

		// When and Then
		this.mockMvc.perform(get("/statistics/summary")).andExpect(status().isOk()).andExpect(content().json(
				"{'numberOfHits':3,'browsers':[{'name':'Firefox','total':1},{'name':'Chrome','total':2}],'devicesTypes':[{'name':'Computer','total':3}],'operatingSystems':[{'name':'Linux','total':3}]}"));
	}

	@Test
	public void whenGettingStatisticsByCodeReturnsTheExistingOnes() throws Exception {
		// Given
		String code = "3077yW";
		Long numberOfHits = 3L;

		StatisticsDTO firefox = new StatisticsDTO("Firefox", 1L);
		StatisticsDTO chrome = new StatisticsDTO("Chrome", 2L);
		List<StatisticsDTO> browsers = Arrays.asList(new StatisticsDTO[] { firefox, chrome });

		StatisticsDTO computer = new StatisticsDTO("Computer", 3L);
		List<StatisticsDTO> devicesTypes = Arrays.asList(new StatisticsDTO[] { computer });

		StatisticsDTO linux = new StatisticsDTO("Linux", 3L);
		List<StatisticsDTO> operatingSystems = Arrays.asList(new StatisticsDTO[] { linux });

		StatisticsSummaryDTO statisticsSummary = new StatisticsSummaryDTO();
		statisticsSummary.setNumberOfHits(numberOfHits);
		statisticsSummary.setBrowsers(browsers);
		statisticsSummary.setDevicesTypes(devicesTypes);
		statisticsSummary.setOperatingSystems(operatingSystems);
		
		given(statisticService.getStatisticsSummaryByCode(code)).willReturn(statisticsSummary);

		// When and Then
		this.mockMvc.perform(get("/statistics/summary/" + code)).andExpect(status().isOk()).andExpect(content().json(
				"{'numberOfHits':3,'browsers':[{'name':'Firefox','total':1},{'name':'Chrome','total':2}],'devicesTypes':[{'name':'Computer','total':3}],'operatingSystems':[{'name':'Linux','total':3}]}"));
	}
}