package br.org.ccb.sgh.integrated.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
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

import java.time.LocalDate;
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
				.andExpect(header().string("location", endsWith(URL.concat("/7"))));
	}
	
	@Test
	void findByIdSuccessTest() throws Exception {
		this.mockMvc.perform(get(BY_ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("id", is(1))).andExpect(jsonPath("name", is("Room 1")))
		.andExpect(jsonPath("floor", is("1"))).andExpect(jsonPath("number", is("1A")))
		.andExpect(jsonPath("numberOfBeds", is(4))).andExpect(jsonPath("status", nullValue()));
	}
	
	@Test
	void findAllSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?limit=1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Room 1")))
		.andExpect(jsonPath("$.content.[0].floor", is("1")))
		.andExpect(jsonPath("$.content.[0].number", is("1A")))
		.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
		.andExpect(jsonPath("$.content.[0].status", nullValue()));
	}
	
	@Test
	void findAllFilteringByIdSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?id=1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Room 1")))
		.andExpect(jsonPath("$.content.[0].floor", is("1")))
		.andExpect(jsonPath("$.content.[0].number", is("1A")))
		.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
		.andExpect(jsonPath("$.content.[0].status", nullValue()));
		
	}
	
	@Test
	void findAllFilteringByNameSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?name=Room 1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Room 1")))
		.andExpect(jsonPath("$.content.[0].floor", is("1")))
		.andExpect(jsonPath("$.content.[0].number", is("1A")))
		.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
		.andExpect(jsonPath("$.content.[0].status", nullValue()));
	}
	
	@Test
	void findAllFilteringByFloorSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?floor=1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Room 1")))
		.andExpect(jsonPath("$.content.[0].floor", is("1")))
		.andExpect(jsonPath("$.content.[0].number", is("1A")))
		.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
		.andExpect(jsonPath("$.content.[0].status", nullValue()));
	}
	
	@Test
	void findAllFilteringByNumberSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?number=1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Room 1")))
		.andExpect(jsonPath("$.content.[0].floor", is("1")))
		.andExpect(jsonPath("$.content.[0].number", is("1A")))
		.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
		.andExpect(jsonPath("$.content.[0].status", nullValue()));
	}
	
	@Test
	void findAllFilteringByNumberOfBedsSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?numberOfBeds=4")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Room 1")))
		.andExpect(jsonPath("$.content.[0].floor", is("1")))
		.andExpect(jsonPath("$.content.[0].number", is("1A")))
		.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
		.andExpect(jsonPath("$.content.[0].status", nullValue()));
	}
	
	@Test
	void findAllFilteringByReservationIdSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?reservationId=1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1))).andExpect(jsonPath("$.content.[0].id", is(1)))
		.andExpect(jsonPath("$.content.[0].name", is("Room 1")))
		.andExpect(jsonPath("$.content.[0].floor", is("1")))
		.andExpect(jsonPath("$.content.[0].number", is("1A")))
		.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
		.andExpect(jsonPath("$.content.[0].status", nullValue()));
	}
	
	@Test
	void findAllFilteringBySupportHouseIdSuccessTest() throws Exception {
		this.mockMvc.perform(get(URL.concat("?supportHouseId=1")).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.content", hasSize(6)))
				.andExpect(jsonPath("$.content.[0].id", is(1))).andExpect(jsonPath("$.content.[0].name", is("Room 1")))
				.andExpect(jsonPath("$.content.[0].floor", is("1")))
				.andExpect(jsonPath("$.content.[0].number", is("1A")))
				.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
				.andExpect(jsonPath("$.content.[0].status", nullValue())).andExpect(jsonPath("$.content.[1].id", is(2)))
				.andExpect(jsonPath("$.content.[1].name", is("Room 2")))
				.andExpect(jsonPath("$.content.[1].floor", is("2")))
				.andExpect(jsonPath("$.content.[1].number", is("2A")))
				.andExpect(jsonPath("$.content.[1].numberOfBeds", is(1)))
				.andExpect(jsonPath("$.content.[1].status", nullValue()))
				.andExpect(jsonPath("$.content.[2].id", is(3)))
				.andExpect(jsonPath("$.content.[2].name", is("Room 3")))
				.andExpect(jsonPath("$.content.[2].floor", is("3")))
				.andExpect(jsonPath("$.content.[2].number", is("3A")))
				.andExpect(jsonPath("$.content.[2].numberOfBeds", is(1)))
				.andExpect(jsonPath("$.content.[2].status", nullValue()))
				.andExpect(jsonPath("$.content.[3].id", is(4)))
				.andExpect(jsonPath("$.content.[3].name", is("Room 4")))
				.andExpect(jsonPath("$.content.[3].floor", is("4")))
				.andExpect(jsonPath("$.content.[3].number", is("4A")))
				.andExpect(jsonPath("$.content.[3].numberOfBeds", is(1)))
				.andExpect(jsonPath("$.content.[3].status", nullValue()))
				.andExpect(jsonPath("$.content.[4].id", is(5)))
				.andExpect(jsonPath("$.content.[4].name", is("Room 5")))
				.andExpect(jsonPath("$.content.[4].floor", is("5")))
				.andExpect(jsonPath("$.content.[4].number", is("5A")))
				.andExpect(jsonPath("$.content.[4].numberOfBeds", is(1)))
				.andExpect(jsonPath("$.content.[4].status", nullValue()))
				.andExpect(jsonPath("$.content.[5].id", is(6)))
				.andExpect(jsonPath("$.content.[5].name", is("Room 6")))
				.andExpect(jsonPath("$.content.[5].floor", is("6")))
				.andExpect(jsonPath("$.content.[5].number", is("6A")))
				.andExpect(jsonPath("$.content.[5].numberOfBeds", is(1)))
				.andExpect(jsonPath("$.content.[5].status", nullValue()));
	}
	
	@Test
	void findAllFilteringByReservedStatusSuccessTest() throws Exception {
		
		String content = this.mockMvc
		.perform(get("/reservations")
				.contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
		System.out.println(content);
		
		String date = TestUtils.getFormattedDate(LocalDate.now());
		this.mockMvc
				.perform(get(URL.concat("?status=RESERVED&initialDate=" + date + "&finalDate=" + date))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.content", hasSize(1)))
				.andExpect(jsonPath("$.content.[0].id", is(2)))
				.andExpect(jsonPath("$.content.[0].name", is("Room 2")))
				.andExpect(jsonPath("$.content.[0].floor", is("2")))
				.andExpect(jsonPath("$.content.[0].number", is("2A")))
				.andExpect(jsonPath("$.content.[0].numberOfBeds", is(1)))
				.andExpect(jsonPath("$.content.[0].status", is("RESERVED")));
	}
	
	@Test
	void findAllFilteringByOccupiedStatusSuccessTest() throws Exception {
		String date = TestUtils.getFormattedDate(LocalDate.now());
		this.mockMvc
				.perform(get(URL.concat("?status=OCCUPIED&initialDate=" + date + "&finalDate=" + date))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.content", hasSize(2)))
				.andExpect(jsonPath("$.content.[0].id", is(3)))
				.andExpect(jsonPath("$.content.[0].name", is("Room 3")))
				.andExpect(jsonPath("$.content.[0].floor", is("3")))
				.andExpect(jsonPath("$.content.[0].number", is("3A")))
				.andExpect(jsonPath("$.content.[0].numberOfBeds", is(1)))
				.andExpect(jsonPath("$.content.[0].status", is("OCCUPIED")))
				.andExpect(jsonPath("$.content.[1].id", is(4)))
				.andExpect(jsonPath("$.content.[1].name", is("Room 4")))
				.andExpect(jsonPath("$.content.[1].floor", is("4")))
				.andExpect(jsonPath("$.content.[1].number", is("4A")))
				.andExpect(jsonPath("$.content.[1].numberOfBeds", is(1)))
				.andExpect(jsonPath("$.content.[1].status", is("OCCUPIED")));
	}
	
	@Test
	void findAllFilteringByAvailableStatusSuccessTest() throws Exception {
		String date = TestUtils.getFormattedDate(LocalDate.now());
		this.mockMvc
				.perform(get(URL.concat("?status=AVAILABLE&initialDate=" + date + "&finalDate=" + date))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.content", hasSize(3)))
				.andExpect(jsonPath("$.content.[0].id", is(1))).andExpect(jsonPath("$.content.[0].name", is("Room 1")))
				.andExpect(jsonPath("$.content.[0].floor", is("1")))
				.andExpect(jsonPath("$.content.[0].number", is("1A")))
				.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
				.andExpect(jsonPath("$.content.[0].status", is("AVAILABLE")))
				.andExpect(jsonPath("$.content.[1].id", is(5)))
				.andExpect(jsonPath("$.content.[1].name", is("Room 5")))
				.andExpect(jsonPath("$.content.[1].floor", is("5")))
				.andExpect(jsonPath("$.content.[1].number", is("5A")))
				.andExpect(jsonPath("$.content.[1].numberOfBeds", is(1)))
				.andExpect(jsonPath("$.content.[1].status", is("AVAILABLE")))
				.andExpect(jsonPath("$.content.[2].id", is(6)))
				.andExpect(jsonPath("$.content.[2].name", is("Room 6")))
				.andExpect(jsonPath("$.content.[2].floor", is("6")))
				.andExpect(jsonPath("$.content.[2].number", is("6A")))
				.andExpect(jsonPath("$.content.[2].numberOfBeds", is(1)))
				.andExpect(jsonPath("$.content.[2].status", is("AVAILABLE")));
	}
	
	@Test
	void findAllFilteringByOnlyInitialDateErrorTest() throws Exception {
		String date = TestUtils.getFormattedDate(LocalDate.now());
		String url = URL.concat("?initialDate=" + date);
		this.mockMvc
				.perform(get(url)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(400)))
				.andExpect(jsonPath("message", is("Valor incorreto passado para o parâmetro: initialDate: " + date + ". Verifique os dados e tente novamente")))
				.andExpect(jsonPath("error", is("You must inform both initialDate and finalDate")))
				.andExpect(jsonPath("path", is(url)));
	}
	
	@Test
	void findAllFilteringByOnlyFinalDateErrorTest() throws Exception {
		String date = TestUtils.getFormattedDate(LocalDate.now());
		String url = URL.concat("?finalDate=" + date);
		this.mockMvc
				.perform(get(url)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(400)))
				.andExpect(jsonPath("message", is("Valor incorreto passado para o parâmetro: finalDate: " + date + ". Verifique os dados e tente novamente")))
				.andExpect(jsonPath("error", is("You must inform both initialDate and finalDate")))
				.andExpect(jsonPath("path", is(url)));
	}
	
	@Test
	void findAllFilteringByOnlyStatysErrorTest() throws Exception {
		String url = URL.concat("?status=RESERVED");
		this.mockMvc
				.perform(get(url)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(400)))
				.andExpect(jsonPath("message", is("Valor incorreto passado para o parâmetro: status: RESERVED. Verifique os dados e tente novamente")))
				.andExpect(jsonPath("error", is("You must inform both initialDate and finalDate when status is informed")))
				.andExpect(jsonPath("path", is(url)));
	}
	
	@Test
	void findAllFilteringByFinalDateBeforeInitialDateErrorTest() throws Exception {
		String date = TestUtils.getFormattedDate(LocalDate.now().minusDays(1));
		String url = URL.concat("?initialDate=" + TestUtils.getFormattedDate(LocalDate.now()) + "&finalDate=" + TestUtils.getFormattedDate(LocalDate.now().minusDays(1)));
		this.mockMvc
				.perform(get(url)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(400)))
				.andExpect(jsonPath("message", is("Valor incorreto passado para o parâmetro: finalDate: " + date + ". Verifique os dados e tente novamente")))
				.andExpect(jsonPath("error", is("initialDate must be before finalDate")))
				.andExpect(jsonPath("path", is(url)));
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
		this.mockMvc
				.perform(post(URL).content(new ObjectMapper().writeValueAsString(TestUtils.createRoomDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(header().exists("location"))
				.andExpect(header().string("location", endsWith(URL.concat("/7"))));
		String byId = URL.concat("/7");
		this.mockMvc.perform(delete(byId)).andExpect(status().isNoContent());
		this.mockMvc.perform(get(byId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

	}
}
