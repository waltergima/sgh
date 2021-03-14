package br.org.ccb.sgh.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String name;
	@OneToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id", referencedColumnName = "id")
	@Fetch(FetchMode.JOIN)
	private GuestType type;
	@OneToOne(orphanRemoval = true, optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	@Fetch(FetchMode.JOIN)
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
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "guests")
	@JsonIgnore
	@JsonIgnoreProperties(value = "reservations")
	@Fetch(FetchMode.JOIN)
	private List<Reservation> reservations;

	public static Guest fromDto(Long id, Long addressId, GuestDto guestDto) {
		return Guest.builder().id(id).name(guestDto.getName()).type(GuestType.builder().id(guestDto.getType()).build())
				.address(Address.fromDto(addressId, guestDto.getAddress())).dateOfBirth(guestDto.getDateOfBirth())
				.rg(guestDto.getRg()).cpf(guestDto.getCpf()).phoneNumber(guestDto.getPhoneNumber())
				.celNumber(guestDto.getCelNumber()).ministery(guestDto.getMinistery()).baptized(guestDto.getBaptized())
				.dateOfBaptism(guestDto.getDateOfBaptism()).prayingHouse(guestDto.getPrayingHouse())
				.observation(guestDto.getObservation()).build();
	}
}
