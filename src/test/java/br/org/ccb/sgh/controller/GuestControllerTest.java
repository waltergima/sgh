package br.org.ccb.sgh.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.ccb.sgh.TestUtils;
import br.org.ccb.sgh.controller.handler.ResourceExceptionHandler;
import br.org.ccb.sgh.entity.Guest;
import br.org.ccb.sgh.http.dto.GuestDto;
import br.org.ccb.sgh.http.dto.GuestRequestParamsDto;
import br.org.ccb.sgh.service.GuestService;

@SpringBootTest
class GuestControllerTest {

	private static final String URL = "/guests";
	private static final String BY_ID = URL.concat("/1");
	private MockMvc mockMvc;

	@MockBean
	private GuestService guestService;

	@InjectMocks
	private GuestController guestController;

	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(guestController)
				.setControllerAdvice(new ResourceExceptionHandler()).build();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void findAllBadRequestErrorTest() throws Exception {
		when(this.guestService.findAll(any(GuestRequestParamsDto.class)))
				.thenThrow(new DataIntegrityViolationException("Error"));
		this.mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("timestamp", notNullValue())).andExpect(jsonPath("status", is(400)))
				.andExpect(jsonPath("message", is("Não é possível remover este registro pois está sendo utilizado")))
				.andExpect(jsonPath("error", is("Error"))).andExpect(jsonPath("path", is(URL)));
	}

	@Test
	void findAllSuccessTest() throws Exception {
		when(this.guestService.findAll(any(GuestRequestParamsDto.class)))
				.thenReturn(new PageImpl<>(TestUtils.createGuestList()));
		this.mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(2))).andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[0].name", is("Guest 1")))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[0]", is(LocalDateTime.now().getYear())))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[1]", is(LocalDateTime.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[0].dateOfBirth[2]", is(LocalDateTime.now().getDayOfMonth())))
				.andExpect(jsonPath("$.content.[0].phoneNumber", is("1658741258")))
				.andExpect(jsonPath("$.content.[0].celNumber", is("18966547890")))
				.andExpect(jsonPath("$.content.[0].rg", is("126547854")))
				.andExpect(jsonPath("$.content.[0].cpf", is("19290228083")))
				.andExpect(jsonPath("$.content.[0].ministery", is(true)))
				.andExpect(jsonPath("$.content.[0].baptized", is(true)))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[0]", is(LocalDateTime.now().getYear())))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[1]", is(LocalDateTime.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[0].dateOfBaptism[2]", is(LocalDateTime.now().getDayOfMonth())))
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
				.andExpect(jsonPath("$.content.[1].dateOfBirth[0]", is(LocalDateTime.now().getYear())))
				.andExpect(jsonPath("$.content.[1].dateOfBirth[1]", is(LocalDateTime.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[1].dateOfBirth[2]", is(LocalDateTime.now().getDayOfMonth())))
				.andExpect(jsonPath("$.content.[1].phoneNumber", is("1166987451")))
				.andExpect(jsonPath("$.content.[1].celNumber", is("11988887746")))
				.andExpect(jsonPath("$.content.[1].rg", is("226547854")))
				.andExpect(jsonPath("$.content.[1].cpf", is("24953683013")))
				.andExpect(jsonPath("$.content.[1].ministery", is(false)))
				.andExpect(jsonPath("$.content.[1].baptized", is(false)))
				.andExpect(jsonPath("$.content.[1].dateOfBaptism[0]", is(LocalDateTime.now().getYear())))
				.andExpect(jsonPath("$.content.[1].dateOfBaptism[1]", is(LocalDateTime.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[1].dateOfBaptism[2]", is(LocalDateTime.now().getDayOfMonth())))
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
	void findByIdNotFoundErrorTest() throws Exception {
		when(this.guestService.byId(1l))
				.thenThrow(new ObjectNotFoundException(1l, Guest.class.getName()));
		this.mockMvc.perform(get(BY_ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(jsonPath("timestamp", notNullValue())).andExpect(jsonPath("status", is(404)))
				.andExpect(jsonPath("message", is("Registro com id 1 não encontrado")))
				.andExpect(jsonPath("error",
						is("No row with the given identifier exists: [br.org.ccb.sgh.entity.Guest#1]")))
				.andExpect(jsonPath("path", is(BY_ID)));
	}

	@Test
	void findByIdSuccessTest() throws Exception {
		when(this.guestService.byId(1l)).thenReturn(TestUtils.createGuestList().get(0));
		this.mockMvc.perform(get(BY_ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("id", is(1)))
				.andExpect(jsonPath("name", is("Guest 1")))
				.andExpect(jsonPath("dateOfBirth[0]", is(LocalDateTime.now().getYear())))
				.andExpect(jsonPath("dateOfBirth[1]", is(LocalDateTime.now().getMonthValue())))
				.andExpect(jsonPath("dateOfBirth[2]", is(LocalDateTime.now().getDayOfMonth())))
				.andExpect(jsonPath("phoneNumber", is("1658741258")))
				.andExpect(jsonPath("celNumber", is("18966547890")))
				.andExpect(jsonPath("rg", is("126547854")))
				.andExpect(jsonPath("cpf", is("19290228083")))
				.andExpect(jsonPath("ministery", is(true)))
				.andExpect(jsonPath("baptized", is(true)))
				.andExpect(jsonPath("dateOfBaptism[0]", is(LocalDateTime.now().getYear())))
				.andExpect(jsonPath("dateOfBaptism[1]", is(LocalDateTime.now().getMonthValue())))
				.andExpect(jsonPath("dateOfBaptism[2]", is(LocalDateTime.now().getDayOfMonth())))
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
	void saveBadRequestErrorTest() throws Exception {
		when(this.guestService.save(any(GuestDto.class)))
				.thenThrow(new DataIntegrityViolationException("Error"));
		this.mockMvc
				.perform(post(URL).content(new ObjectMapper().writeValueAsString(TestUtils.createGuestDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(400)))
				.andExpect(jsonPath("message", is("Não é possível remover este registro pois está sendo utilizado")))
				.andExpect(jsonPath("error", is("Error"))).andExpect(jsonPath("path", is(URL)));
	}

	@Test
	void saveSuccessTest() throws Exception {
		when(this.guestService.save(any(GuestDto.class)))
				.thenReturn(TestUtils.createGuestList().get(0));
		this.mockMvc
				.perform(post(URL).content(new ObjectMapper().writeValueAsString(TestUtils.createGuestDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(header().exists("location"))
				.andExpect(header().string("location", endsWith(BY_ID)));
	}

	@Test
	void updateNotFoundErrorTest() throws Exception {		
		when(this.guestService.update(eq(1l), any(GuestDto.class)))
				.thenThrow(new ObjectNotFoundException(1l, Guest.class.getName()));
		this.mockMvc
				.perform(put(BY_ID).content(new ObjectMapper().writeValueAsString(TestUtils.createGuestDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(404)))
				.andExpect(jsonPath("message", is("Registro com id 1 não encontrado")))
				.andExpect(jsonPath("error",
						is("No row with the given identifier exists: [br.org.ccb.sgh.entity.Guest#1]")))
				.andExpect(jsonPath("path", is(BY_ID)));

	}

	@Test
	void updateSuccessTest() throws Exception {
		when(this.guestService.update(eq(1l), any(GuestDto.class)))
				.thenReturn(TestUtils.createGuestList().get(0));
		this.mockMvc
				.perform(put(BY_ID).content(new ObjectMapper().writeValueAsString(TestUtils.createGuestDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	void removeNotFoundErrorTest() throws Exception {
		doThrow(new ObjectNotFoundException(1l, Guest.class.getName())).when(this.guestService)
				.remove(1l);
		this.mockMvc
				.perform(delete(BY_ID).content(new ObjectMapper().writeValueAsString(TestUtils.createGuestDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(404)))
				.andExpect(jsonPath("message", is("Registro com id 1 não encontrado")))
				.andExpect(jsonPath("error",
						is("No row with the given identifier exists: [br.org.ccb.sgh.entity.Guest#1]")))
				.andExpect(jsonPath("path", is(BY_ID)));

	}

	@Test
	void removeSuccessTest() throws Exception {
		doNothing().when(this.guestService).remove(1l);
		this.mockMvc
				.perform(delete(BY_ID).content(new ObjectMapper().writeValueAsString(TestUtils.createGuestDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
}
