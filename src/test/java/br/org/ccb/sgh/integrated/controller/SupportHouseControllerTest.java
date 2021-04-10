package br.org.ccb.sgh.integrated.controller;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.ccb.sgh.TestUtils;
import br.org.ccb.sgh.controller.SupportHouseController;
import br.org.ccb.sgh.controller.handler.ResourceExceptionHandler;
import br.org.ccb.sgh.http.dto.SupportHouseDto;

@Sql(scripts = {"classpath:sql/drop.sql", "classpath:sql/create.sql", "classpath:sql/populate.sql"})
@SpringBootTest
@ExtendWith(SpringExtension.class)
@SpringJUnitWebConfig
public class SupportHouseControllerTest {
	
	private static final String URL = "/supporthouses";
	private static final String BY_ID = URL.concat("/1");
	private MockMvc mockMvc;
	private AutoCloseable closeable;

	@Autowired
	private SupportHouseController supportHouseController;
	
	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(supportHouseController)
				.setControllerAdvice(new ResourceExceptionHandler()).setLocaleResolver(new FixedLocaleResolver(new Locale("pt", "BR"))).build();
		closeable = MockitoAnnotations.openMocks(this);
	}
	
	@AfterEach
	void releaseMocks() throws Exception {
		closeable.close();
	}
	
	@Test
	void saveSuccessTest() throws Exception {
		this.mockMvc
				.perform(post(URL).content(new ObjectMapper().writeValueAsString(TestUtils.createSupportHouseDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(header().exists("location"))
				.andExpect(header().string("location", endsWith(URL.concat("/3"))));
	}
	
	@Test
	void findByIdSuccessTest() throws Exception {
		this.mockMvc.perform(get(BY_ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("id", is(1))).andExpect(jsonPath("name", is("Test")))
		.andExpect(jsonPath("cnpj", is("48183050000188"))).andExpect(jsonPath("address.id", is(1)))
		.andExpect(jsonPath("address.street", is("Street"))).andExpect(jsonPath("address.city", is("Test")))
		.andExpect(jsonPath("address.district", is("District"))).andExpect(jsonPath("address.number", is("1F")))
		.andExpect(jsonPath("address.state", is("SP"))).andExpect(jsonPath("address.zipCode", is("11111111")));
	}
	
	@Test
	void findAllSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?limit=1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Test")))
		.andExpect(jsonPath("$.content.[0].cnpj", is("48183050000188")))
		.andExpect(jsonPath("$.content.[0].address.id", is(1)))
		.andExpect(jsonPath("$.content.[0].address.street", is("Street")))
		.andExpect(jsonPath("$.content.[0].address.city", is("Test")))
		.andExpect(jsonPath("$.content.[0].address.district", is("District")))
		.andExpect(jsonPath("$.content.[0].address.number", is("1F")))
		.andExpect(jsonPath("$.content.[0].address.state", is("SP")))
		.andExpect(jsonPath("$.content.[0].address.zipCode", is("11111111")));
	}
	
	@Test
	void findFilteringByIduccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?id=1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Test")))
		.andExpect(jsonPath("$.content.[0].cnpj", is("48183050000188")))
		.andExpect(jsonPath("$.content.[0].address.id", is(1)))
		.andExpect(jsonPath("$.content.[0].address.street", is("Street")))
		.andExpect(jsonPath("$.content.[0].address.city", is("Test")))
		.andExpect(jsonPath("$.content.[0].address.district", is("District")))
		.andExpect(jsonPath("$.content.[0].address.number", is("1F")))
		.andExpect(jsonPath("$.content.[0].address.state", is("SP")))
		.andExpect(jsonPath("$.content.[0].address.zipCode", is("11111111")));
	}
	
	@Test
	void findAllFilteringByNameSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?name=Test")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(2))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Test")))
		.andExpect(jsonPath("$.content.[0].cnpj", is("48183050000188")))
		.andExpect(jsonPath("$.content.[0].address.id", is(1)))
		.andExpect(jsonPath("$.content.[0].address.street", is("Street")))
		.andExpect(jsonPath("$.content.[0].address.city", is("Test")))
		.andExpect(jsonPath("$.content.[0].address.district", is("District")))
		.andExpect(jsonPath("$.content.[0].address.number", is("1F")))
		.andExpect(jsonPath("$.content.[0].address.state", is("SP")))
		.andExpect(jsonPath("$.content.[0].address.zipCode", is("11111111")));
	}
	
	@Test
	void findAllFilteringByCnpjSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?cnpj=4818305000018")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Test")))
		.andExpect(jsonPath("$.content.[0].cnpj", is("48183050000188")))
		.andExpect(jsonPath("$.content.[0].address.id", is(1)))
		.andExpect(jsonPath("$.content.[0].address.street", is("Street")))
		.andExpect(jsonPath("$.content.[0].address.city", is("Test")))
		.andExpect(jsonPath("$.content.[0].address.district", is("District")))
		.andExpect(jsonPath("$.content.[0].address.number", is("1F")))
		.andExpect(jsonPath("$.content.[0].address.state", is("SP")))
		.andExpect(jsonPath("$.content.[0].address.zipCode", is("11111111")));
	}
	
	@Test
	void findAllFilteringByCitySuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?city=Test4")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1))).andExpect(jsonPath("$.content.[0].id", is(2)))
		.andExpect(jsonPath("$.content.[0].name", is("Test 2")))
		.andExpect(jsonPath("$.content.[0].cnpj", is("76085751000110")))
		.andExpect(jsonPath("$.content.[0].address.id", is(4)))
		.andExpect(jsonPath("$.content.[0].address.street", is("Street4")))
		.andExpect(jsonPath("$.content.[0].address.city", is("Test4")))
		.andExpect(jsonPath("$.content.[0].address.district", is("District4")))
		.andExpect(jsonPath("$.content.[0].address.number", is("1I")))
		.andExpect(jsonPath("$.content.[0].address.state", is("AM")))
		.andExpect(jsonPath("$.content.[0].address.zipCode", is("44444444")));
	}
	
	@Test
	void findAllFilteringByStateSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?state=SP")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Test")))
		.andExpect(jsonPath("$.content.[0].cnpj", is("48183050000188")))
		.andExpect(jsonPath("$.content.[0].address.id", is(1)))
		.andExpect(jsonPath("$.content.[0].address.street", is("Street")))
		.andExpect(jsonPath("$.content.[0].address.city", is("Test")))
		.andExpect(jsonPath("$.content.[0].address.district", is("District")))
		.andExpect(jsonPath("$.content.[0].address.number", is("1F")))
		.andExpect(jsonPath("$.content.[0].address.state", is("SP")))
		.andExpect(jsonPath("$.content.[0].address.zipCode", is("11111111")));
	}
	
	@Test
	void updateSuccessTest() throws Exception {
		SupportHouseDto supportHouseDto = TestUtils.createSupportHouseUpdateDto();
		this.mockMvc
				.perform(put(BY_ID).content(new ObjectMapper().writeValueAsString(supportHouseDto))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
		this.mockMvc.perform(get(BY_ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("id", is(1))).andExpect(jsonPath("name", is(supportHouseDto.getName())))
		.andExpect(jsonPath("cnpj", is(supportHouseDto.getCnpj())))
		.andExpect(jsonPath("address.street", is(supportHouseDto.getAddress().getStreet()))).andExpect(jsonPath("address.city", is(supportHouseDto.getAddress().getCity())))
		.andExpect(jsonPath("address.district", is(supportHouseDto.getAddress().getDistrict()))).andExpect(jsonPath("address.number", is(supportHouseDto.getAddress().getNumber())))
		.andExpect(jsonPath("address.state", is(supportHouseDto.getAddress().getState()))).andExpect(jsonPath("address.zipCode", is(supportHouseDto.getAddress().getZipCode())));
		
	}
	
	@Test
	void updateNotFoundTest() throws Exception {
		this.mockMvc
				.perform(put(URL.concat("/99999999")).content(new ObjectMapper().writeValueAsString(TestUtils.createSupportHouseUpdateDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void deleteSuccessTest() throws Exception {
		String byId = URL.concat("/2");
		this.mockMvc
				.perform(delete(byId))
				.andExpect(status().isNoContent());
		this.mockMvc.perform(get(byId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
		
	}
}
