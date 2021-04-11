package br.org.ccb.sgh.service;

import org.springframework.data.domain.Page;

import br.org.ccb.sgh.entity.Reservation;
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

}
