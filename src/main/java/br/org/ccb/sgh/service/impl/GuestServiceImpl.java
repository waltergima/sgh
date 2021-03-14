package br.org.ccb.sgh.service.impl;

import javax.transaction.Transactional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.org.ccb.sgh.entity.Guest;
import br.org.ccb.sgh.http.dto.GuestDto;
import br.org.ccb.sgh.http.dto.GuestRequestParamsDto;
import br.org.ccb.sgh.repository.GuestRepository;
import br.org.ccb.sgh.repository.specification.GuestSpecification;
import br.org.ccb.sgh.service.GuestService;

@Service
public class GuestServiceImpl implements GuestService {

	@Autowired
	private GuestRepository guestRepository;

	public Page<Guest> findAll(GuestRequestParamsDto requestParams) {
		Page<Guest> guests = this.guestRepository.findAll(new GuestSpecification(requestParams),
				requestParams.getPageRequest());
		if (guests.isEmpty()) {
			throw new EmptyResultDataAccessException(requestParams.getLimit());
		}

		return guests;
	}

	@Override
	public Guest byId(Long id) {
		return guestRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, Guest.class.getName()));
	}

	@Override
	public Guest save(GuestDto guestDto) {
		Guest guest = Guest.fromDto(null, null, guestDto);
		return this.guestRepository.save(guest);
	}

	@Override
	@Transactional
	public Guest update(Long id, GuestDto guestDto) {
		Guest guest = this.byId(id);
		guest = Guest.fromDto(guest.getId(), guest.getAddress().getId(), guestDto);

		return this.guestRepository.save(guest);
	}

	@Override
	public void remove(Long id) {
		this.guestRepository.deleteById(id);
	}

}
