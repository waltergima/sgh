package br.org.ccb.sgh.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.org.ccb.sgh.http.dto.ContactDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

	@Id
	@GeneratedValue
	private Long id;
	@Column
	private String name;
	@Column
	private String phoneNumber;
	@Column
	private String celNumber;
	@Column
	private String ministery;
	@Column
	private String relationship;
	@OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;
	@Column
	private String observation;

	public static Contact fromDto(Long id, ContactDto contactDto) {
		return Contact.builder().id(id).name(contactDto.getName()).phoneNumber(contactDto.getPhoneNumber())
				.celNumber(contactDto.getCelNumber()).ministery(contactDto.getMinistery())
				.relationship(contactDto.getRelationship()).address(Address.fromDto(null, contactDto.getAddress()))
				.observation(contactDto.getObservation()).build();
	}
}
