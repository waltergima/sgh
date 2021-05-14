package br.org.ccb.sgh.integrated.controller;

import static org.hamcrest.CoreMatchers.equalTo;
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
import br.org.ccb.sgh.controller.GuestController;
import br.org.ccb.sgh.controller.handler.ResourceExceptionHandler;
import br.org.ccb.sgh.http.dto.GuestDto;

@Sql(scripts = {"classpath:sql/drop.sql", "classpath:sql/create.sql", "classpath:sql/populate.sql"})
@SpringBootTest
@ExtendWith(SpringExtension.class)
@SpringJUnitWebConfig
class GuestControllerTest {

	private static final String URL = "/guests";
	private static final String BY_ID = URL.concat("/1");
	private MockMvc mockMvc;
	private AutoCloseable closeable;

	@Autowired
	private GuestController guestController;

	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(guestController)
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
				.perform(post(URL).content(new ObjectMapper().writeValueAsString(TestUtils.createGuestDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(header().exists("location"))
				.andExpect(header().string("location", endsWith(URL.concat("/8"))));
	}
	
	@Test
	void findByIdSuccessTest() throws Exception {
		this.mockMvc.perform(get(BY_ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("id", is(1)))
		.andExpect(jsonPath("name", is("Guest 1")))
		.andExpect(jsonPath("dateOfBirth[0]", is(2000)))
		.andExpect(jsonPath("dateOfBirth[1]", is(12)))
		.andExpect(jsonPath("dateOfBirth[2]", is(11)))
		.andExpect(jsonPath("phoneNumber", is("1658741258")))
		.andExpect(jsonPath("celNumber", is("18966547890")))
		.andExpect(jsonPath("rg", is("126547854")))
		.andExpect(jsonPath("cpf", is("19290228083")))
		.andExpect(jsonPath("ministery", is(true)))
		.andExpect(jsonPath("baptized", is(true)))
		.andExpect(jsonPath("dateOfBaptism[0]", is(2019)))
		.andExpect(jsonPath("dateOfBaptism[1]", is(1)))
		.andExpect(jsonPath("dateOfBaptism[2]", is(1)))
		.andExpect(jsonPath("prayingHouse", is("Central")))
		.andExpect(jsonPath("observation", is("observation")))
		.andExpect(jsonPath("address.id", is(1)))
		.andExpect(jsonPath("address.street", is("Street")))
		.andExpect(jsonPath("address.city", is("Test")))
		.andExpect(jsonPath("address.district", is("District")))
		.andExpect(jsonPath("address.number", is("1F")))
		.andExpect(jsonPath("address.state", is("SP")))
		.andExpect(jsonPath("address.zipCode", is("11111111")));
	}
	
	@Test
	void findAllSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?limit=2")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(equalTo(2)))).andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[0].name", is("Guest 1")))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[0]", is(2000)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[1]", is(12)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[2]", is(11)))
				.andExpect(jsonPath("$.content.[0].phoneNumber", is("1658741258")))
				.andExpect(jsonPath("$.content.[0].celNumber", is("18966547890")))
				.andExpect(jsonPath("$.content.[0].rg", is("126547854")))
				.andExpect(jsonPath("$.content.[0].cpf", is("19290228083")))
				.andExpect(jsonPath("$.content.[0].ministery", is(true)))
				.andExpect(jsonPath("$.content.[0].baptized", is(true)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[0]", is(2019)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[1]", is(1)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[2]", is(1)))
				.andExpect(jsonPath("$.content.[0].prayingHouse", is("Central")))
				.andExpect(jsonPath("$.content.[0].observation", is("observation")))
				.andExpect(jsonPath("$.content.[0].address.id", is(1)))
				.andExpect(jsonPath("$.content.[0].address.street", is("Street")))
				.andExpect(jsonPath("$.content.[0].address.city", is("Test")))
				.andExpect(jsonPath("$.content.[0].address.district", is("District")))
				.andExpect(jsonPath("$.content.[0].address.number", is("1F")))
				.andExpect(jsonPath("$.content.[0].address.state", is("SP")))
				.andExpect(jsonPath("$.content.[0].address.zipCode", is("11111111")))
				.andExpect(jsonPath("$.content.[1].id", is(2)))
				.andExpect(jsonPath("$.content.[1].name", is("Guest 2")))
				.andExpect(jsonPath("$.content.[1].dateOfBirth[0]", is(2001)))
				.andExpect(jsonPath("$.content.[1].dateOfBirth[1]", is(11)))
				.andExpect(jsonPath("$.content.[1].dateOfBirth[2]", is(10)))
				.andExpect(jsonPath("$.content.[1].phoneNumber", is("1166987451")))
				.andExpect(jsonPath("$.content.[1].celNumber", is("11988887746")))
				.andExpect(jsonPath("$.content.[1].rg", is("226547854")))
				.andExpect(jsonPath("$.content.[1].cpf", is("24953683013")))
				.andExpect(jsonPath("$.content.[1].ministery", is(false)))
				.andExpect(jsonPath("$.content.[1].baptized", is(false)))
				.andExpect(jsonPath("$.content.[1].dateOfBaptism[0]", is(2018)))
				.andExpect(jsonPath("$.content.[1].dateOfBaptism[1]", is(12)))
				.andExpect(jsonPath("$.content.[1].dateOfBaptism[2]", is(12)))
				.andExpect(jsonPath("$.content.[1].prayingHouse", is("Central")))
				.andExpect(jsonPath("$.content.[1].observation", is("observation")))
				.andExpect(jsonPath("$.content.[1].address.id", is(2)))
				.andExpect(jsonPath("$.content.[1].address.street", is("Street2")))
				.andExpect(jsonPath("$.content.[1].address.city", is("Test2")))
				.andExpect(jsonPath("$.content.[1].address.district", is("District2")))
				.andExpect(jsonPath("$.content.[1].address.number", is("1G")))
				.andExpect(jsonPath("$.content.[1].address.state", is("MG")))
				.andExpect(jsonPath("$.content.[1].address.zipCode", is("22222222")));
	}
	
	@Test
	void findAllFilteringByIdSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?id=1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(equalTo(1)))).andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[0].name", is("Guest 1")))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[0]", is(2000)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[1]", is(12)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[2]", is(11)))
				.andExpect(jsonPath("$.content.[0].phoneNumber", is("1658741258")))
				.andExpect(jsonPath("$.content.[0].celNumber", is("18966547890")))
				.andExpect(jsonPath("$.content.[0].rg", is("126547854")))
				.andExpect(jsonPath("$.content.[0].cpf", is("19290228083")))
				.andExpect(jsonPath("$.content.[0].ministery", is(true)))
				.andExpect(jsonPath("$.content.[0].baptized", is(true)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[0]", is(2019)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[1]", is(1)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[2]", is(1)))
				.andExpect(jsonPath("$.content.[0].prayingHouse", is("Central")))
				.andExpect(jsonPath("$.content.[0].observation", is("observation")))
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
		this.mockMvc.perform(get(URL.concat("?name=Guest 1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(equalTo(1)))).andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[0].name", is("Guest 1")))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[0]", is(2000)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[1]", is(12)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[2]", is(11)))
				.andExpect(jsonPath("$.content.[0].phoneNumber", is("1658741258")))
				.andExpect(jsonPath("$.content.[0].celNumber", is("18966547890")))
				.andExpect(jsonPath("$.content.[0].rg", is("126547854")))
				.andExpect(jsonPath("$.content.[0].cpf", is("19290228083")))
				.andExpect(jsonPath("$.content.[0].ministery", is(true)))
				.andExpect(jsonPath("$.content.[0].baptized", is(true)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[0]", is(2019)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[1]", is(1)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[2]", is(1)))
				.andExpect(jsonPath("$.content.[0].prayingHouse", is("Central")))
				.andExpect(jsonPath("$.content.[0].observation", is("observation")))
				.andExpect(jsonPath("$.content.[0].address.id", is(1)))
				.andExpect(jsonPath("$.content.[0].address.street", is("Street")))
				.andExpect(jsonPath("$.content.[0].address.city", is("Test")))
				.andExpect(jsonPath("$.content.[0].address.district", is("District")))
				.andExpect(jsonPath("$.content.[0].address.number", is("1F")))
				.andExpect(jsonPath("$.content.[0].address.state", is("SP")))
				.andExpect(jsonPath("$.content.[0].address.zipCode", is("11111111")));
	}
	
	@Test
	void findAllFilteringByDateOfBirthSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?dateOfBirth=11/12/2000")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(equalTo(1)))).andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[0].name", is("Guest 1")))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[0]", is(2000)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[1]", is(12)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[2]", is(11)))
				.andExpect(jsonPath("$.content.[0].phoneNumber", is("1658741258")))
				.andExpect(jsonPath("$.content.[0].celNumber", is("18966547890")))
				.andExpect(jsonPath("$.content.[0].rg", is("126547854")))
				.andExpect(jsonPath("$.content.[0].cpf", is("19290228083")))
				.andExpect(jsonPath("$.content.[0].ministery", is(true)))
				.andExpect(jsonPath("$.content.[0].baptized", is(true)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[0]", is(2019)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[1]", is(1)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[2]", is(1)))
				.andExpect(jsonPath("$.content.[0].prayingHouse", is("Central")))
				.andExpect(jsonPath("$.content.[0].observation", is("observation")))
				.andExpect(jsonPath("$.content.[0].address.id", is(1)))
				.andExpect(jsonPath("$.content.[0].address.street", is("Street")))
				.andExpect(jsonPath("$.content.[0].address.city", is("Test")))
				.andExpect(jsonPath("$.content.[0].address.district", is("District")))
				.andExpect(jsonPath("$.content.[0].address.number", is("1F")))
				.andExpect(jsonPath("$.content.[0].address.state", is("SP")))
				.andExpect(jsonPath("$.content.[0].address.zipCode", is("11111111")));
	}
	
	@Test
	void findAllFilteringByDateOfBaptismSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?dateOfBaptism=01/01/2019")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(equalTo(1)))).andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[0].name", is("Guest 1")))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[0]", is(2000)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[1]", is(12)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[2]", is(11)))
				.andExpect(jsonPath("$.content.[0].phoneNumber", is("1658741258")))
				.andExpect(jsonPath("$.content.[0].celNumber", is("18966547890")))
				.andExpect(jsonPath("$.content.[0].rg", is("126547854")))
				.andExpect(jsonPath("$.content.[0].cpf", is("19290228083")))
				.andExpect(jsonPath("$.content.[0].ministery", is(true)))
				.andExpect(jsonPath("$.content.[0].baptized", is(true)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[0]", is(2019)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[1]", is(1)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[2]", is(1)))
				.andExpect(jsonPath("$.content.[0].prayingHouse", is("Central")))
				.andExpect(jsonPath("$.content.[0].observation", is("observation")))
				.andExpect(jsonPath("$.content.[0].address.id", is(1)))
				.andExpect(jsonPath("$.content.[0].address.street", is("Street")))
				.andExpect(jsonPath("$.content.[0].address.city", is("Test")))
				.andExpect(jsonPath("$.content.[0].address.district", is("District")))
				.andExpect(jsonPath("$.content.[0].address.number", is("1F")))
				.andExpect(jsonPath("$.content.[0].address.state", is("SP")))
				.andExpect(jsonPath("$.content.[0].address.zipCode", is("11111111")));
	}
	
	@Test
	void findAllFilteringByRgSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?rg=126547854")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(equalTo(1)))).andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[0].name", is("Guest 1")))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[0]", is(2000)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[1]", is(12)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[2]", is(11)))
				.andExpect(jsonPath("$.content.[0].phoneNumber", is("1658741258")))
				.andExpect(jsonPath("$.content.[0].celNumber", is("18966547890")))
				.andExpect(jsonPath("$.content.[0].rg", is("126547854")))
				.andExpect(jsonPath("$.content.[0].cpf", is("19290228083")))
				.andExpect(jsonPath("$.content.[0].ministery", is(true)))
				.andExpect(jsonPath("$.content.[0].baptized", is(true)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[0]", is(2019)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[1]", is(1)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[2]", is(1)))
				.andExpect(jsonPath("$.content.[0].prayingHouse", is("Central")))
				.andExpect(jsonPath("$.content.[0].observation", is("observation")))
				.andExpect(jsonPath("$.content.[0].address.id", is(1)))
				.andExpect(jsonPath("$.content.[0].address.street", is("Street")))
				.andExpect(jsonPath("$.content.[0].address.city", is("Test")))
				.andExpect(jsonPath("$.content.[0].address.district", is("District")))
				.andExpect(jsonPath("$.content.[0].address.number", is("1F")))
				.andExpect(jsonPath("$.content.[0].address.state", is("SP")))
				.andExpect(jsonPath("$.content.[0].address.zipCode", is("11111111")));
	}
	
	@Test
	void findAllFilteringByCpfSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?cpf=19290228083")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(equalTo(1)))).andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[0].name", is("Guest 1")))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[0]", is(2000)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[1]", is(12)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[2]", is(11)))
				.andExpect(jsonPath("$.content.[0].phoneNumber", is("1658741258")))
				.andExpect(jsonPath("$.content.[0].celNumber", is("18966547890")))
				.andExpect(jsonPath("$.content.[0].rg", is("126547854")))
				.andExpect(jsonPath("$.content.[0].cpf", is("19290228083")))
				.andExpect(jsonPath("$.content.[0].ministery", is(true)))
				.andExpect(jsonPath("$.content.[0].baptized", is(true)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[0]", is(2019)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[1]", is(1)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[2]", is(1)))
				.andExpect(jsonPath("$.content.[0].prayingHouse", is("Central")))
				.andExpect(jsonPath("$.content.[0].observation", is("observation")))
				.andExpect(jsonPath("$.content.[0].address.id", is(1)))
				.andExpect(jsonPath("$.content.[0].address.street", is("Street")))
				.andExpect(jsonPath("$.content.[0].address.city", is("Test")))
				.andExpect(jsonPath("$.content.[0].address.district", is("District")))
				.andExpect(jsonPath("$.content.[0].address.number", is("1F")))
				.andExpect(jsonPath("$.content.[0].address.state", is("SP")))
				.andExpect(jsonPath("$.content.[0].address.zipCode", is("11111111")));
	}
	
	@Test
	void findAllFilteringByPhoneNumberSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?phoneNumber=1658741258")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(equalTo(1)))).andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[0].name", is("Guest 1")))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[0]", is(2000)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[1]", is(12)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[2]", is(11)))
				.andExpect(jsonPath("$.content.[0].phoneNumber", is("1658741258")))
				.andExpect(jsonPath("$.content.[0].celNumber", is("18966547890")))
				.andExpect(jsonPath("$.content.[0].rg", is("126547854")))
				.andExpect(jsonPath("$.content.[0].cpf", is("19290228083")))
				.andExpect(jsonPath("$.content.[0].ministery", is(true)))
				.andExpect(jsonPath("$.content.[0].baptized", is(true)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[0]", is(2019)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[1]", is(1)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[2]", is(1)))
				.andExpect(jsonPath("$.content.[0].prayingHouse", is("Central")))
				.andExpect(jsonPath("$.content.[0].observation", is("observation")))
				.andExpect(jsonPath("$.content.[0].address.id", is(1)))
				.andExpect(jsonPath("$.content.[0].address.street", is("Street")))
				.andExpect(jsonPath("$.content.[0].address.city", is("Test")))
				.andExpect(jsonPath("$.content.[0].address.district", is("District")))
				.andExpect(jsonPath("$.content.[0].address.number", is("1F")))
				.andExpect(jsonPath("$.content.[0].address.state", is("SP")))
				.andExpect(jsonPath("$.content.[0].address.zipCode", is("11111111")));
	}
	
	@Test
	void findAllFilteringByMinisterySuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?ministery=true&limit=1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(equalTo(1)))).andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[0].name", is("Guest 1")))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[0]", is(2000)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[1]", is(12)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[2]", is(11)))
				.andExpect(jsonPath("$.content.[0].phoneNumber", is("1658741258")))
				.andExpect(jsonPath("$.content.[0].celNumber", is("18966547890")))
				.andExpect(jsonPath("$.content.[0].rg", is("126547854")))
				.andExpect(jsonPath("$.content.[0].cpf", is("19290228083")))
				.andExpect(jsonPath("$.content.[0].ministery", is(true)))
				.andExpect(jsonPath("$.content.[0].baptized", is(true)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[0]", is(2019)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[1]", is(1)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[2]", is(1)))
				.andExpect(jsonPath("$.content.[0].prayingHouse", is("Central")))
				.andExpect(jsonPath("$.content.[0].observation", is("observation")))
				.andExpect(jsonPath("$.content.[0].address.id", is(1)))
				.andExpect(jsonPath("$.content.[0].address.street", is("Street")))
				.andExpect(jsonPath("$.content.[0].address.city", is("Test")))
				.andExpect(jsonPath("$.content.[0].address.district", is("District")))
				.andExpect(jsonPath("$.content.[0].address.number", is("1F")))
				.andExpect(jsonPath("$.content.[0].address.state", is("SP")))
				.andExpect(jsonPath("$.content.[0].address.zipCode", is("11111111")));
	}
	
	@Test
	void findAllFilteringByPrayingHouseSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?prayingHouse=Central&limit=1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(equalTo(1)))).andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[0].name", is("Guest 1")))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[0]", is(2000)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[1]", is(12)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[2]", is(11)))
				.andExpect(jsonPath("$.content.[0].phoneNumber", is("1658741258")))
				.andExpect(jsonPath("$.content.[0].celNumber", is("18966547890")))
				.andExpect(jsonPath("$.content.[0].rg", is("126547854")))
				.andExpect(jsonPath("$.content.[0].cpf", is("19290228083")))
				.andExpect(jsonPath("$.content.[0].ministery", is(true)))
				.andExpect(jsonPath("$.content.[0].baptized", is(true)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[0]", is(2019)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[1]", is(1)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[2]", is(1)))
				.andExpect(jsonPath("$.content.[0].prayingHouse", is("Central")))
				.andExpect(jsonPath("$.content.[0].observation", is("observation")))
				.andExpect(jsonPath("$.content.[0].address.id", is(1)))
				.andExpect(jsonPath("$.content.[0].address.street", is("Street")))
				.andExpect(jsonPath("$.content.[0].address.city", is("Test")))
				.andExpect(jsonPath("$.content.[0].address.district", is("District")))
				.andExpect(jsonPath("$.content.[0].address.number", is("1F")))
				.andExpect(jsonPath("$.content.[0].address.state", is("SP")))
				.andExpect(jsonPath("$.content.[0].address.zipCode", is("11111111")));
	}
	
	@Test
	void findAllFilteringByObservationSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?observation=observation&limit=1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(equalTo(1)))).andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[0].name", is("Guest 1")))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[0]", is(2000)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[1]", is(12)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[2]", is(11)))
				.andExpect(jsonPath("$.content.[0].phoneNumber", is("1658741258")))
				.andExpect(jsonPath("$.content.[0].celNumber", is("18966547890")))
				.andExpect(jsonPath("$.content.[0].rg", is("126547854")))
				.andExpect(jsonPath("$.content.[0].cpf", is("19290228083")))
				.andExpect(jsonPath("$.content.[0].ministery", is(true)))
				.andExpect(jsonPath("$.content.[0].baptized", is(true)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[0]", is(2019)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[1]", is(1)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[2]", is(1)))
				.andExpect(jsonPath("$.content.[0].prayingHouse", is("Central")))
				.andExpect(jsonPath("$.content.[0].observation", is("observation")))
				.andExpect(jsonPath("$.content.[0].address.id", is(1)))
				.andExpect(jsonPath("$.content.[0].address.street", is("Street")))
				.andExpect(jsonPath("$.content.[0].address.city", is("Test")))
				.andExpect(jsonPath("$.content.[0].address.district", is("District")))
				.andExpect(jsonPath("$.content.[0].address.number", is("1F")))
				.andExpect(jsonPath("$.content.[0].address.state", is("SP")))
				.andExpect(jsonPath("$.content.[0].address.zipCode", is("11111111")));
	}
	
	@Test
	void findAllFilteringByReservationSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?reservationId=1&limit=1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(equalTo(1)))).andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[0].name", is("Guest 1")))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[0]", is(2000)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[1]", is(12)))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[2]", is(11)))
				.andExpect(jsonPath("$.content.[0].phoneNumber", is("1658741258")))
				.andExpect(jsonPath("$.content.[0].celNumber", is("18966547890")))
				.andExpect(jsonPath("$.content.[0].rg", is("126547854")))
				.andExpect(jsonPath("$.content.[0].cpf", is("19290228083")))
				.andExpect(jsonPath("$.content.[0].ministery", is(true)))
				.andExpect(jsonPath("$.content.[0].baptized", is(true)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[0]", is(2019)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[1]", is(1)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[2]", is(1)))
				.andExpect(jsonPath("$.content.[0].prayingHouse", is("Central")))
				.andExpect(jsonPath("$.content.[0].observation", is("observation")))
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
		GuestDto guestDto = TestUtils.createUpdateGuestDto();
		this.mockMvc
				.perform(put(BY_ID).content(new ObjectMapper().writeValueAsString(guestDto))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
		this.mockMvc.perform(get(BY_ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("id", is(1)))
		.andExpect(jsonPath("name", is(guestDto.getName())))
		.andExpect(jsonPath("dateOfBirth[0]", is(guestDto.getDateOfBirth().getYear())))
		.andExpect(jsonPath("dateOfBirth[1]", is(guestDto.getDateOfBirth().getMonthValue())))
		.andExpect(jsonPath("dateOfBirth[2]", is(guestDto.getDateOfBirth().getDayOfMonth())))
		.andExpect(jsonPath("phoneNumber", is(guestDto.getPhoneNumber())))
		.andExpect(jsonPath("celNumber", is(guestDto.getCelNumber())))
		.andExpect(jsonPath("rg", is(guestDto.getRg())))
		.andExpect(jsonPath("cpf", is(guestDto.getCpf())))
		.andExpect(jsonPath("ministery", is(guestDto.getMinistery())))
		.andExpect(jsonPath("baptized", is(guestDto.getBaptized())))
		.andExpect(jsonPath("dateOfBaptism[0]", is(guestDto.getDateOfBaptism().getYear())))
		.andExpect(jsonPath("dateOfBaptism[1]", is(guestDto.getDateOfBaptism().getMonthValue())))
		.andExpect(jsonPath("dateOfBaptism[2]", is(guestDto.getDateOfBaptism().getDayOfMonth())))
		.andExpect(jsonPath("prayingHouse", is(guestDto.getPrayingHouse())))
		.andExpect(jsonPath("observation", is(guestDto.getObservation())))
		.andExpect(jsonPath("address.id", is(1)))
		.andExpect(jsonPath("address.street", is(guestDto.getAddress().getStreet())))
		.andExpect(jsonPath("address.city", is(guestDto.getAddress().getCity())))
		.andExpect(jsonPath("address.district", is(guestDto.getAddress().getDistrict())))
		.andExpect(jsonPath("address.number", is(guestDto.getAddress().getNumber())))
		.andExpect(jsonPath("address.state", is(guestDto.getAddress().getState())))
		.andExpect(jsonPath("address.zipCode", is(guestDto.getAddress().getZipCode())));
		
	}
	
	@Test
	void updateNotFoundTest() throws Exception {
		GuestDto guestDto = TestUtils.createUpdateGuestDto();
		this.mockMvc
				.perform(put(URL.concat("/99999999")).content(new ObjectMapper().writeValueAsString(guestDto))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void deleteSuccessTest() throws Exception {
		this.mockMvc
		.perform(post(URL).content(new ObjectMapper().writeValueAsString(TestUtils.createGuestDto()))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated()).andExpect(header().exists("location"))
		.andExpect(header().string("location", endsWith(URL.concat("/8"))));
		String byId = URL.concat("/8");
		this.mockMvc
				.perform(delete(byId))
				.andExpect(status().isNoContent());
		this.mockMvc.perform(get(byId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
		
	}
}
