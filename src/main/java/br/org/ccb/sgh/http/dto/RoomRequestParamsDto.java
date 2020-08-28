package br.org.ccb.sgh.http.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class RoomRequestParamsDto extends RequestParamsDto {
	private static final long serialVersionUID = 3404622936619484482L;

	private Long id;
	private String name;
	private String floor;
	private String number;
	private Integer numberOfBeds;
	private Boolean available;
}
