package br.org.ccb.sgh.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.org.ccb.sgh.http.dto.RoomDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {

	@Id
	@GeneratedValue
	private Long id;
	@Column
	private String name;
	@Column
	private String floor;
	@Column
	private String number;
	@Column
	private Integer numberOfBeds;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	private SupportHouse supportHouse;
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.TRUE)
	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "room")
	private List<Reservation> reservations;
	@Transient
	private Boolean available;

	public static Room fromDto(Long id, Long supportHouseId, RoomDto roomDto) {
		return Room.builder().id(id).name(roomDto.getName()).floor(roomDto.getFloor()).number(roomDto.getNumber())
				.numberOfBeds(roomDto.getNumberOfBeds()).supportHouse(SupportHouse.builder().id(supportHouseId).build())
				.build();
	}

	@PostLoad
	private void postLoad() {
		this.available = ObjectUtils.isEmpty(reservations)
				|| reservations.stream().noneMatch(reservation -> reservation.getCheckoutDate() == null
						|| reservation.getCheckoutDate().isAfter(LocalDate.now()));
	}
}
