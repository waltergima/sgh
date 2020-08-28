package br.org.ccb.sgh.http.dto;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto implements Serializable {
	private static final long serialVersionUID = 1343959111141053202L;
	
	@Min(1)
	private Long id;
	@NotNull
	@Size(min = 1, max = 255)
	private String name;
	@NotNull
	@Size(min = 1, max = 255)
	private String floor;
	@NotNull
	@Size(min = 1, max = 10)
	private String number;
	@NotNull 
	@Min(1)
	private Integer numberOfBeds;
	@Valid
	@NotNull
	private InnerObjectDto supportHouse;
}
