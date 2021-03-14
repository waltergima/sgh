package br.org.ccb.sgh.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.org.ccb.sgh.TestUtils;
import br.org.ccb.sgh.entity.Reservation;
import br.org.ccb.sgh.http.dto.ReservationRequestParamsDto;
import br.org.ccb.sgh.repository.AddressRepository;
import br.org.ccb.sgh.repository.ReservationRepository;
import br.org.ccb.sgh.repository.specification.ReservationSpecification;

@SpringBootTest
class ReservationServiceImplTest {

	@Autowired
	@InjectMocks
	private ReservationServiceImpl reservationService;

	@MockBean
	private ReservationRepository reservationRepository;

	@MockBean
	private AddressRepository addressRepository;

	@Test
	void findAllErrorTest() {
		when(reservationRepository.findAll(any(ReservationSpecification.class), any(Pageable.class)))
				.thenThrow(new DataIntegrityViolationException("DataIntegrityViolationException"));
		ReservationRequestParamsDto requestParams = ReservationRequestParamsDto.builder().offset(0).limit(25)
				.orderBy("id").direction("ASC").build();
		DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
			this.reservationService.findAll(requestParams);
		});
		assertEquals("DataIntegrityViolationException", exception.getMessage());
	}
	
	@Test
	void findAllNotFoundTest() {
		when(reservationRepository.findAll(any(ReservationSpecification.class), any(Pageable.class)))
				.thenReturn(new PageImpl<>(new ArrayList<>()));
		ReservationRequestParamsDto requestParams = ReservationRequestParamsDto.builder().offset(0).limit(25)
				.orderBy("id").direction("ASC").build();
		EmptyResultDataAccessException exception = assertThrows(EmptyResultDataAccessException.class, () -> {
			this.reservationService.findAll(requestParams);
		});
		assertEquals("Incorrect result size: expected 25, actual 0", exception.getMessage());
	}

	@Test
	void findAllSuccessTest() {
		List<Reservation> mock = TestUtils.createReservationList();
		when(reservationRepository.findAll(any(ReservationSpecification.class), any(Pageable.class)))
				.thenReturn(new PageImpl<Reservation>(mock));
		Page<Reservation> reservations = this.reservationService.findAll(
				ReservationRequestParamsDto.builder().offset(0).limit(25).orderBy("id").direction("ASC").build());
		assertEquals(reservations.getContent(), mock);

		verify(reservationRepository).findAll(any(ReservationSpecification.class), any(Pageable.class));
	}

	@Test
	void byIdErrorTest() {
		when(reservationRepository.findById(1l))
				.thenThrow(new DataIntegrityViolationException("DataIntegrityViolationException"));
		DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
			this.reservationService.byId(1l);
		});
		assertEquals("DataIntegrityViolationException", exception.getMessage());
	}

	@Test
	void byIdNotFoundTest() {
		when(reservationRepository.findById(1l)).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			this.reservationService.byId(1l);
		});
		assertEquals("No row with the given identifier exists: [br.org.ccb.sgh.entity.Reservation#1]",
				exception.getMessage());
	}

	@Test
	void byIdSuccessTest() {
		List<Reservation> mock = TestUtils.createReservationList();
		when(reservationRepository.findById(1l)).thenReturn(Optional.of(mock.get(0)));

		Reservation reservation = this.reservationService.byId(1l);
		assertEquals(reservation, mock.get(0));

		verify(reservationRepository).findById(1l);
	}

	@Test
	void saveSuccessTest() {
		Reservation mock = TestUtils.createReservationList().get(0);
		mock.setId(null);
		when(this.reservationRepository.save(any(Reservation.class))).thenReturn(mock);
		Reservation reservation = this.reservationService.save(TestUtils.createReservationDto());
		assertEquals(mock, reservation);
		verify(this.reservationRepository).save(any(Reservation.class));
	}

	@Test
	void updateSuccessTest() {
		Reservation mock = TestUtils.createReservationList().get(0);
		mock.setId(null);
		when(this.reservationRepository.findById(mock.getId())).thenReturn(Optional.of(mock));
		when(this.reservationRepository.save(any(Reservation.class))).thenReturn(mock);
		Reservation reservation = this.reservationService.update(mock.getId(), TestUtils.createReservationDto());
		assertEquals(mock, reservation);
		verify(this.reservationRepository).findById(mock.getId());
		verify(this.reservationRepository).save(any(Reservation.class));
	}

	@Test
	void deleteByIdSuccessTest() {
		doNothing().when(this.reservationRepository).deleteById(1l);
		this.reservationService.remove(1l);
		verify(this.reservationRepository).deleteById(1l);
	}
}
