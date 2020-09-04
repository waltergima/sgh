package br.org.ccb.sgh.http.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class ReservationRequestParamsDto extends RequestParamsDto {
	private static final long serialVersionUID = 1238302711226296125L;
	
	private Long id;
	private LocalDate initialDate;
	private LocalDate finalDate;
	private LocalDate checkinDate;
	private LocalDate checkoutDate;
	private Long roomId;
	private String roomName;
	private Long guestId;
	private String guestName;
	private Long contactId;
	private String contactName;
	private Long supportHouseId;
	private String status;

}
