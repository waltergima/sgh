package br.org.ccb.sgh.controller;

import java.net.URI;
import java.time.LocalDate;

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

import br.org.ccb.sgh.entity.Reservation;
import br.org.ccb.sgh.http.dto.ReservationDto;
import br.org.ccb.sgh.http.dto.ReservationRequestParamsDto;
import br.org.ccb.sgh.service.ReservationService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/reservations")
@Tag(name = "Reservation", description = "CRUD for Reservation")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.OPTIONS })
	@GetMapping
	public ResponseEntity<Page<Reservation>> findAll(@RequestParam(value = "id", required = false) Long id,
			@DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam(value = "initialDate", required = false) LocalDate initialDate,
			@DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam(value = "finalDate", required = false) LocalDate finalDate,
			@DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam(value = "checkinDate", required = false) LocalDate checkinDate,
			@DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam(value = "checkoutDate", required = false) LocalDate checkoutDate,
			@RequestParam(value = "roomId", required = false) Long roomId,
			@RequestParam(value = "roomName", required = false) String roomName,
			@RequestParam(value = "guestId", required = false) Long guestId,
			@RequestParam(value = "guestName", required = false) String guestName,
			@RequestParam(value = "contactId", required = false) Long contactId,
			@RequestParam(value = "contactName", required = false) String contactName,
			@RequestParam(value = "supportHouseId", required = false) Long supportHouseId,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "offset", defaultValue = "0") Integer offset,
			@RequestParam(value = "limit", defaultValue = "10") Integer limit,
			@RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		return ResponseEntity.ok(this.reservationService
				.findAll(ReservationRequestParamsDto.builder()
						.initialDate(initialDate).finalDate(finalDate).checkinDate(checkinDate).checkoutDate(checkoutDate)
						.roomId(roomId).roomName(roomName).guestId(guestId).guestName(guestName)
						.contactId(contactId).contactName(contactName).supportHouseId(supportHouseId)
						.offset(offset).limit(limit).orderBy(orderBy).direction(direction).build()));
	}
	
	@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.OPTIONS })
	@GetMapping(value="/{id}")
	public ResponseEntity<Reservation> byId(@PathVariable Long id){
		return ResponseEntity.ok(this.reservationService.byId(id));
	}
	
	@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.OPTIONS })
	@PostMapping
	public ResponseEntity<Void> save(@Validated @RequestBody ReservationDto reservationDto) {
		Reservation city = this.reservationService.save(reservationDto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(city.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@CrossOrigin(origins = "*", methods = { RequestMethod.PUT, RequestMethod.OPTIONS })
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @Validated @RequestBody ReservationDto reservationDto) {
		this.reservationService.update(id, reservationDto);

		return ResponseEntity.noContent().build();
	}
	
	@CrossOrigin(origins = "*", methods={RequestMethod.DELETE, RequestMethod.OPTIONS})
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> remove(@PathVariable Long id){
		this.reservationService.remove(id);
		return ResponseEntity.noContent().build();
	}
}
