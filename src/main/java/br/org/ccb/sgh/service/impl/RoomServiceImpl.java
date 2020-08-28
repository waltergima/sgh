package br.org.ccb.sgh.service.impl;

import javax.transaction.Transactional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.org.ccb.sgh.entity.Room;
import br.org.ccb.sgh.http.dto.RoomDto;
import br.org.ccb.sgh.http.dto.RoomRequestParamsDto;
import br.org.ccb.sgh.repository.RoomRepository;
import br.org.ccb.sgh.repository.specification.RoomSpecification;
import br.org.ccb.sgh.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {
	
	@Autowired
	private RoomRepository roomRepository;

	public Page<Room> findAll(RoomRequestParamsDto requestParams) {
		return this.roomRepository.findAll(new RoomSpecification(requestParams),
				requestParams.getPageRequest());
	}

	@Override
	public Room byId(Long id) {
		return roomRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(id, Room.class.getName()));
	}

	@Override
	public Room save(RoomDto roomDto) {
		return this.roomRepository.save(Room.fromDto(null, roomDto.getSupportHouse().getId(), roomDto));
	}

	@Override
	@Transactional
	public Room update(Long id, RoomDto roomDto) {
		Room room = this.byId(id);
		room = Room.fromDto(room.getId(), roomDto.getSupportHouse().getId(), roomDto);

		return this.roomRepository.save(room);
	}

	@Override
	public void remove(Long id) {
		this.roomRepository.deleteById(id);
	}

}
