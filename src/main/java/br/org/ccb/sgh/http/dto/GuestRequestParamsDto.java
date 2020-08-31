package br.org.ccb.sgh.http.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class GuestRequestParamsDto extends RequestParamsDto {
	private static final long serialVersionUID = 5201088081308163581L;
	
	private Long id;
	private String name;
	private LocalDate dateOfBirth;
	private LocalDate dateOfBaptism;
	private String rg;
	private String cpf;
	private String phoneNumber;
	private String celNumber;
	private Boolean ministery;
	private String prayingHouse;
	private String observation;
	private Long reservationId;
}
