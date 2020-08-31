package br.org.ccb.sgh.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.org.ccb.sgh.TestUtils;
import br.org.ccb.sgh.entity.Guest;
import br.org.ccb.sgh.http.dto.GuestRequestParamsDto;
import br.org.ccb.sgh.repository.AddressRepository;
import br.org.ccb.sgh.repository.GuestRepository;
import br.org.ccb.sgh.repository.specification.GuestSpecification;

@SpringBootTest
class GuestServiceImplTest {

	@Autowired
	@InjectMocks
	private GuestServiceImpl guestService;

	@MockBean
	private GuestRepository guestRepository;

	@MockBean
	private AddressRepository addressRepository;

	@Test
	void findAllErrorTest() {
		when(guestRepository.findAll(any(GuestSpecification.class), any(Pageable.class)))
				.thenThrow(new DataIntegrityViolationException("DataIntegrityViolationException"));
		GuestRequestParamsDto requestParams = GuestRequestParamsDto.builder().offset(0).limit(25)
				.orderBy("id").direction("ASC").build();
		DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
			this.guestService.findAll(requestParams);
		});
		assertEquals("DataIntegrityViolationException", exception.getMessage());
	}

	@Test
	void findAllSuccessTest() {
		List<Guest> mock = TestUtils.createGuestList();
		when(guestRepository.findAll(any(GuestSpecification.class), any(Pageable.class)))
				.thenReturn(new PageImpl<Guest>(mock));
		Page<Guest> guests = this.guestService.findAll(
				GuestRequestParamsDto.builder().offset(0).limit(25).orderBy("id").direction("ASC").build());
		assertEquals(guests.getContent(), mock);

		verify(guestRepository).findAll(any(GuestSpecification.class), any(Pageable.class));
	}

	@Test
	void byIdErrorTest() {
		when(guestRepository.findById(1l))
				.thenThrow(new DataIntegrityViolationException("DataIntegrityViolationException"));
		DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
			this.guestService.byId(1l);
		});
		assertEquals("DataIntegrityViolationException", exception.getMessage());
	}

	@Test
	void byIdNotFoundTest() {
		when(guestRepository.findById(1l)).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			this.guestService.byId(1l);
		});
		assertEquals("No row with the given identifier exists: [br.org.ccb.sgh.entity.Guest#1]",
				exception.getMessage());
	}

	@Test
	void byIdSuccessTest() {
		List<Guest> mock = TestUtils.createGuestList();
		when(guestRepository.findById(1l)).thenReturn(Optional.of(mock.get(0)));

		Guest guest = this.guestService.byId(1l);
		assertEquals(guest, mock.get(0));

		verify(guestRepository).findById(1l);
	}

	@Test
	void saveSuccessTest() {
		Guest mock = TestUtils.createGuestList().get(0);
		mock.setId(null);
		mock.getAddress().setId(null);
		mock.getType().setDescription(null);
		when(this.guestRepository.save(any(Guest.class))).thenReturn(mock);
		when(this.addressRepository.save(mock.getAddress())).thenReturn(mock.getAddress());
		Guest guest = this.guestService.save(TestUtils.createGuestDto());
		assertEquals(mock, guest);
		verify(this.guestRepository).save(any(Guest.class));
		verify(this.addressRepository).save(mock.getAddress());
	}

	@Test
	void updateSuccessTest() {
		Guest mock = TestUtils.createGuestList().get(0);
		mock.setId(null);
		mock.getAddress().setId(null);
		when(this.guestRepository.findById(mock.getId())).thenReturn(Optional.of(mock));
		when(this.guestRepository.save(any(Guest.class))).thenReturn(mock);
		when(this.addressRepository.save(mock.getAddress())).thenReturn(mock.getAddress());
		Guest guest = this.guestService.update(mock.getId(), TestUtils.createGuestDto());
		assertEquals(mock, guest);
		verify(this.guestRepository).findById(mock.getId());
		verify(this.guestRepository).save(any(Guest.class));
	}

	@Test
	void deleteByIdSuccessTest() {
		doNothing().when(this.guestRepository).deleteById(1l);
		this.guestService.remove(1l);
		verify(this.guestRepository).deleteById(1l);
	}
}
