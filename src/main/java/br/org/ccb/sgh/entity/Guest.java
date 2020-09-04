package br.org.ccb.sgh.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.org.ccb.sgh.http.dto.GuestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Guest {

	@Id
	@GeneratedValue
	private Long id;
	@Column
	private String name;
	@OneToOne
	@JoinColumn(name = "type_id", referencedColumnName = "id")
	private GuestType type;
	@OneToOne(orphanRemoval = true)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;
	@Column
	private LocalDate dateOfBirth;
	@Column
	private String rg;
	@Column
	private String cpf;
	@Column
	private String phoneNumber;
	@Column
	private String celNumber;
	@Column
	private Boolean ministery;
	@Column
	private Boolean baptized;
	@Column
	private LocalDate dateOfBaptism;
	@Column
	private String prayingHouse;
	@Column
	private String observation;
	@JsonBackReference
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "guests")
	@JsonIgnore
	private List<Reservation> reservations;

	public static Guest fromDto(Long id, Long addressId, GuestDto guestDto) {
		return Guest.builder().id(id).name(guestDto.getName()).type(GuestType.builder().id(guestDto.getType()).build())
				.address(Address.builder().id(addressId).build()).dateOfBirth(guestDto.getDateOfBirth())
				.rg(guestDto.getRg()).cpf(guestDto.getCpf()).phoneNumber(guestDto.getPhoneNumber())
				.celNumber(guestDto.getCelNumber()).ministery(guestDto.getMinistery()).baptized(guestDto.getBaptized())
				.dateOfBaptism(guestDto.getDateOfBaptism()).prayingHouse(guestDto.getPrayingHouse())
				.observation(guestDto.getObservation()).build();
	}
}
