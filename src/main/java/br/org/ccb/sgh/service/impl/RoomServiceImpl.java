package br.org.ccb.sgh.service.impl;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

	@Override
	public Page<Room> findAll(RoomRequestParamsDto requestParams) {
		Page<Room> rooms = this.roomRepository.findAll(new RoomSpecification(requestParams),
				requestParams.getPageRequest());

		if (rooms.isEmpty()) {
			throw new EmptyResultDataAccessException(requestParams.getLimit());
		}
		
		setAvailability(requestParams, rooms);

		return rooms;
	}

	@Override
	public Room byId(Long id) {
		return roomRepository.findById(id).map(room -> {
			room.setAvailable(roomIsAvailable(room));
			return room;
		}).orElseThrow(() -> new ObjectNotFoundException(id, Room.class.getName()));
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

	private void setAvailability(RoomRequestParamsDto requestParams, Page<Room> rooms) {
		rooms.getContent().forEach(room -> {
			if (requestParams.getAvailable() != null) {
				room.setAvailable(requestParams.getAvailable());
			} else {
				room.setAvailable(roomIsAvailable(room));
			}
		});
	}

	public Boolean roomIsAvailable(Room room) {
		LocalDate date = LocalDate.now();
		return (ObjectUtils.isEmpty(room.getReservations()) || room.getReservations().stream()
				.noneMatch(reservation -> !date.isBefore(reservation.getInitialDate())
						&& (reservation.getCheckoutDate() == null
								|| date.isBefore(reservation.getCheckoutDate()))));
	}

}
