package br.org.ccb.sgh.http.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestDto implements Serializable {
	private static final long serialVersionUID = 5794322388946526991L;

	@NotNull
	@Size(min = 1, max = 255)
	private String name;
	@NotNull
	private Long type; 
	@Valid
	private AddressDto address;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;
	@Size(min = 1, max = 9)
	private String rg;
	@CPF
	private String cpf;
	@Size(min = 1, max = 11)
	private String phoneNumber;
	@Size(min = 1, max = 11)
	private String celNumber;
	private Boolean ministery;
	private Boolean baptized;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBaptism;
	@Size(min = 1, max = 255)
	private String prayingHouse;
	@Size(min = 1)
	private String observation;
}
