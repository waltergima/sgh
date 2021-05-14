package br.org.ccb.sgh.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.org.ccb.sgh.TestUtils;
import br.org.ccb.sgh.entity.Room;
import br.org.ccb.sgh.http.dto.RoomRequestParamsDto;
import br.org.ccb.sgh.repository.AddressRepository;
import br.org.ccb.sgh.repository.RoomRepository;
import br.org.ccb.sgh.repository.specification.RoomSpecification;
import br.org.ccb.sgh.util.RoomStatus;

@SpringBootTest
class RoomServiceImplTest {

	@Autowired
	@InjectMocks
	private RoomServiceImpl roomService;

	@MockBean
	private RoomRepository roomRepository;

	@MockBean
	private AddressRepository addressRepository;

	@Test
	void findAllErrorTest() {
		when(roomRepository.findAll(any(RoomSpecification.class), any(Pageable.class)))
				.thenThrow(new DataIntegrityViolationException("DataIntegrityViolationException"));
		RoomRequestParamsDto requestParams = RoomRequestParamsDto.builder().offset(0).limit(25)
				.orderBy("id").direction("ASC").build();
		DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
			this.roomService.findAll(requestParams);
		});
		assertEquals("DataIntegrityViolationException", exception.getMessage());
	}
	
	@Test
	void findAllNotFoundTest() {
		when(roomRepository.findAll(any(RoomSpecification.class), any(Pageable.class)))
				.thenReturn(new PageImpl<>(new ArrayList<>()));
		RoomRequestParamsDto requestParams = RoomRequestParamsDto.builder().offset(0).limit(25).orderBy("id")
				.direction("ASC").build();
		EmptyResultDataAccessException exception = assertThrows(EmptyResultDataAccessException.class, () -> {
			this.roomService.findAll(requestParams);
		});
		assertEquals("Incorrect result size: expected 25, actual 0", exception.getMessage());
	}

	@Test
	void findAllSuccessTest() {
		List<Room> mock = TestUtils.createRoomList();
		when(roomRepository.findAll(any(RoomSpecification.class), any(Pageable.class)))
				.thenReturn(new PageImpl<Room>(mock));
		Page<Room> rooms = this.roomService.findAll(
				RoomRequestParamsDto.builder().offset(0).limit(25).orderBy("id").direction("ASC").build());
		assertEquals(rooms.getContent(), mock);

		verify(roomRepository).findAll(any(RoomSpecification.class), any(Pageable.class));
	}
	
	@Test
	void findAllWithoutStatusTest() {
		List<Room> mock = TestUtils.createRoomWithAllStatus();
		when(roomRepository.findAll(any(RoomSpecification.class), any(Pageable.class)))
				.thenReturn(new PageImpl<Room>(mock));
		Page<Room> rooms = this.roomService.findAll(
				RoomRequestParamsDto.builder().offset(0).limit(25).orderBy("id").direction("ASC").build());
		
		assertEquals(8, rooms.getContent().size());
		
		assertTrue(rooms.getContent().stream().allMatch(room -> room.getStatus() == null));

		verify(roomRepository).findAll(any(RoomSpecification.class), any(Pageable.class));
	}
	
	@Test
	void findAllWithAllStatusTest() {
		List<Room> mock = TestUtils.createRoomWithAllStatus();
		when(roomRepository.findAll(any(RoomSpecification.class), any(Pageable.class)))
				.thenReturn(new PageImpl<Room>(mock));
		Page<Room> rooms = this.roomService.findAll(
				RoomRequestParamsDto.builder().initialDate(LocalDate.now()).finalDate(LocalDate.now()).offset(0).limit(25).orderBy("id").direction("ASC").build());
		
		assertEquals(8, rooms.getContent().size());
		
		assertEquals(RoomStatus.AVAILABLE, rooms.getContent().get(0).getStatus());
		
		assertEquals(RoomStatus.OCCUPIED, rooms.getContent().get(1).getStatus());
		
		assertEquals(RoomStatus.OCCUPIED, rooms.getContent().get(2).getStatus());
		
		assertEquals(RoomStatus.AVAILABLE, rooms.getContent().get(3).getStatus());
		
		assertEquals(RoomStatus.AVAILABLE, rooms.getContent().get(4).getStatus());
		
		assertEquals(RoomStatus.AVAILABLE, rooms.getContent().get(5).getStatus());
		
		assertEquals(RoomStatus.RESERVED, rooms.getContent().get(6).getStatus());
		
		assertEquals(RoomStatus.AVAILABLE, rooms.getContent().get(7).getStatus());

		verify(roomRepository).findAll(any(RoomSpecification.class), any(Pageable.class));
	}
	
	@Test
	void findAllWithAllStatusFinalDateTest() {
		List<Room> mock = TestUtils.createRoomWithAllStatus();
		when(roomRepository.findAll(any(RoomSpecification.class), any(Pageable.class)))
				.thenReturn(new PageImpl<Room>(mock));
		Page<Room> rooms = this.roomService.findAll(
				RoomRequestParamsDto.builder().initialDate(LocalDate.now().minusDays(2)).finalDate(LocalDate.now()).offset(0).limit(25).orderBy("id").direction("ASC").build());
		
		assertEquals(8, rooms.getContent().size());
		
		assertEquals(RoomStatus.AVAILABLE, rooms.getContent().get(0).getStatus());
		
		assertEquals(RoomStatus.OCCUPIED, rooms.getContent().get(1).getStatus());
		
		assertEquals(RoomStatus.OCCUPIED, rooms.getContent().get(2).getStatus());
		
		assertEquals(RoomStatus.AVAILABLE, rooms.getContent().get(3).getStatus());
		
		assertEquals(RoomStatus.OCCUPIED, rooms.getContent().get(4).getStatus());
		
		assertEquals(RoomStatus.AVAILABLE, rooms.getContent().get(5).getStatus());
		
		assertEquals(RoomStatus.RESERVED, rooms.getContent().get(6).getStatus());
		
		assertEquals(RoomStatus.RESERVED, rooms.getContent().get(7).getStatus());

		verify(roomRepository).findAll(any(RoomSpecification.class), any(Pageable.class));
	}

	@Test
	void byIdErrorTest() {
		when(roomRepository.findById(1l))
				.thenThrow(new DataIntegrityViolationException("DataIntegrityViolationException"));
		DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
			this.roomService.byId(1l);
		});
		assertEquals("DataIntegrityViolationException", exception.getMessage());
	}

	@Test
	void byIdNotFoundTest() {
		when(roomRepository.findById(1l)).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			this.roomService.byId(1l);
		});
		assertEquals("No row with the given identifier exists: [br.org.ccb.sgh.entity.Room#1]",
				exception.getMessage());
	}

	@Test
	void byIdSuccessTest() {
		List<Room> mock = TestUtils.createRoomList();
		when(roomRepository.findById(1l)).thenReturn(Optional.of(mock.get(0)));

		Room room = this.roomService.byId(1l);
		assertEquals(room, mock.get(0));

		verify(roomRepository).findById(1l);
	}

	@Test
	void saveSuccessTest() {
		Room mock = TestUtils.createRoomList().get(0);
		mock.setId(null);
		when(this.roomRepository.save(mock)).thenReturn(mock);
		Room room = this.roomService.save(TestUtils.createRoomDto());
		assertEquals(mock, room);
		verify(this.roomRepository).save(mock);
	}

	@Test
	void updateSuccessTest() {
		Room mock = TestUtils.createRoomList().get(0);
		when(this.roomRepository.findById(mock.getId())).thenReturn(Optional.of(mock));
		when(this.roomRepository.save(Mockito.any())).thenReturn(mock);
		Room room = this.roomService.update(mock.getId(), TestUtils.createRoomDto());
		assertEquals(mock, room);
		verify(this.roomRepository).findById(mock.getId());
		verify(this.roomRepository).save(Mockito.any());
	}

	@Test
	void deleteByIdSuccessTest() {
		doNothing().when(this.roomRepository).deleteById(1l);
		this.roomService.remove(1l);
		verify(this.roomRepository).deleteById(1l);
	}
}
