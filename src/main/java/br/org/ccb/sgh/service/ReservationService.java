package br.org.ccb.sgh.service;

import java.util.List;

import org.springframework.data.domain.Page;

import br.org.ccb.sgh.entity.Reservation;
import br.org.ccb.sgh.http.dto.ContactDto;
import br.org.ccb.sgh.http.dto.GuestDto;
import br.org.ccb.sgh.http.dto.ReservationCheckinCheckoutDto;
import br.org.ccb.sgh.http.dto.ReservationDto;
import br.org.ccb.sgh.http.dto.ReservationRequestParamsDto;
import br.org.ccb.sgh.http.dto.ReservationStatusDto;

public interface ReservationService {
	
	public Page<Reservation> findAll(ReservationRequestParamsDto requestParams);

	public Reservation byId(Long id);

	public Reservation save(ReservationDto reservationDto);

	public Reservation update(Long id, ReservationDto reservationDto);

	public void remove(Long id);

	public Reservation updateStatus(Long id, ReservationStatusDto reservationStatusDto);

	public void checkin(Long id, ReservationCheckinCheckoutDto reservationsCheckinDto);
	
	public void checkout(Long id, ReservationCheckinCheckoutDto reservationsCheckinDto);

	public void updateContact(Long id, ContactDto contactDto);
	
	public void updateObservation(Long id, String observation);
	
	public void addGuests(Long id, List<GuestDto> guestDto);

}
