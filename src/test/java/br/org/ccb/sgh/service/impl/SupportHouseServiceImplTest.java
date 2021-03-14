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
import br.org.ccb.sgh.entity.SupportHouse;
import br.org.ccb.sgh.http.dto.SupportHouseRequestParamsDto;
import br.org.ccb.sgh.repository.SupportHouseRepository;
import br.org.ccb.sgh.repository.specification.SupportHouseSpecification;

@SpringBootTest
class SupportHouseServiceImplTest {

	@Autowired
	@InjectMocks
	private SupportHouseServiceImpl supportHouseService;

	@MockBean
	private SupportHouseRepository supportHouseRepository;

	@Test
	void findAllErrorTest() {
		when(supportHouseRepository.findAll(any(SupportHouseSpecification.class), any(Pageable.class)))
				.thenThrow(new DataIntegrityViolationException("DataIntegrityViolationException"));
		SupportHouseRequestParamsDto requestParams = SupportHouseRequestParamsDto.builder().offset(0).limit(25)
				.orderBy("id").direction("ASC").build();
		DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
			this.supportHouseService.findAll(requestParams);
		});
		assertEquals("DataIntegrityViolationException", exception.getMessage());
	}

	@Test
	void findAllNotFoundTest() {
		when(supportHouseRepository.findAll(any(SupportHouseSpecification.class), any(Pageable.class)))
				.thenReturn(new PageImpl<>(new ArrayList<>()));
		SupportHouseRequestParamsDto requestParams = SupportHouseRequestParamsDto.builder().offset(0).limit(25)
				.orderBy("id").direction("ASC").build();
		EmptyResultDataAccessException exception = assertThrows(EmptyResultDataAccessException.class, () -> {
			this.supportHouseService.findAll(requestParams);
		});
		assertEquals("Incorrect result size: expected 25, actual 0", exception.getMessage());
	}
	
	@Test
	void findAllSuccessTest() {
		List<SupportHouse> mock = TestUtils.createSupportHouseList();
		when(supportHouseRepository.findAll(any(SupportHouseSpecification.class), any(Pageable.class)))
				.thenReturn(new PageImpl<SupportHouse>(mock));
		Page<SupportHouse> supportHouses = this.supportHouseService.findAll(
				SupportHouseRequestParamsDto.builder().offset(0).limit(25).orderBy("id").direction("ASC").build());
		assertEquals(supportHouses.getContent(), mock);

		verify(supportHouseRepository).findAll(any(SupportHouseSpecification.class), any(Pageable.class));
	}

	@Test
	void byIdErrorTest() {
		when(supportHouseRepository.findById(1l))
				.thenThrow(new DataIntegrityViolationException("DataIntegrityViolationException"));
		DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
			this.supportHouseService.byId(1l);
		});
		assertEquals("DataIntegrityViolationException", exception.getMessage());
	}

	@Test
	void byIdNotFoundTest() {
		when(supportHouseRepository.findById(1l)).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			this.supportHouseService.byId(1l);
		});
		assertEquals("No row with the given identifier exists: [br.org.ccb.sgh.entity.SupportHouse#1]",
				exception.getMessage());
	}

	@Test
	void byIdSuccessTest() {
		List<SupportHouse> mock = TestUtils.createSupportHouseList();
		when(supportHouseRepository.findById(1l)).thenReturn(Optional.of(mock.get(0)));

		SupportHouse supportHouse = this.supportHouseService.byId(1l);
		assertEquals(supportHouse, mock.get(0));

		verify(supportHouseRepository).findById(1l);
	}

	@Test
	void saveSuccessTest() {
		SupportHouse mock = TestUtils.createSupportHouseList().get(0);
		mock.setId(null);
		mock.getAddress().setId(null);
		when(this.supportHouseRepository.save(mock)).thenReturn(mock);
		SupportHouse supportHouse = this.supportHouseService.save(TestUtils.createSupportHouseDto());
		assertEquals(mock, supportHouse);
		verify(this.supportHouseRepository).save(mock);
	}

	@Test
	void updateSuccessTest() {
		SupportHouse mock = TestUtils.createSupportHouseList().get(0);
		mock.setId(null);
		mock.getAddress().setId(null);
		when(this.supportHouseRepository.findById(mock.getId())).thenReturn(Optional.of(mock));
		when(this.supportHouseRepository.save(mock)).thenReturn(mock);
		SupportHouse supportHouse = this.supportHouseService.update(mock.getId(), TestUtils.createSupportHouseDto());
		assertEquals(mock, supportHouse);
		verify(this.supportHouseRepository).findById(mock.getId());
		verify(this.supportHouseRepository).save(mock);
	}

	@Test
	void deleteByIdSuccessTest() {
		doNothing().when(this.supportHouseRepository).deleteById(1l);
		this.supportHouseService.remove(1l);
		verify(this.supportHouseRepository).deleteById(1l);
	}
}
