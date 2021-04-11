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
import br.org.ccb.sgh.controller.RoomController;
import br.org.ccb.sgh.controller.handler.ResourceExceptionHandler;
import br.org.ccb.sgh.http.dto.RoomDto;

@Sql(scripts = {"classpath:sql/drop.sql", "classpath:sql/create.sql", "classpath:sql/populate.sql"})
@SpringBootTest
@ExtendWith(SpringExtension.class)
@SpringJUnitWebConfig
public class RoomControllerTest {
	
	private static final String URL = "/rooms";
	private static final String BY_ID = URL.concat("/1");
	private MockMvc mockMvc;
	private AutoCloseable closeable;

	@Autowired
	private RoomController roomController;
	
	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(roomController)
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
				.perform(post(URL).content(new ObjectMapper().writeValueAsString(TestUtils.createRoomDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(header().exists("location"))
				.andExpect(header().string("location", endsWith(URL.concat("/3"))));
	}
	
	@Test
	void findByIdSuccessTest() throws Exception {
		this.mockMvc.perform(get(BY_ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("id", is(1))).andExpect(jsonPath("name", is("Room 1")))
		.andExpect(jsonPath("floor", is("1"))).andExpect(jsonPath("number", is("1A")))
		.andExpect(jsonPath("numberOfBeds", is(4))).andExpect(jsonPath("available", is(true)));
	}
	
	@Test
	void findAllSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?limit=1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Room 1")))
		.andExpect(jsonPath("$.content.[0].floor", is("1")))
		.andExpect(jsonPath("$.content.[0].number", is("1A")))
		.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
		.andExpect(jsonPath("$.content.[0].available", is(true)));
	}
	
	@Test
	void findAllFilteringByIdSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?id=1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Room 1")))
		.andExpect(jsonPath("$.content.[0].floor", is("1")))
		.andExpect(jsonPath("$.content.[0].number", is("1A")))
		.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
		.andExpect(jsonPath("$.content.[0].available", is(true)));
		
	}
	
	@Test
	void findAllFilteringByNameSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?name=Room 1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Room 1")))
		.andExpect(jsonPath("$.content.[0].floor", is("1")))
		.andExpect(jsonPath("$.content.[0].number", is("1A")))
		.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
		.andExpect(jsonPath("$.content.[0].available", is(true)));
	}
	
	@Test
	void findAllFilteringByFloorSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?floor=1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Room 1")))
		.andExpect(jsonPath("$.content.[0].floor", is("1")))
		.andExpect(jsonPath("$.content.[0].number", is("1A")))
		.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
		.andExpect(jsonPath("$.content.[0].available", is(true)));
	}
	
	@Test
	void findAllFilteringByNumberSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?number=1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Room 1")))
		.andExpect(jsonPath("$.content.[0].floor", is("1")))
		.andExpect(jsonPath("$.content.[0].number", is("1A")))
		.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
		.andExpect(jsonPath("$.content.[0].available", is(true)));
	}
	
	@Test
	void findAllFilteringByNumberOfBedsSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?numberOfBeds=4")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Room 1")))
		.andExpect(jsonPath("$.content.[0].floor", is("1")))
		.andExpect(jsonPath("$.content.[0].number", is("1A")))
		.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
		.andExpect(jsonPath("$.content.[0].available", is(true)));
	}
	
	@Test
	void findAllFilteringByAvailableSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?available=false")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(2))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Room 1")))
		.andExpect(jsonPath("$.content.[0].floor", is("1")))
		.andExpect(jsonPath("$.content.[0].number", is("1A")))
		.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
		.andExpect(jsonPath("$.content.[0].available", is(false)))
		.andExpect(jsonPath("$.content.[1].id", is(2)))
		.andExpect(jsonPath("$.content.[1].name", is("Room 2")))
		.andExpect(jsonPath("$.content.[1].floor", is("2")))
		.andExpect(jsonPath("$.content.[1].number", is("2A")))
		.andExpect(jsonPath("$.content.[1].numberOfBeds", is(1)))
		.andExpect(jsonPath("$.content.[1].available", is(false)));
	}
	
	@Test
	void findAllFilteringByInitialDateSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?initialDate=01/11/2020")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(2))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Room 1")))
		.andExpect(jsonPath("$.content.[0].floor", is("1")))
		.andExpect(jsonPath("$.content.[0].number", is("1A")))
		.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
		.andExpect(jsonPath("$.content.[0].available", is(true)))
		.andExpect(jsonPath("$.content.[1].id", is(2)))
		.andExpect(jsonPath("$.content.[1].name", is("Room 2")))
		.andExpect(jsonPath("$.content.[1].floor", is("2")))
		.andExpect(jsonPath("$.content.[1].number", is("2A")))
		.andExpect(jsonPath("$.content.[1].numberOfBeds", is(1)))
		.andExpect(jsonPath("$.content.[1].available", is(true)));
	}
	
	@Test
	void findAllFilteringByFinalDateSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?finalDate=09/11/2020")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(2))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Room 1")))
		.andExpect(jsonPath("$.content.[0].floor", is("1")))
		.andExpect(jsonPath("$.content.[0].number", is("1A")))
		.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
		.andExpect(jsonPath("$.content.[0].available", is(true)));
	}
	
	@Test
	void findAllFilteringByReservationIdSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?reservationId=1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Room 1")))
		.andExpect(jsonPath("$.content.[0].floor", is("1")))
		.andExpect(jsonPath("$.content.[0].number", is("1A")))
		.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
		.andExpect(jsonPath("$.content.[0].available", is(true)));
	}
	
	@Test
	void findAllFilteringBySupportHouseIdSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?supportHouseId=1")).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.content", hasSize(2)))
				.andExpect(jsonPath("$.content.[0].id", is(1))).andExpect(jsonPath("$.content.[0].name", is("Room 1")))
				.andExpect(jsonPath("$.content.[0].floor", is("1")))
				.andExpect(jsonPath("$.content.[0].number", is("1A")))
				.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
				.andExpect(jsonPath("$.content.[0].available", is(true))).andExpect(jsonPath("$.content.[1].id", is(2)))
				.andExpect(jsonPath("$.content.[1].name", is("Room 2")))
				.andExpect(jsonPath("$.content.[1].floor", is("2")))
				.andExpect(jsonPath("$.content.[1].number", is("2A")))
				.andExpect(jsonPath("$.content.[1].numberOfBeds", is(1)))
				.andExpect(jsonPath("$.content.[1].available", is(true)));
	}
	
	@Test
	void updateSuccessTest() throws Exception {
		RoomDto roomDto = TestUtils.createRoomUpdateDto();
		this.mockMvc
				.perform(put(BY_ID).content(new ObjectMapper().writeValueAsString(roomDto))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
		this.mockMvc.perform(get(BY_ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("id", is(1))).andExpect(jsonPath("name", is(roomDto.getName())))
		.andExpect(jsonPath("floor", is(roomDto.getFloor()))).andExpect(jsonPath("number", is(roomDto.getNumber())))
		.andExpect(jsonPath("numberOfBeds", is(roomDto.getNumberOfBeds())));
		
	}
	
	@Test
	void updateNotFoundTest() throws Exception {
		this.mockMvc
				.perform(put(URL.concat("/99999999")).content(new ObjectMapper().writeValueAsString(TestUtils.createRoomUpdateDto()))
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
