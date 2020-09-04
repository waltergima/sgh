package br.org.ccb.sgh.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.org.ccb.sgh.http.dto.ReservationDto;
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
	@JsonIgnoreProperties(value = "supportHouse")
	private Room room;
	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "reservations_guests", joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "guest_id", referencedColumnName = "id"))
	@Fetch(FetchMode.JOIN)
	@JsonIgnoreProperties(value = "reservations")
	private List<Guest> guests;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
	@JoinColumn(name = "contact_id", referencedColumnName = "id")
	private Contact contact;
	@Column
	private String observation;
	@Enumerated(EnumType.STRING)
	private Status status;

	public static Reservation fromDto(Long id, ReservationDto reservationDto) {
		return Reservation.builder().id(id).initialDate(reservationDto.getInitialDate())
				.finalDate(reservationDto.getFinalDate()).checkinDate(reservationDto.getCheckinDate())
				.checkoutDate(reservationDto.getCheckoutDate())
				.room(Room.builder().id(reservationDto.getRoom().getId()).build())
				.guests(reservationDto.getGuests().stream().map(guest -> Guest.builder().id(guest.getId()).build())
						.collect(Collectors.toList()))
				.contact(Contact.fromDto(null, reservationDto.getContact()))
				.observation(reservationDto.getObservation()).status(Status.valueOf(reservationDto.getStatus()))
				.build();
	}
}
