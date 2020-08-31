package br.org.ccb.sgh.service;

import org.springframework.data.domain.Page;

import br.org.ccb.sgh.entity.Guest;
import br.org.ccb.sgh.http.dto.GuestDto;
import br.org.ccb.sgh.http.dto.GuestRequestParamsDto;

public interface GuestService {
	
	public Page<Guest> findAll(GuestRequestParamsDto requestParams);

	public Guest byId(Long id);

	public Guest save(GuestDto guestDto);

	public Guest update(Long id, GuestDto guestDto);

	public void remove(Long id);

}
