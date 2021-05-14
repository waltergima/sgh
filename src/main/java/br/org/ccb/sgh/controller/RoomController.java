package br.org.ccb.sgh.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.org.ccb.sgh.entity.Room;
import br.org.ccb.sgh.exception.InvalidParamException;
import br.org.ccb.sgh.http.dto.RoomDto;
import br.org.ccb.sgh.http.dto.RoomRequestParamsDto;
import br.org.ccb.sgh.service.RoomService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/rooms")
@Tag(name = "Room", description = "CRUD for Room")
public class RoomController {

	@Autowired
	private RoomService roomService;

	@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.OPTIONS })
	@GetMapping
	public ResponseEntity<Page<Room>> findAll(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "floor", required = false) String floor,
			@RequestParam(value = "number", required = false) String number,
			@RequestParam(value = "numberOfBeds", required = false) Integer numberOfBeds,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "initialDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate initialDate,
			@RequestParam(value = "finalDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate finalDate,
			@RequestParam(value = "reservationId", required = false) Long reservationId,
			@RequestParam(value = "supportHouseId", required = false) Long supportHouseId,
			@RequestParam(value = "offset", defaultValue = "0") Integer offset,
			@RequestParam(value = "limit", defaultValue = "10") Integer limit,
			@RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		
		validateParams(status, initialDate, finalDate);

		return ResponseEntity.ok(this.roomService.findAll(RoomRequestParamsDto.builder().id(id).name(name).floor(floor)
				.number(number).numberOfBeds(numberOfBeds).status(status).initialDate(initialDate).finalDate(finalDate)
				.reservationId(reservationId).supportHouseId(supportHouseId).offset(offset).limit(limit)
				.orderBy(orderBy).direction(direction).build()));
	}
	
	@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.OPTIONS })
	@GetMapping(value = "/{id}")
	public ResponseEntity<Room> byId(@PathVariable Long id) {
		return ResponseEntity.ok(this.roomService.byId(id));
	}

	@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.OPTIONS })
	@PostMapping
	public ResponseEntity<Void> save(@Validated @RequestBody RoomDto roomDto) {
		Room city = this.roomService.save(roomDto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(city.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@CrossOrigin(origins = "*", methods = { RequestMethod.PUT, RequestMethod.OPTIONS })
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @Validated @RequestBody RoomDto roomDto) {
		this.roomService.update(id, roomDto);

		return ResponseEntity.noContent().build();
	}

	@CrossOrigin(origins = "*", methods = { RequestMethod.DELETE, RequestMethod.OPTIONS })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> remove(@PathVariable Long id) {
		this.roomService.remove(id);
		return ResponseEntity.noContent().build();
	}
	
	private void validateParams(String status, LocalDate initialDate, LocalDate finalDate) {
		String message = "You must inform both initialDate and finalDate";
		
		if (status != null && (initialDate == null || finalDate == null)) {
			throw new InvalidParamException("status", status, message.concat(" when status is informed"));
		}
		
		if (initialDate != null && finalDate == null) {
			throw new InvalidParamException("initialDate",
					initialDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd")), message);
		}
		if (initialDate == null && finalDate != null) {
			throw new InvalidParamException("finalDate", finalDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd")),
					message);
		}
		
		if(finalDate != null && finalDate.isBefore(initialDate)) {
			throw new InvalidParamException("finalDate", finalDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd")),
					"initialDate must be before finalDate");
		}
	}
}
