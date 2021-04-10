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

import java.util.List;

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
import br.org.ccb.sgh.entity.Room;
import br.org.ccb.sgh.http.dto.RoomDto;
import br.org.ccb.sgh.http.dto.RoomRequestParamsDto;
import br.org.ccb.sgh.service.RoomService;

@SpringBootTest
class RoomControllerTest {

	private static final String URL = "/rooms";
	private static final String BY_ID = URL.concat("/1");
	private MockMvc mockMvc;
	private AutoCloseable closeable;

	@MockBean
	private RoomService roomService;

	@InjectMocks
	private RoomController roomController;

	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(roomController)
				.setControllerAdvice(new ResourceExceptionHandler()).build();
		closeable = MockitoAnnotations.openMocks(this);
	}
	
	@AfterEach
	void releaseMocks() throws Exception {
		closeable.close();
	}

	@Test
	void findAllBadRequestErrorTest() throws Exception {
		when(this.roomService.findAll(any(RoomRequestParamsDto.class)))
				.thenThrow(new DataIntegrityViolationException("Error"));
		this.mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("timestamp", notNullValue())).andExpect(jsonPath("status", is(400)))
				.andExpect(jsonPath("message", is("Não é possível remover este registro pois está sendo utilizado")))
				.andExpect(jsonPath("error", is("Error"))).andExpect(jsonPath("path", is(URL)));
	}

	@Test
	void findAllSuccessTest() throws Exception {
		List<Room> roomList = TestUtils.createRoomList();
		roomList.get(0).setAvailable(true);
		roomList.get(1).setAvailable(false);
		when(this.roomService.findAll(any(RoomRequestParamsDto.class))).thenReturn(new PageImpl<>(roomList));
		this.mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(2))).andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[0].name", is("Room 1")))
				.andExpect(jsonPath("$.content.[0].floor", is("1")))
				.andExpect(jsonPath("$.content.[0].number", is("1A")))
				.andExpect(jsonPath("$.content.[0].numberOfBeds", is(4)))
				.andExpect(jsonPath("$.content.[0].available", is(true))).andExpect(jsonPath("$.content.[1].id", is(2)))
				.andExpect(jsonPath("$.content.[1].name", is("Room 2")))
				.andExpect(jsonPath("$.content.[1].floor", is("1")))
				.andExpect(jsonPath("$.content.[1].number", is("1B")))
				.andExpect(jsonPath("$.content.[1].numberOfBeds", is(2)))
				.andExpect(jsonPath("$.content.[1].available", is(false)));
	}

	@Test
	void findByIdNotFoundErrorTest() throws Exception {
		when(this.roomService.byId(1l)).thenThrow(new ObjectNotFoundException(1l, Room.class.getName()));
		this.mockMvc.perform(get(BY_ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(jsonPath("timestamp", notNullValue())).andExpect(jsonPath("status", is(404)))
				.andExpect(jsonPath("message", is("Registro com id 1 não encontrado")))
				.andExpect(jsonPath("error",
						is("No row with the given identifier exists: [br.org.ccb.sgh.entity.Room#1]")))
				.andExpect(jsonPath("path", is(BY_ID)));
	}

	@Test
	void findByIdSuccessTest() throws Exception {
		Room room = TestUtils.createRoomList().get(0);
		room.setAvailable(true);
		when(this.roomService.byId(1l)).thenReturn(room);
		this.mockMvc.perform(get(BY_ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("id", is(1))).andExpect(jsonPath("name", is("Room 1")))
				.andExpect(jsonPath("floor", is("1"))).andExpect(jsonPath("number", is("1A")))
				.andExpect(jsonPath("numberOfBeds", is(4))).andExpect(jsonPath("available", is(true)));
	}

	@Test
	void saveBadRequestErrorTest() throws Exception {
		when(this.roomService.save(any(RoomDto.class))).thenThrow(new DataIntegrityViolationException("Error"));
		this.mockMvc
				.perform(post(URL).content(new ObjectMapper().writeValueAsString(TestUtils.createRoomDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(400)))
				.andExpect(jsonPath("message", is("Não é possível remover este registro pois está sendo utilizado")))
				.andExpect(jsonPath("error", is("Error"))).andExpect(jsonPath("path", is(URL)));
	}

	@Test
	void saveSuccessTest() throws Exception {
		when(this.roomService.save(any(RoomDto.class))).thenReturn(TestUtils.createRoomList().get(0));
		this.mockMvc
				.perform(post(URL).content(new ObjectMapper().writeValueAsString(TestUtils.createRoomDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(header().exists("location"))
				.andExpect(header().string("location", endsWith(BY_ID)));
	}

	@Test
	void updateNotFoundErrorTest() throws Exception {
		when(this.roomService.update(eq(1l), any(RoomDto.class)))
				.thenThrow(new ObjectNotFoundException(1l, Room.class.getName()));
		this.mockMvc
				.perform(put(BY_ID).content(new ObjectMapper().writeValueAsString(TestUtils.createRoomDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(404)))
				.andExpect(jsonPath("message", is("Registro com id 1 não encontrado")))
				.andExpect(jsonPath("error",
						is("No row with the given identifier exists: [br.org.ccb.sgh.entity.Room#1]")))
				.andExpect(jsonPath("path", is(BY_ID)));

	}

	@Test
	void updateSuccessTest() throws Exception {
		when(this.roomService.update(eq(1l), any(RoomDto.class))).thenReturn(TestUtils.createRoomList().get(0));
		this.mockMvc.perform(put(BY_ID).content(new ObjectMapper().writeValueAsString(TestUtils.createRoomDto()))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
	}

	@Test
	void removeNotFoundErrorTest() throws Exception {
		doThrow(new ObjectNotFoundException(1l, Room.class.getName())).when(this.roomService).remove(1l);
		this.mockMvc
				.perform(delete(BY_ID).content(new ObjectMapper().writeValueAsString(TestUtils.createRoomDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(404)))
				.andExpect(jsonPath("message", is("Registro com id 1 não encontrado")))
				.andExpect(jsonPath("error",
						is("No row with the given identifier exists: [br.org.ccb.sgh.entity.Room#1]")))
				.andExpect(jsonPath("path", is(BY_ID)));

	}

	@Test
	void removeSuccessTest() throws Exception {
		doNothing().when(this.roomService).remove(1l);
		this.mockMvc.perform(delete(BY_ID).content(new ObjectMapper().writeValueAsString(TestUtils.createRoomDto()))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
	}
}
