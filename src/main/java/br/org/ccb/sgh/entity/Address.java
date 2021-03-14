package br.org.ccb.sgh.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.org.ccb.sgh.http.dto.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {
	private static final long serialVersionUID = -650219341005024051L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String street;
	@Column
	private String district;
	@Column
	private String city;
	@Column
	private String state;
	@Column
	private String number;
	@Column
	private String zipCode;

	public static Address fromDto(Long id, AddressDto addressDto) {
		return Address.builder().id(id).street(addressDto.getStreet()).district(addressDto.getDistrict())
				.city(addressDto.getCity()).state(addressDto.getState()).number(addressDto.getNumber())
				.zipCode(addressDto.getZipCode()).build();
	}
}
