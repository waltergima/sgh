package br.org.ccb.sgh.controller;

import java.net.URI;
import java.time.LocalDate;

import javax.transaction.Transactional;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;
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

import br.org.ccb.sgh.entity.Guest;
import br.org.ccb.sgh.http.dto.GuestDto;
import br.org.ccb.sgh.http.dto.GuestRequestParamsDto;
import br.org.ccb.sgh.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/guests")
@Validated
@Transactional
@Tag(name = "Guest", description = "CRUD for guest")
public class GuestController {

	@Autowired
	private GuestService guestService;

	@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.OPTIONS })
	@GetMapping
	@Operation
	public ResponseEntity<Page<Guest>> findAll(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "name", required = false) String name,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(value = "dateOfBirth", required = false) LocalDate dateOfBirth,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(value = "dateOfBaptism", required = false) LocalDate dateOfBaptism,
			@RequestParam(value = "rg", required = false) @Size(min = 1, max = 9) String rg,
			@RequestParam(value = "cpf", required = false) @CPF String cpf,
			@RequestParam(value = "phoneNumber", required = false) @Size(min = 1, max = 11) String phoneNumber,
			@RequestParam(value = "celNumber", required = false) @Size(min = 1, max = 11) String celNumber,
			@RequestParam(value = "ministery", required = false) Boolean ministery,
			@RequestParam(value = "prayingHouse", required = false) @Size(min = 1, max = 255) String prayingHouse,
			@RequestParam(value = "observation", required = false) @Size(min = 1) String observation,
			@RequestParam(value = "reservationId", required = false) Long reservationId,
			@RequestParam(value = "offset", defaultValue = "0") Integer offset,
			@RequestParam(value = "limit", defaultValue = "10") Integer limit,
			@RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		return ResponseEntity.ok(this.guestService
				.findAll(GuestRequestParamsDto.builder().id(id).name(name).dateOfBirth(dateOfBirth).rg(rg).cpf(cpf)
						.dateOfBaptism(dateOfBaptism).phoneNumber(phoneNumber).celNumber(celNumber).ministery(ministery)
						.prayingHouse(prayingHouse).observation(observation).reservationId(reservationId).offset(offset)
						.limit(limit).orderBy(orderBy).direction(direction).build()));
	}
	
	@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.OPTIONS })
	@GetMapping(value="/{id}")
	public ResponseEntity<Guest> byId(@PathVariable Long id){
		return ResponseEntity.ok(this.guestService.byId(id));
	}
	
	@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.OPTIONS })
	@PostMapping
	public ResponseEntity<Void> save(@Validated @RequestBody GuestDto guestDto) {
		Guest guest = this.guestService.save(guestDto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(guest.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@CrossOrigin(origins = "*", methods = { RequestMethod.PUT, RequestMethod.OPTIONS })
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @Validated @RequestBody GuestDto guestDto) {
		this.guestService.update(id, guestDto);

		return ResponseEntity.noContent().build();
	}
	
	@CrossOrigin(origins = "*", methods={RequestMethod.DELETE, RequestMethod.OPTIONS})
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> remove(@PathVariable Long id){
		this.guestService.remove(id);
		return ResponseEntity.noContent().build();
	}
}
