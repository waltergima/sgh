package br.org.ccb.sgh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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
	@OneToOne
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;
	@Column
	private String observation;
}
