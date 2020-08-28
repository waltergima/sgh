package br.org.ccb.sgh.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

	@Id
	@GeneratedValue
	private Long id;
	@Column
	private LocalDate initialDate;
	@Column
	private LocalDate finalDate;
	@Column
	private LocalDate checkinDate;
	@Column
	private LocalDate checkoutDate;
	@OneToOne
	@JoinColumn(name = "room_id", referencedColumnName = "id")
	private Room room;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "reservations_guests", joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "guest_id", referencedColumnName = "id"))
	@Fetch(FetchMode.JOIN)
	private List<Guest> guests;
	@OneToOne
	@JoinColumn(name = "contact_id", referencedColumnName = "id")
	private Contact contact;
	@OneToOne
	@JoinColumn(name = "support_house_id", referencedColumnName = "id")
	private SupportHouse supportHouse;
	@Column
	private String observation;
	@Enumerated(EnumType.STRING)
	private Status status;
}
