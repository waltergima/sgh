package br.org.ccb.sgh.service.impl;

import javax.transaction.Transactional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.org.ccb.sgh.entity.Address;
import br.org.ccb.sgh.entity.Guest;
import br.org.ccb.sgh.http.dto.GuestDto;
import br.org.ccb.sgh.http.dto.GuestRequestParamsDto;
import br.org.ccb.sgh.repository.AddressRepository;
import br.org.ccb.sgh.repository.GuestRepository;
import br.org.ccb.sgh.repository.specification.GuestSpecification;
import br.org.ccb.sgh.service.GuestService;

@Service
public class GuestServiceImpl implements GuestService {

	@Autowired
	private GuestRepository guestRepository;

	@Autowired
	private AddressRepository addressRepository;

	public Page<Guest> findAll(GuestRequestParamsDto requestParams) {
		return this.guestRepository.findAll(new GuestSpecification(requestParams),
				requestParams.getPageRequest());
	}

	@Override
	public Guest byId(Long id) {
		return guestRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(id, Guest.class.getName()));
	}

	@Override
	public Guest save(GuestDto guestDto) {
		Guest guest = Guest.fromDto(null, null, guestDto);
		guest.setAddress(this.addressRepository.save(Address.fromDto(null, guestDto.getAddress())));
		return this.guestRepository.save(guest);
	}

	@Override
	@Transactional
	public Guest update(Long id, GuestDto guestDto) {
		Guest guest = this.byId(id);
		Address address = Address.fromDto(guest.getAddress().getId(), guestDto.getAddress());
		guest = Guest.fromDto(guest.getId(), address.getId(), guestDto);

		return this.guestRepository.save(guest);
	}

	@Override
	public void remove(Long id) {
		this.guestRepository.deleteById(id);
	}

}
