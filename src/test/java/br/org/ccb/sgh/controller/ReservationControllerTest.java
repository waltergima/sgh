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

import java.time.LocalDate;

import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
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
import br.org.ccb.sgh.entity.Reservation;
import br.org.ccb.sgh.http.dto.ReservationDto;
import br.org.ccb.sgh.http.dto.ReservationRequestParamsDto;
import br.org.ccb.sgh.service.ReservationService;

@SpringBootTest
class ReservationControllerTest {

	private static final String URL = "/reservations";
	private static final String BY_ID = URL.concat("/1");
	private MockMvc mockMvc;
	private AutoCloseable closeable;

	@MockBean
	private ReservationService supportHouseService;

	@InjectMocks
	private ReservationController supportHouseController;

	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(supportHouseController)
				.setControllerAdvice(new ResourceExceptionHandler()).build();
		closeable = MockitoAnnotations.openMocks(this);
	}
	
	@AfterEach
	void releaseMocks() throws Exception {
		closeable.close();
	}

	@Test
	void findAllBadRequestErrorTest() throws Exception {
		when(this.supportHouseService.findAll(any(ReservationRequestParamsDto.class)))
				.thenThrow(new DataIntegrityViolationException("Error"));
		this.mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("timestamp", notNullValue())).andExpect(jsonPath("status", is(400)))
				.andExpect(jsonPath("message", is("Não é possível remover este registro pois está sendo utilizado")))
				.andExpect(jsonPath("error", is("Error"))).andExpect(jsonPath("path", is(URL)));
	}

	@Test
	void findAllSuccessTest() throws Exception {
		when(this.supportHouseService.findAll(any(ReservationRequestParamsDto.class)))
				.thenReturn(new PageImpl<>(TestUtils.createReservationList()));
		this.mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(2))).andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[0].initialDate[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("$.content.[0].initialDate[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[0].initialDate[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("$.content.[0].finalDate[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("$.content.[0].finalDate[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[0].finalDate[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("$.content.[0].checkinDate[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("$.content.[0].checkinDate[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[0].checkinDate[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("$.content.[0].checkoutDate[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("$.content.[0].checkoutDate[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[0].checkoutDate[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("$.content.[0].observation", is("Observation")))
				.andExpect(jsonPath("$.content.[0].status", is("CONFIRMED")))
				.andExpect(jsonPath("$.content.[0].guests[0].name", is("Guest 1")))
				.andExpect(jsonPath("$.content.[0].guests[0].dateOfBirth[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("$.content.[0].guests[0].dateOfBirth[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[0].guests[0].dateOfBirth[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("$.content.[0].guests[0].phoneNumber", is("1658741258")))
				.andExpect(jsonPath("$.content.[0].guests[0].celNumber", is("18966547890")))
				.andExpect(jsonPath("$.content.[0].guests[0].rg", is("126547854")))
				.andExpect(jsonPath("$.content.[0].guests[0].cpf", is("19290228083")))
				.andExpect(jsonPath("$.content.[0].guests[0].ministery", is(true)))
				.andExpect(jsonPath("$.content.[0].guests[0].baptized", is(true)))
				.andExpect(jsonPath("$.content.[0].guests[0].dateOfBaptism[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("$.content.[0].guests[0].dateOfBaptism[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[0].guests[0].dateOfBaptism[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("$.content.[0].guests[0].prayingHouse", is("Central")))
				.andExpect(jsonPath("$.content.[0].guests[0].observation", is("observation")))
				.andExpect(jsonPath("$.content.[0].guests[0].address.id", is(1)))
				.andExpect(jsonPath("$.content.[0].guests[0].address.street", is("Street")))
				.andExpect(jsonPath("$.content.[0].guests[0].address.city", is("Test")))
				.andExpect(jsonPath("$.content.[0].guests[0].address.district", is("District")))
				.andExpect(jsonPath("$.content.[0].guests[0].address.number", is("1F")))
				.andExpect(jsonPath("$.content.[0].guests[0].address.state", is("SP")))
				.andExpect(jsonPath("$.content.[0].guests[0].address.zipCode", is("11111111")))
				.andExpect(jsonPath("$.content.[0].guests[1].id", is(2)))
				.andExpect(jsonPath("$.content.[0].guests[1].name", is("Guest 2")))
				.andExpect(jsonPath("$.content.[0].guests[1].dateOfBirth[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("$.content.[0].guests[1].dateOfBirth[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[0].guests[1].dateOfBirth[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("$.content.[0].guests[1].phoneNumber", is("1166987451")))
				.andExpect(jsonPath("$.content.[0].guests[1].celNumber", is("11988887746")))
				.andExpect(jsonPath("$.content.[0].guests[1].rg", is("226547854")))
				.andExpect(jsonPath("$.content.[0].guests[1].cpf", is("24953683013")))
				.andExpect(jsonPath("$.content.[0].guests[1].ministery", is(false)))
				.andExpect(jsonPath("$.content.[0].guests[1].baptized", is(false)))
				.andExpect(jsonPath("$.content.[0].guests[1].dateOfBaptism[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("$.content.[0].guests[1].dateOfBaptism[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[0].guests[1].dateOfBaptism[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("$.content.[0].guests[1].prayingHouse", is("Central")))
				.andExpect(jsonPath("$.content.[0].guests[1].observation", is("observation")))
				.andExpect(jsonPath("$.content.[0].guests[1].address.id", is(2)))
				.andExpect(jsonPath("$.content.[0].guests[1].address.street", is("Street2")))
				.andExpect(jsonPath("$.content.[0].guests[1].address.city", is("Test2")))
				.andExpect(jsonPath("$.content.[0].guests[1].address.district", is("District2")))
				.andExpect(jsonPath("$.content.[0].guests[1].address.number", is("1G")))
				.andExpect(jsonPath("$.content.[0].guests[1].address.state", is("MG")))
				.andExpect(jsonPath("$.content.[0].guests[1].address.zipCode", is("22222222")))
				.andExpect(jsonPath("$.content.[0].contact.id", is(1)))
				.andExpect(jsonPath("$.content.[0].contact.name", is("Contact 1")))
				.andExpect(jsonPath("$.content.[0].contact.phoneNumber", is("1666987450")))
				.andExpect(jsonPath("$.content.[0].contact.celNumber", is("16988887745")))
				.andExpect(jsonPath("$.content.[0].contact.ministery", is("Diácono")))
				.andExpect(jsonPath("$.content.[0].contact.relationship", is("Irmão")))
				.andExpect(jsonPath("$.content.[0].contact.observation", is("observation")))
				.andExpect(jsonPath("$.content.[0].contact.address.id", is(1)))
				.andExpect(jsonPath("$.content.[0].contact.address.street", is("Street")))
				.andExpect(jsonPath("$.content.[0].contact.address.city", is("Test")))
				.andExpect(jsonPath("$.content.[0].contact.address.district", is("District")))
				.andExpect(jsonPath("$.content.[0].contact.address.number", is("1F")))
				.andExpect(jsonPath("$.content.[0].contact.address.state", is("SP")))
				.andExpect(jsonPath("$.content.[0].contact.address.zipCode", is("11111111")))
				.andExpect(jsonPath("$.content.[1].id", is(2)))
				.andExpect(jsonPath("$.content.[1].initialDate[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("$.content.[1].initialDate[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[1].initialDate[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("$.content.[1].finalDate[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("$.content.[1].finalDate[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[1].finalDate[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("$.content.[1].checkinDate[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("$.content.[1].checkinDate[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[1].checkinDate[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("$.content.[1].checkoutDate[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("$.content.[1].checkoutDate[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[1].checkoutDate[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("$.content.[1].observation", is("Observation 2")))
				.andExpect(jsonPath("$.content.[1].status", is("PAUSED")))
				.andExpect(jsonPath("$.content.[1].guests[0].name", is("Guest 1")))
				.andExpect(jsonPath("$.content.[1].guests[0].dateOfBirth[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("$.content.[1].guests[0].dateOfBirth[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[1].guests[0].dateOfBirth[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("$.content.[1].guests[0].phoneNumber", is("1658741258")))
				.andExpect(jsonPath("$.content.[1].guests[0].celNumber", is("18966547890")))
				.andExpect(jsonPath("$.content.[1].guests[0].rg", is("126547854")))
				.andExpect(jsonPath("$.content.[1].guests[0].cpf", is("19290228083")))
				.andExpect(jsonPath("$.content.[1].guests[0].ministery", is(true)))
				.andExpect(jsonPath("$.content.[1].guests[0].baptized", is(true)))
				.andExpect(jsonPath("$.content.[1].guests[0].dateOfBaptism[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("$.content.[1].guests[0].dateOfBaptism[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[1].guests[0].dateOfBaptism[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("$.content.[1].guests[0].prayingHouse", is("Central")))
				.andExpect(jsonPath("$.content.[1].guests[0].observation", is("observation")))
				.andExpect(jsonPath("$.content.[1].guests[0].address.id", is(1)))
				.andExpect(jsonPath("$.content.[1].guests[0].address.street", is("Street")))
				.andExpect(jsonPath("$.content.[1].guests[0].address.city", is("Test")))
				.andExpect(jsonPath("$.content.[1].guests[0].address.district", is("District")))
				.andExpect(jsonPath("$.content.[1].guests[0].address.number", is("1F")))
				.andExpect(jsonPath("$.content.[1].guests[0].address.state", is("SP")))
				.andExpect(jsonPath("$.content.[1].guests[0].address.zipCode", is("11111111")))
				.andExpect(jsonPath("$.content.[1].guests[1].id", is(2)))
				.andExpect(jsonPath("$.content.[1].guests[1].name", is("Guest 2")))
				.andExpect(jsonPath("$.content.[1].guests[1].dateOfBirth[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("$.content.[1].guests[1].dateOfBirth[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[1].guests[1].dateOfBirth[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("$.content.[1].guests[1].phoneNumber", is("1166987451")))
				.andExpect(jsonPath("$.content.[1].guests[1].celNumber", is("11988887746")))
				.andExpect(jsonPath("$.content.[1].guests[1].rg", is("226547854")))
				.andExpect(jsonPath("$.content.[1].guests[1].cpf", is("24953683013")))
				.andExpect(jsonPath("$.content.[1].guests[1].ministery", is(false)))
				.andExpect(jsonPath("$.content.[1].guests[1].baptized", is(false)))
				.andExpect(jsonPath("$.content.[1].guests[1].dateOfBaptism[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("$.content.[1].guests[1].dateOfBaptism[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("$.content.[1].guests[1].dateOfBaptism[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("$.content.[1].guests[1].prayingHouse", is("Central")))
				.andExpect(jsonPath("$.content.[1].guests[1].observation", is("observation")))
				.andExpect(jsonPath("$.content.[1].guests[1].address.id", is(2)))
				.andExpect(jsonPath("$.content.[1].guests[1].address.street", is("Street2")))
				.andExpect(jsonPath("$.content.[1].guests[1].address.city", is("Test2")))
				.andExpect(jsonPath("$.content.[1].guests[1].address.district", is("District2")))
				.andExpect(jsonPath("$.content.[1].guests[1].address.number", is("1G")))
				.andExpect(jsonPath("$.content.[1].guests[1].address.state", is("MG")))
				.andExpect(jsonPath("$.content.[1].guests[1].address.zipCode", is("22222222")))
				.andExpect(jsonPath("$.content.[1].contact.id", is(2)))
				.andExpect(jsonPath("$.content.[1].contact.name", is("Contact 2")))
				.andExpect(jsonPath("$.content.[1].contact.phoneNumber", is("1666987451")))
				.andExpect(jsonPath("$.content.[1].contact.celNumber", is("16988887746")))
				.andExpect(jsonPath("$.content.[1].contact.ministery", is("Ancião")))
				.andExpect(jsonPath("$.content.[1].contact.relationship", is("Cunhado")))
				.andExpect(jsonPath("$.content.[1].contact.observation", is("observation")))
				.andExpect(jsonPath("$.content.[1].contact.address.id", is(2)))
				.andExpect(jsonPath("$.content.[1].contact.address.street", is("Street2")))
				.andExpect(jsonPath("$.content.[1].contact.address.city", is("Test2")))
				.andExpect(jsonPath("$.content.[1].contact.address.district", is("District2")))
				.andExpect(jsonPath("$.content.[1].contact.address.number", is("1G")))
				.andExpect(jsonPath("$.content.[1].contact.address.state", is("MG")))
				.andExpect(jsonPath("$.content.[1].contact.address.zipCode", is("22222222")));
	}

	@Test
	void findByIdNotFoundErrorTest() throws Exception {
		when(this.supportHouseService.byId(1l)).thenThrow(new ObjectNotFoundException(1l, Reservation.class.getName()));
		this.mockMvc.perform(get(BY_ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(jsonPath("timestamp", notNullValue())).andExpect(jsonPath("status", is(404)))
				.andExpect(jsonPath("message", is("Registro com id 1 não encontrado")))
				.andExpect(jsonPath("error",
						is("No row with the given identifier exists: [br.org.ccb.sgh.entity.Reservation#1]")))
				.andExpect(jsonPath("path", is(BY_ID)));
	}

	@Test
	void findByIdSuccessTest() throws Exception {
		when(this.supportHouseService.byId(1l)).thenReturn(TestUtils.createReservationList().get(0));
		this.mockMvc.perform(get(BY_ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("id", is(1))).andExpect(jsonPath("initialDate[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("initialDate[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("initialDate[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("finalDate[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("finalDate[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("finalDate[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("checkinDate[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("checkinDate[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("checkinDate[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("checkoutDate[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("checkoutDate[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("checkoutDate[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("observation", is("Observation"))).andExpect(jsonPath("status", is("CONFIRMED")))
				.andExpect(jsonPath("guests[0].name", is("Guest 1")))
				.andExpect(jsonPath("guests[0].dateOfBirth[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("guests[0].dateOfBirth[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("guests[0].dateOfBirth[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("guests[0].phoneNumber", is("1658741258")))
				.andExpect(jsonPath("guests[0].celNumber", is("18966547890")))
				.andExpect(jsonPath("guests[0].rg", is("126547854")))
				.andExpect(jsonPath("guests[0].cpf", is("19290228083")))
				.andExpect(jsonPath("guests[0].ministery", is(true)))
				.andExpect(jsonPath("guests[0].baptized", is(true)))
				.andExpect(jsonPath("guests[0].dateOfBaptism[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("guests[0].dateOfBaptism[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("guests[0].dateOfBaptism[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("guests[0].prayingHouse", is("Central")))
				.andExpect(jsonPath("guests[0].observation", is("observation")))
				.andExpect(jsonPath("guests[0].address.id", is(1)))
				.andExpect(jsonPath("guests[0].address.street", is("Street")))
				.andExpect(jsonPath("guests[0].address.city", is("Test")))
				.andExpect(jsonPath("guests[0].address.district", is("District")))
				.andExpect(jsonPath("guests[0].address.number", is("1F")))
				.andExpect(jsonPath("guests[0].address.state", is("SP")))
				.andExpect(jsonPath("guests[0].address.zipCode", is("11111111")))
				.andExpect(jsonPath("guests[1].id", is(2))).andExpect(jsonPath("guests[1].name", is("Guest 2")))
				.andExpect(jsonPath("guests[1].dateOfBirth[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("guests[1].dateOfBirth[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("guests[1].dateOfBirth[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("guests[1].phoneNumber", is("1166987451")))
				.andExpect(jsonPath("guests[1].celNumber", is("11988887746")))
				.andExpect(jsonPath("guests[1].rg", is("226547854")))
				.andExpect(jsonPath("guests[1].cpf", is("24953683013")))
				.andExpect(jsonPath("guests[1].ministery", is(false)))
				.andExpect(jsonPath("guests[1].baptized", is(false)))
				.andExpect(jsonPath("guests[1].dateOfBaptism[0]", is(LocalDate.now().getYear())))
				.andExpect(jsonPath("guests[1].dateOfBaptism[1]", is(LocalDate.now().getMonthValue())))
				.andExpect(jsonPath("guests[1].dateOfBaptism[2]", is(LocalDate.now().getDayOfMonth())))
				.andExpect(jsonPath("guests[1].prayingHouse", is("Central")))
				.andExpect(jsonPath("guests[1].observation", is("observation")))
				.andExpect(jsonPath("guests[1].address.id", is(2)))
				.andExpect(jsonPath("guests[1].address.street", is("Street2")))
				.andExpect(jsonPath("guests[1].address.city", is("Test2")))
				.andExpect(jsonPath("guests[1].address.district", is("District2")))
				.andExpect(jsonPath("guests[1].address.number", is("1G")))
				.andExpect(jsonPath("guests[1].address.state", is("MG")))
				.andExpect(jsonPath("guests[1].address.zipCode", is("22222222")))
				.andExpect(jsonPath("contact.id", is(1))).andExpect(jsonPath("contact.name", is("Contact 1")))
				.andExpect(jsonPath("contact.phoneNumber", is("1666987450")))
				.andExpect(jsonPath("contact.celNumber", is("16988887745")))
				.andExpect(jsonPath("contact.ministery", is("Diácono")))
				.andExpect(jsonPath("contact.relationship", is("Irmão")))
				.andExpect(jsonPath("contact.observation", is("observation")))
				.andExpect(jsonPath("contact.address.id", is(1)))
				.andExpect(jsonPath("contact.address.street", is("Street")))
				.andExpect(jsonPath("contact.address.city", is("Test")))
				.andExpect(jsonPath("contact.address.district", is("District")))
				.andExpect(jsonPath("contact.address.number", is("1F")))
				.andExpect(jsonPath("contact.address.state", is("SP")))
				.andExpect(jsonPath("contact.address.zipCode", is("11111111")));
	}

	@Test
	void saveBadRequestErrorTest() throws Exception {
		when(this.supportHouseService.save(any(ReservationDto.class)))
				.thenThrow(new DataIntegrityViolationException("Error"));
		this.mockMvc
				.perform(post(URL).content(new ObjectMapper().writeValueAsString(TestUtils.createReservationDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(400)))
				.andExpect(jsonPath("message", is("Não é possível remover este registro pois está sendo utilizado")))
				.andExpect(jsonPath("error", is("Error"))).andExpect(jsonPath("path", is(URL)));
	}

	@Test
	void saveSuccessTest() throws Exception {
		when(this.supportHouseService.save(any(ReservationDto.class)))
				.thenReturn(TestUtils.createReservationList().get(0));
		this.mockMvc
				.perform(post(URL).content(new ObjectMapper().writeValueAsString(TestUtils.createReservationDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(header().exists("location"))
				.andExpect(header().string("location", endsWith(BY_ID)));
	}

	@Test
	void updateNotFoundErrorTest() throws Exception {
		when(this.supportHouseService.update(eq(1l), any(ReservationDto.class)))
				.thenThrow(new ObjectNotFoundException(1l, Reservation.class.getName()));
		this.mockMvc
				.perform(put(BY_ID).content(new ObjectMapper().writeValueAsString(TestUtils.createReservationDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(404)))
				.andExpect(jsonPath("message", is("Registro com id 1 não encontrado")))
				.andExpect(jsonPath("error",
						is("No row with the given identifier exists: [br.org.ccb.sgh.entity.Reservation#1]")))
				.andExpect(jsonPath("path", is(BY_ID)));

	}

	@Test
	void updateSuccessTest() throws Exception {
		when(this.supportHouseService.update(eq(1l), any(ReservationDto.class)))
				.thenReturn(TestUtils.createReservationList().get(0));
		this.mockMvc.perform(put(BY_ID).content(new ObjectMapper().writeValueAsString(TestUtils.createReservationDto()))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
	}

	@Test
	void removeNotFoundErrorTest() throws Exception {
		doThrow(new ObjectNotFoundException(1l, Reservation.class.getName())).when(this.supportHouseService).remove(1l);
		this.mockMvc
				.perform(delete(BY_ID).content(new ObjectMapper().writeValueAsString(TestUtils.createReservationDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(404)))
				.andExpect(jsonPath("message", is("Registro com id 1 não encontrado")))
				.andExpect(jsonPath("error",
						is("No row with the given identifier exists: [br.org.ccb.sgh.entity.Reservation#1]")))
				.andExpect(jsonPath("path", is(BY_ID)));

	}

	@Test
	void removeSuccessTest() throws Exception {
		doNothing().when(this.supportHouseService).remove(1l);
		this.mockMvc
				.perform(delete(BY_ID).content(new ObjectMapper().writeValueAsString(TestUtils.createReservationDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
}
