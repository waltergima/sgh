package br.org.ccb.sgh.service;

import org.springframework.data.domain.Page;

import br.org.ccb.sgh.entity.Room;
import br.org.ccb.sgh.http.dto.RoomDto;
import br.org.ccb.sgh.http.dto.RoomRequestParamsDto;

public interface RoomService {
	
	public Page<Room> findAll(RoomRequestParamsDto requestParams);

	public Room byId(Long id);

	public Room save(RoomDto roomDto);

	public Room update(Long id, RoomDto roomDto);

	public void remove(Long id);


}
