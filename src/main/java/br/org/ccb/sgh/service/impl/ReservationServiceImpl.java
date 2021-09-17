package br.org.ccb.sgh.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.org.ccb.sgh.entity.Contact;
import br.org.ccb.sgh.entity.Guest;
import br.org.ccb.sgh.entity.Reservation;
import br.org.ccb.sgh.http.dto.ContactDto;
import br.org.ccb.sgh.http.dto.GuestDto;
import br.org.ccb.sgh.http.dto.ReservationCheckinCheckoutDto;
import br.org.ccb.sgh.http.dto.ReservationDto;
import br.org.ccb.sgh.http.dto.ReservationRequestParamsDto;
import br.org.ccb.sgh.http.dto.ReservationStatusDto;
import br.org.ccb.sgh.repository.ReservationRepository;
import br.org.ccb.sgh.repository.specification.ReservationSpecification;
import br.org.ccb.sgh.service.ReservationService;
import br.org.ccb.sgh.util.ReservationStatus;

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
	@Transactional
	public Reservation updateStatus(Long id, ReservationStatusDto reservationStatusDto) {
		Reservation reservation = this.byId(id);
		reservation.setStatus(ReservationStatus.valueOf(reservationStatusDto.getStatus()));

		return this.reservationRepository.save(reservation);
	}
	
	@Override
	public void remove(Long id) {
		this.reservationRepository.deleteById(id);
	}

	@Override
	public void checkin(Long id, ReservationCheckinCheckoutDto reservationCheckinCheckoutDto) {
		this.reservationRepository.checkin(id, reservationCheckinCheckoutDto.getDate());
	}
	
	@Override
	public void checkout(Long id, ReservationCheckinCheckoutDto reservationCheckinCheckoutDto) {
		this.reservationRepository.checkout(id, reservationCheckinCheckoutDto.getDate());
	}

	@Override
	@Transactional
	public void updateContact(Long id, ContactDto contactDto) {
		Reservation reservation = this.byId(id);
		reservation.setContact(Contact.fromDto(null, contactDto));

		this.reservationRepository.save(reservation);
	}

	@Override
	@Transactional
	public void updateObservation(Long id, String observation) {
		Reservation reservation = this.byId(id);
		reservation.setObservation(observation);

		this.reservationRepository.save(reservation);
	}

	@Override
	@Transactional
	public void addGuests(Long id, List<GuestDto> guestDto) {
		Reservation reservation = this.byId(id);
		reservation.getGuests()
				.addAll(guestDto.stream().map(guest -> Guest.fromDto(null, null, guest)).collect(Collectors.toList()));

		this.reservationRepository.save(reservation);
		
	}

}
