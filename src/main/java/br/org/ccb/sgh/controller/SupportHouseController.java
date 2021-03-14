package br.org.ccb.sgh.controller;

import java.net.URI;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import br.org.ccb.sgh.entity.SupportHouse;
import br.org.ccb.sgh.http.dto.SupportHouseDto;
import br.org.ccb.sgh.http.dto.SupportHouseRequestParamsDto;
import br.org.ccb.sgh.service.SupportHouseService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/supporthouses")
@Validated
@Tag(name = "SupportHouse", description = "CRUD for Support Houses")
public class SupportHouseController {

	@Autowired
	private SupportHouseService supportHouseService;

	@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.OPTIONS })
	@GetMapping
	public ResponseEntity<Page<SupportHouse>> findAll(@Min(1) @RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "cnpj", required = false) String cnpj,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "offset", defaultValue = "0") Integer offset,
			@RequestParam(value = "limit", defaultValue = "10") Integer limit,
			@RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		return ResponseEntity.ok(this.supportHouseService
				.findAll(SupportHouseRequestParamsDto.builder().id(id).name(name).cnpj(cnpj).city(city).state(state)
						.offset(offset).limit(limit).orderBy(orderBy).direction(direction).build()));
	}
	
	@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.OPTIONS })
	@GetMapping(value="/{id}")
	public ResponseEntity<SupportHouse> byId(@PathVariable Long id){
		return ResponseEntity.ok(this.supportHouseService.byId(id));
	}
	
	@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.OPTIONS })
	@PostMapping
	public ResponseEntity<Void> save(@Validated @RequestBody SupportHouseDto supportHouseDto) {
		SupportHouse city = this.supportHouseService.save(supportHouseDto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(city.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@CrossOrigin(origins = "*", methods = { RequestMethod.PUT, RequestMethod.OPTIONS })
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @Validated @RequestBody SupportHouseDto supportHouseDto) {
		this.supportHouseService.update(id, supportHouseDto);

		return ResponseEntity.noContent().build();
	}
	
	@CrossOrigin(origins = "*", methods={RequestMethod.DELETE, RequestMethod.OPTIONS})
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> remove(@PathVariable Long id){
		this.supportHouseService.remove(id);
		return ResponseEntity.noContent().build();
	}
}
