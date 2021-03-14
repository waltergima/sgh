package br.org.ccb.sgh.service.impl;

import javax.transaction.Transactional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.org.ccb.sgh.entity.Reservation;
import br.org.ccb.sgh.http.dto.ReservationDto;
import br.org.ccb.sgh.http.dto.ReservationRequestParamsDto;
import br.org.ccb.sgh.repository.ReservationRepository;
import br.org.ccb.sgh.repository.specification.ReservationSpecification;
import br.org.ccb.sgh.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;

	public Page<Reservation> findAll(ReservationRequestParamsDto requestParams) {
		Page<Reservation> reservations = this.reservationRepository.findAll(new ReservationSpecification(requestParams),
				requestParams.getPageRequest());
		if (reservations.isEmpty()) {
			throw new EmptyResultDataAccessException(requestParams.getLimit());
		}

		return reservations;
	}

	@Override
	public Reservation byId(Long id) {
		return reservationRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(id, Reservation.class.getName()));
	}

	@Override
	public Reservation save(ReservationDto reservationDto) {
		return this.reservationRepository.save(Reservation.fromDto(null, reservationDto));
	}

	@Override
	@Transactional
	public Reservation update(Long id, ReservationDto reservationDto) {
		Reservation reservation = this.byId(id);
		reservation = Reservation.fromDto(reservation.getId(), reservationDto);

		return this.reservationRepository.save(reservation);
	}

	@Override
	public void remove(Long id) {
		this.reservationRepository.deleteById(id);
	}

}
