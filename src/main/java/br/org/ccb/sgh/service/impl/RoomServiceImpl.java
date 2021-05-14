package br.org.ccb.sgh.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.org.ccb.sgh.entity.Reservation;
import br.org.ccb.sgh.entity.Room;
import br.org.ccb.sgh.http.dto.RoomDto;
import br.org.ccb.sgh.http.dto.RoomRequestParamsDto;
import br.org.ccb.sgh.repository.RoomRepository;
import br.org.ccb.sgh.repository.specification.RoomSpecification;
import br.org.ccb.sgh.service.RoomService;
import br.org.ccb.sgh.util.ReservationStatus;
import br.org.ccb.sgh.util.RoomStatus;

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
		
		if(requestParams.getInitialDate() != null && requestParams.getFinalDate() != null) {
			setAvailability(requestParams, rooms);
		}

		return rooms;
	}

	@Override
	public Room byId(Long id) {
		return roomRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, Room.class.getName()));
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
			if(requestParams.getStatus() != null) {
				room.setStatus(RoomStatus.valueOf(requestParams.getStatus()));
			} else {
				room.setStatus(getRoomStatus(room, requestParams.getInitialDate(), requestParams.getFinalDate()));
			}
		});
	}

	public RoomStatus getRoomStatus(Room room, LocalDate initialDate, LocalDate finalDate) {
		if(ObjectUtils.isEmpty(room.getReservations())) {
			return RoomStatus.AVAILABLE;
		}
		List<Reservation> reservations = room.getReservations().stream()
				.filter(reservation -> reservation.getStatus().equals(ReservationStatus.CONFIRMED))
				.collect(Collectors.toList());
		
		for (Reservation reservation : reservations) {
			if (reservation.getCheckinDate() != null && (
					isDateBetweenCheckinAndCheckoutDates(initialDate, reservation)
					|| isDateBetweenCheckinAndCheckoutDates(finalDate, reservation)
					|| (!reservation.getCheckinDate().isBefore(initialDate)
							&& !reservation.getCheckinDate().isAfter(finalDate)))) {
				return RoomStatus.OCCUPIED;
			}
			
			if (reservation.getCheckoutDate() == null && (isDateBetweenInitialAndFinalDates(initialDate, reservation)
					|| isDateBetweenInitialAndFinalDates(finalDate, reservation)
					|| (!reservation.getInitialDate().isBefore(initialDate)
							&& !reservation.getInitialDate().isAfter(finalDate)))) {
				return RoomStatus.RESERVED;
			}
		}
		return RoomStatus.AVAILABLE;
	}
	
	private Boolean isDateBetweenCheckinAndCheckoutDates(LocalDate date, Reservation reservation) {
		return !date.isBefore(reservation.getCheckinDate())
				&& (reservation.getCheckoutDate() == null || !date.isAfter(reservation.getCheckoutDate()));
	}
	
	private Boolean isDateBetweenInitialAndFinalDates(LocalDate date, Reservation reservation) {
		return !date.isBefore(reservation.getInitialDate())
				&& !date.isAfter(reservation.getFinalDate());
	}
}
