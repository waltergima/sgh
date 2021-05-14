package br.org.ccb.sgh.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.org.ccb.sgh.http.dto.RoomDto;
import br.org.ccb.sgh.util.RoomStatus;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String name;
	@Column
	private String floor;
	@Column
	private String number;
	@Column
	private Integer numberOfBeds;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "support_house_id", referencedColumnName = "id")
	@Fetch(FetchMode.JOIN)
	private SupportHouse supportHouse;
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.TRUE)
	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "room", fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private List<Reservation> reservations;
	@Transient
	private RoomStatus status;

	public static Room fromDto(Long id, Long supportHouseId, RoomDto roomDto) {
		return Room.builder().id(id).name(roomDto.getName()).floor(roomDto.getFloor()).number(roomDto.getNumber())
				.numberOfBeds(roomDto.getNumberOfBeds()).supportHouse(SupportHouse.builder().id(supportHouseId).build())
				.build();
	}
}
